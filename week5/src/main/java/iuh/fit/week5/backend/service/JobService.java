package iuh.fit.week5.backend.service;

import iuh.fit.week5.backend.entity.*;
import iuh.fit.week5.backend.repository.AddressRepository;
import iuh.fit.week5.backend.repository.JobRepository;
import iuh.fit.week5.backend.repository.CompanyRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    // tạo faker data job
    public void fakeDataJob() {
        Faker faker = new Faker();

        // Tạo đối tượng Job
        Job job = new Job();
        job.setJobName(faker.job().title());  // Lấy tên công việc giả
        job.setJobDesc(faker.lorem().paragraph(3));  // Lấy mô tả công việc giả

        // Tạo đối tượng Company
        Company company = new Company();
        company.setAbout(faker.lorem().paragraph(3));  // Giới thiệu công ty giả
        company.setEmail(faker.internet().emailAddress());  // Email công ty giả
        company.setCompName(faker.company().name());  // Tên công ty giả
        company.setPhone(faker.phoneNumber().cellPhone());  // Số điện thoại công ty giả
        company.setWebUrl(faker.internet().url());  // URL website công ty giả

        // taọ đối tượng Address
        Address address = new Address();
        address.setStreet(faker.address().streetAddress());
        address.setCity(faker.address().city());
        address.setCountry((short) faker.number().numberBetween(1, 250)); // Assuming country is stored as a short
        address.setNumber(faker.address().buildingNumber());
        address.setZipcode(faker.address().zipCode());

        company.setAddress(address);  // Lưu địa chỉ công ty vào đối tượng Company
        job.setCompany(company);  // Lưu công ty vào công việc

        // Lưu Job vào cơ sở dữ liệu
        addressRepository.save(address);
        companyRepository.save(company);
        jobRepository.save(job);

    }


    public Page<Job> findAll(int pageNo, int pageSize, String sortBy,
                                   String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return (Page<Job>) jobRepository.findAll(pageable);  //findFirst.../findTop...
    }


    public Page<Job> findBySkillName(String keyword, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return (Page<Job>) jobRepository.findBySkillName(keyword, pageable);
    }

    public void save(Job job) {
        jobRepository.save(job);
    }

    public Job findById(long jobId) {
        return jobRepository.findById(jobId).get();
    }

    public void delete(Job job) {
        jobRepository.delete(job);
    }
}
