package iuh.fit.week5.fontend.controller;

import iuh.fit.week5.backend.entity.Candidate;
import iuh.fit.week5.backend.entity.CandidateSkill;
import iuh.fit.week5.backend.entity.JobSkill;
import iuh.fit.week5.backend.entity.Skill;
import iuh.fit.week5.backend.service.AddressService;
import iuh.fit.week5.backend.service.CandidateService;
import iuh.fit.week5.backend.service.CandidateSkillService;
import iuh.fit.week5.backend.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private CandidateSkillService candidateSkillService;

    @GetMapping("")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        List<Skill> skills = skillService.findAll();

        Page<Candidate> candidatePage= candidateService.findAll(
                currentPage - 1,pageSize,"id","asc");


        model.addAttribute("candidatePage", candidatePage);
        model.addAttribute("skills", skills);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/candidate";
    }

    @PostMapping("/add")
    public String addCandidate(@RequestParam("jobId") long jobId) {
        candidateService.userApply(jobId);
        return "redirect:/job";
    }

    @GetMapping("/search")
    public String searchCandidate(@RequestParam("query") String query,
                                  @RequestParam("skillName") Optional<String> skillName,
                                  Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage;
        List<Skill> skills = skillService.findAll();
        model.addAttribute("skills", skills);

        boolean isQueryEmpty = (query == null || query.trim().isEmpty());

        if (isQueryEmpty && (skillName.get() == "")) {
            candidatePage = candidateService.findAll(currentPage - 1, pageSize, "id", "asc");
        } else if (skillName.get() != "" && isQueryEmpty) {
            candidatePage = candidateService.findBySkillName(skillName.get(), currentPage - 1, pageSize, "id", "asc");
        } else if (skillName.get() != "" && !isQueryEmpty) {
            candidatePage = candidateService.findByFullNameAndSkillName(query, skillName.get(), currentPage - 1, pageSize, "id", "asc");
        } else {
            candidatePage = candidateService.findByFullName(query, currentPage - 1, pageSize, "id", "asc");
        }

        model.addAttribute("candidatePage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/candidate";
    }


    @GetMapping("/delete/{id}")
    public String deleteCandidate(@PathVariable("id") long id) {
        Candidate candidate = candidateService.findById(id);
        List<CandidateSkill> candidateSkill = candidateSkillService.findByCandidateId(candidate.getId());
        if (candidateSkill != null && !candidateSkill.isEmpty()) {
            for (CandidateSkill c_skill : candidateSkill) {
                candidateSkillService.delete(c_skill);
            }
        }
        candidateService.delete(candidate);
        addressService.delete(candidate.getAddress());
        return "redirect:/candidate";
    }

}
