package iuh.fit.week5.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "job")
@ToString
@NamedQueries({
        @NamedQuery(name = "Job.findBySkillName", query = "select j from Job j inner join j.jobSkills jobSkills where jobSkills.skill.skillName = :skillName")
})

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private Long id;

    @Column(name = "job_desc", nullable = false, length = 2000)
    private String jobDesc;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company")
    @ToString.Exclude
    private Company company;

    @OneToMany(mappedBy = "job")
    @ToString.Exclude
    private Set<JobSkill> jobSkills = new LinkedHashSet<>();

}