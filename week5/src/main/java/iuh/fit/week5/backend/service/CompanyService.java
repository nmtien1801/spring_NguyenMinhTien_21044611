package iuh.fit.week5.backend.service;

import iuh.fit.week5.backend.entity.Company;
import iuh.fit.week5.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }
}
