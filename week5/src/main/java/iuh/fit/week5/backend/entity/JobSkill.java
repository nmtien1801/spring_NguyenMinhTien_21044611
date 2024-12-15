package iuh.fit.week5.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "job_skill")
@NamedQueries({
        @NamedQuery(name = "JobSkill.findByJob_IdAndSkill_Id", query = "select j from JobSkill j where j.job.id = :id1 and j.skill.id = :id2"),
        @NamedQuery(name = "JobSkill.findByJob_Id", query = "select j from JobSkill j where j.job.id = :jobId")
})

public class JobSkill {
    @EmbeddedId
    private JobSkillId id;

    @MapsId("jobId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    @ToString.Exclude
    private Job job;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    @ToString.Exclude
    private Skill skill;

    @Column(name = "more_infos", length = 1000)
    private String moreInfos;

    @Column(name = "skill_level", nullable = false)
    private Byte skillLevel;

}