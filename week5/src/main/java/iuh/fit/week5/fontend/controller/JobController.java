package iuh.fit.week5.fontend.controller;

import iuh.fit.week5.backend.entity.*;
import iuh.fit.week5.backend.service.CompanyService;
import iuh.fit.week5.backend.service.JobService;
import iuh.fit.week5.backend.service.JobSkillService;
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
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobSkillService jobSkillService;


    @GetMapping("")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobService.findAll(
                currentPage - 1, pageSize, "id", "asc");

        List<Skill> skills = skillService.findAll();
        model.addAttribute("skills", skills);


        model.addAttribute("jobPage", jobPage);

        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "client/job";
    }

    @GetMapping("/search")
    public String searchCandidate(@RequestParam("skillName") Optional<String> skillName,
                                  Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage;
        List<Skill> skills = skillService.findAll();
        model.addAttribute("skills", skills);

        if (skillName.get() == "") {
            jobPage = jobService.findAll(currentPage - 1, pageSize, "id", "asc");
        } else {
            jobPage = jobService.findBySkillName(skillName.get(), currentPage - 1, pageSize, "id", "asc");
        }

        model.addAttribute("jobPage", jobPage);

        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "client/job";
    }

    @GetMapping("/hire")
    public String manageJob(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobService.findAll(
                currentPage - 1, pageSize, "id", "asc");

        List<Skill> skills = skillService.findAll();
        List<Company> companies = companyService.findAll();

        model.addAttribute("skills", skills);
        model.addAttribute("jobPage", jobPage);
        model.addAttribute("companies", companies);

        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/hire";
    }

    @PostMapping("/add")
    public String addJob(
            @RequestParam("company") Long companyId,
            @RequestParam("skill") Long skillId,
            @RequestParam("job_name") String jobName,
            @RequestParam("job_desc") String jobDesc,
            @RequestParam("level") int level
    ) {
        // Tạo đối tượng Job mới
        Job job = new Job();
        job.setCompany(companyService.findById(companyId));
        job.setJobName(jobName);
        job.setJobDesc(jobDesc);
        jobService.save(job);

        Skill skill = skillService.findById(skillId);

        // Tạo JobSkill
        JobSkill jobSkill = new JobSkill();
        JobSkillId jobSkillId = new JobSkillId(job.getId(), skill.getId()); // Composite Key
        jobSkill.setId(jobSkillId);
        jobSkill.setJob(job);
        jobSkill.setSkill(skill);
        jobSkill.setSkillLevel((byte) level);
        jobSkill.setMoreInfos(jobDesc);
        jobSkillService.save(jobSkill);


        return "redirect:/job/hire";
    }

    @GetMapping("/search_hire")
    public String searchCandidate_hire(@RequestParam("skillName") Optional<String> skillName,
                                  Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage;
        List<Skill> skills = skillService.findAll();
        List<Company> companies = companyService.findAll();

        if (skillName.get() == "") {
            jobPage = jobService.findAll(currentPage - 1, pageSize, "id", "asc");
        } else {
            jobPage = jobService.findBySkillName(skillName.get(), currentPage - 1, pageSize, "id", "asc");
        }

        model.addAttribute("skills", skills);
        model.addAttribute("jobPage", jobPage);
        model.addAttribute("companies", companies);

        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/hire";
    }

    @GetMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long jobId, Model model) {
        Job job = jobService.findById(jobId);
        List<Skill> skills = skillService.findAll();
        List<Company> companies = companyService.findAll();
        List<Skill> arrSkill = skillService.findByJobSkills_Job_Id(jobId);

//        System.out.println(">>>>>>>>>> jobSkills: " + jobSkills);
        model.addAttribute("job", job);
        model.addAttribute("skills", skills);
        model.addAttribute("companies", companies);
        model.addAttribute("skill_ID", arrSkill.get(0));
        return "admin/updateJob";
    }

    @PostMapping("/updateDB/{id}")
    public String update(
            @PathVariable("id") long jobId,
            @RequestParam("company") Long companyId,
            @RequestParam("skill") Long skillId,
            @RequestParam("job_name") String jobName,
            @RequestParam("job_desc") String jobDesc,
            @RequestParam("level") int level
    ) {
        // update Job
        Job job = jobService.findById(jobId);
        job.setCompany(companyService.findById(companyId));
        job.setJobName(jobName);
        job.setJobDesc(jobDesc);
        jobService.save(job);

        Skill skill = skillService.findById(skillId);

//         update JobSkill
        JobSkill jobSkill = jobSkillService.findById(jobId, skillId);
        System.out.println(">>>>>>>>>> jobId: " + jobId + " skillId: " + skillId);
        System.out.println(">>>>>>>>>> jobSkill: " + jobSkill);
        jobSkill.setId(jobSkill.getId());
        jobSkill.setJob(job);
        jobSkill.setSkill(skill);
        jobSkill.setSkillLevel((byte) level);
        jobSkill.setMoreInfos(jobDesc);
        jobSkillService.save(jobSkill);

        return "redirect:/job/hire";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long jobId) {
        Job job = jobService.findById(jobId);
        List<JobSkill> jobSkill = jobSkillService.findByJobId(jobId);
        jobService.delete(job);
        if (jobSkill != null && !jobSkill.isEmpty()) {
            for (JobSkill j_skill : jobSkill) {
                jobSkillService.delete(j_skill);
            }
        }

        return "redirect:/job/hire";
    }

}
