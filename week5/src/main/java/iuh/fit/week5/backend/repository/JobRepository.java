package iuh.fit.week5.backend.repository;

import iuh.fit.week5.backend.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findBySkillName(String skillName, Pageable pageable);

}
