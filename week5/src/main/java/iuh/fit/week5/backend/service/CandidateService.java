package iuh.fit.week5.backend.service;

import iuh.fit.week5.backend.entity.*;
import iuh.fit.week5.backend.repository.AddressRepository;
import iuh.fit.week5.backend.repository.CandidateRepository;
import iuh.fit.week5.backend.repository.CandidateSkillRepository;
import iuh.fit.week5.backend.repository.SkillRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    public void fakeDataCandidate() {
        Faker faker = new Faker();

        Address address = new Address();
        address.setStreet(faker.address().streetAddress());
        address.setCity(faker.address().city());
        address.setCountry((short) faker.number().numberBetween(1, 250)); // Assuming country is stored as a short
        address.setNumber(faker.address().buildingNumber());
        address.setZipcode(faker.address().zipCode());

        Candidate candidate = new Candidate();
        candidate.setDob(LocalDate.of(faker.number().numberBetween(1970, 2000), faker.number().numberBetween(1, 12), faker.number().numberBetween(1, 28)));
        candidate.setEmail(faker.internet().emailAddress());
        candidate.setFullName(faker.name().fullName());
        candidate.setPhone(faker.phoneNumber().cellPhone());
        candidate.setAddress(address);

        addressRepository.save(address);
        candidateRepository.save(candidate);
    }

    public Page<Candidate> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return (Page<Candidate>) candidateRepository.findAll(pageable);  //findFirst.../findTop...
    }


    public void userApply(Long jobId) {
        List<Skill> skill = skillRepository.findByJobSkills_Job_Id(jobId);

        Address address = new Address();
        address.setStreet("Nguyen Thai Son");
        address.setCity("Ho Chi Minh");
        address.setCountry((short) 84); // Assuming country is stored as a short
        address.setNumber("02447");
        address.setZipcode("91747");

        Candidate candidate = new Candidate();
        candidate.setDob(LocalDate.of(1999, 12, 12));
        candidate.setEmail("user@gmail.com");
        candidate.setFullName("User");
        candidate.setPhone("0123456789");
        candidate.setAddress(address);

        addressRepository.save(address);
        candidateRepository.save(candidate);

        // Tạo CandidateSkill
        for(Skill s : skill) {
            CandidateSkill candidateSkill = new CandidateSkill();
            CandidateSkillId candidateSkillId = new CandidateSkillId(candidate.getId(), s.getId()); // Composite Key
            candidateSkill.setId(candidateSkillId);
            candidateSkill.setCan(candidate);
            candidateSkill.setSkill(s);
            candidateSkill.setSkillLevel((byte) 3);
            candidateSkill.setMoreInfos("");
            candidateSkillRepository.save(candidateSkill);
        }
    }

    public Page<Candidate> findByFullName(String keyword, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return (Page<Candidate>) candidateRepository.findByFullName(keyword, pageable);
    }

    public Page<Candidate> findBySkillName(String keyword, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return (Page<Candidate>) candidateRepository.findBySkillName(keyword, pageable);
    }

    public Page<Candidate> findByFullNameAndSkillName(String query, String keyword, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return (Page<Candidate>) candidateRepository.findByFullNameAndSkillName(query, keyword, pageable);
    }

    public Candidate findById(long id) {
        try {
            return candidateRepository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public void delete(Candidate product) {
        candidateRepository.delete(product);
    }



}
