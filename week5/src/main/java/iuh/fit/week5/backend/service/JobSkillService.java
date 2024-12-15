package iuh.fit.week5.backend.service;

import iuh.fit.week5.backend.entity.JobSkill;
import iuh.fit.week5.backend.repository.JobSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSkillService {
    @Autowired
    private JobSkillRepository jobSkillRepository;

    public void save(JobSkill jobSkill) {
        jobSkillRepository.save(jobSkill);
    }

    public JobSkill findById(long jobId, Long skillId) {
        return jobSkillRepository.findByJob_IdAndSkill_Id(jobId, skillId);
    }

    public List<JobSkill> findAll() {
        return jobSkillRepository.findAll();
    }

    public List<JobSkill> findByJobId(long jobId) {
        return jobSkillRepository.findByJob_Id(jobId);
    }

    public void delete(JobSkill jobSkill) {
        jobSkillRepository.delete(jobSkill);
    }
}
