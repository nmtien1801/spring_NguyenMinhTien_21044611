package iuh.fit.week5;

import com.neovisionaries.i18n.CountryCode;
import iuh.fit.week5.backend.entity.Address;
import iuh.fit.week5.backend.entity.Candidate;
import iuh.fit.week5.backend.repository.AddressRepository;
import iuh.fit.week5.backend.repository.CandidateRepository;
import iuh.fit.week5.backend.service.CandidateService;
import iuh.fit.week5.backend.service.JobService;
import iuh.fit.week5.backend.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
public class Week5Application {

    public static void main(String[] args) {
        SpringApplication.run(Week5Application.class, args);
    }


    // tạo data mẫu
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JobService jobService;

    @Autowired
    private SkillService skillService;

    @Bean
    CommandLineRunner dataFaker() {
        return args -> {
            for (int i = 1; i <= 10; i++) { // Vòng lặp từ 1 đến 10
                candidateService.fakeDataCandidate(); // Gọi phương thức giả lập dữ liệu
                jobService.fakeDataJob(); // Gọi phương thức giả lập dữ liệu
                skillService.fakeDataSkill(); // Gọi phương thức giả lập dữ liệu
            }
            System.out.println("Data generation completed.");
        };
    }

}
