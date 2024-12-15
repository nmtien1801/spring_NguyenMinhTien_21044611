package iuh.fit.week5.backend.service;

import iuh.fit.week5.backend.entity.*;
import iuh.fit.week5.backend.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    CandidateRepository candidateRepository;

    public void fakeDataSkill() {
        Faker faker = new Faker();

        // Tạo Skill
        Skill skill = new Skill();
        skill.setSkillDescription(faker.lorem().sentence());
        skill.setSkillName(faker.name().name());
        skill.setType((byte) faker.number().numberBetween(1, 5));
        skillRepository.save(skill);

        // Lấy Job
        long jobId = faker.number().numberBetween(1, 10);
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();

            // Tạo JobSkill
            JobSkill jobSkill = new JobSkill();
            JobSkillId jobSkillId = new JobSkillId(job.getId(), skill.getId()); // Composite Key
            jobSkill.setId(jobSkillId);
            jobSkill.setJob(job);
            jobSkill.setSkill(skill);
            jobSkill.setSkillLevel((byte) faker.number().numberBetween(1, 5));
            jobSkill.setMoreInfos(faker.lorem().sentence());
            jobSkillRepository.save(jobSkill);



        } else {
            System.err.println("Job với ID " + jobId + " không tồn tại.");
        }

        // Lấy Candidate
        long candidateId = faker.number().numberBetween(1, 10);
        Optional<Candidate> optionalCandidate = candidateRepository.findById(candidateId);
        if (optionalCandidate.isPresent()) {
            Candidate candidate = optionalCandidate.get();

            // Tạo CandidateSkill
            CandidateSkill candidateSkill = new CandidateSkill();
            CandidateSkillId candidateSkillId = new CandidateSkillId(candidate.getId(), skill.getId()); // Composite Key
            candidateSkill.setId(candidateSkillId);
            candidateSkill.setCan(candidate);
            candidateSkill.setSkill(skill);
            candidateSkill.setSkillLevel((byte) faker.number().numberBetween(1, 5));
            candidateSkill.setMoreInfos(faker.lorem().sentence());
            candidateSkillRepository.save(candidateSkill);
        } else {
            System.err.println("Candidate với ID " + candidateId + " không tồn tại.");
        }
    }


    public List<Skill> findAll() {
        return skillRepository.findAll();
    }


    public Skill findById(Long skillId) {
        return skillRepository.findById(skillId).orElse(null);
    }

    public List<Skill> findByJobSkills_Job_Id(long jobId) {
        return skillRepository.findByJobSkills_Job_Id(jobId);
    }
}
