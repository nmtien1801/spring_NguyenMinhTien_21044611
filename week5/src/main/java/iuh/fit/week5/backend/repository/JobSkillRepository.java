package iuh.fit.week5.backend.repository;

import iuh.fit.week5.backend.entity.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    JobSkill findByJob_IdAndSkill_Id(long id1, Long id2);

    List<JobSkill> findByJob_Id(long jobId);
}
