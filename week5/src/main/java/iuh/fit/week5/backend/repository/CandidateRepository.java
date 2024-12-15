package iuh.fit.week5.backend.repository;

import iuh.fit.week5.backend.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Page<Candidate> findByFullName(String fullName, Pageable pageable);

    Page<Candidate> findBySkillName(String skillName, Pageable pageable);

    Page<Candidate> findByFullNameAndSkillName(String fullName, String skillName, Pageable pageable);
}
