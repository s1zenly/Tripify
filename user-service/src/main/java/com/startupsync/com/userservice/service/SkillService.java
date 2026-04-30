package com.startupsync.com.userservice.service;



import com.startupsync.com.userservice.entity.Skill;
import com.startupsync.com.userservice.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class SkillService implements com.startupsync.com.userservice.service.interfaces.SkillService {

	@Autowired
	private SkillRepository skillRepository;

	@Override
	public Skill createSkill(Skill skill) {
		return skillRepository.save(skill);
	}

	@Override
	public Skill findSkillById(Long skillId) {
		return skillRepository.findById(skillId).orElse(null);
	}

	@Override
	public List<Skill> findAllSkills() {
		return skillRepository.findAll();
	}

	@Override
	public Skill updateSkill(Skill skill) {
		return skillRepository.save(skill);
	}

	@Override
	public void deleteSkill(Long skillId) {
		skillRepository.deleteById(skillId);
	}

	@Override
	public List<Skill> getSkillsByUserId(Long userId) {
		return skillRepository.findByUserId(userId);
	}
}
