package iuh.fit.week5.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate")
@NamedQueries({
        @NamedQuery(name = "Candidate.findByFullName", query = "select c from Candidate c where c.fullName LIKE CONCAT('%', :fullName, '%')"),
        @NamedQuery(name = "Candidate.findAll", query = "select c from Candidate c"),
        @NamedQuery(name = "Candidate.findById", query = "select c from Candidate c where c.id = :id"),
        @NamedQuery(name = "Candidate.findBySkillName", query = "select c from Candidate c inner join c.candidateSkills candidateSkills where candidateSkills.skill.skillName = :skillName"),
        @NamedQuery(name = "Candidate.findByFullNameAndSkillName", query = "select c from Candidate c inner join c.candidateSkills candidateSkills where c.fullName LIKE CONCAT('%', :fullName, '%') and candidateSkills.skill.skillName = :skillName")

})

public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address", nullable = false)
    @ToString.Exclude
    private Address address;

    @OneToMany(mappedBy = "can")
    @ToString.Exclude
    private Set<CandidateSkill> candidateSkills = new LinkedHashSet<>();

    public Candidate(String s, LocalDate of, Address add, String s1, String s2) {
    }
}