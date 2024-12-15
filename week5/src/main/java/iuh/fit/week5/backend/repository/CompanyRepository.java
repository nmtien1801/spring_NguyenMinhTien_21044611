package iuh.fit.week5.backend.repository;

import iuh.fit.week5.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
