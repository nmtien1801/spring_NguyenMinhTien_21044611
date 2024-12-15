package iuh.fit.week5.backend.service;

import iuh.fit.week5.backend.entity.CandidateSkill;
import iuh.fit.week5.backend.repository.CandidateSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateSkillService {
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    public List<CandidateSkill> findByCandidateId(Long id) {
        return candidateSkillRepository.findById_CanId(id);
    }

    public void delete(CandidateSkill candidateSkill) {
        candidateSkillRepository.delete(candidateSkill);
    }
}
