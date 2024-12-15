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
@ToString
@Table(name = "skill")
@NamedQueries({
        @NamedQuery(name = "Skill.findByJobSkills_Job_Id", query = "select s from Skill s inner join s.jobSkills jobSkills where jobSkills.job.id = :id")
})
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private Long id;

    @Column(name = "skill_description")
    private String skillDescription;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "type")
    private Byte type;

    @OneToMany(mappedBy = "skill")
    @ToString.Exclude
    private Set<CandidateSkill> candidateSkills = new LinkedHashSet<>();

    @OneToMany(mappedBy = "skill")
    @ToString.Exclude
    private Set<JobSkill> jobSkills = new LinkedHashSet<>();

}