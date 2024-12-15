package iuh.fit.week5.backend.repository;

import iuh.fit.week5.backend.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByJobSkills_Job_Id(Long id);
}
