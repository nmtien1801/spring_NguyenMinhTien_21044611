package iuh.fit.week5.backend.repository;

import iuh.fit.week5.backend.entity.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {
    List<CandidateSkill> findById_CanId(Long canId);
}
