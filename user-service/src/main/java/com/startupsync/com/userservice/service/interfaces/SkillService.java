package com.startupsync.com.userservice.service.interfaces;



import com.startupsync.com.userservice.entity.Skill;

import java.util.List;

public interface SkillService {
	Skill createSkill(Skill skill);
	Skill findSkillById(Long skillId);
	List<Skill> findAllSkills();
	Skill updateSkill(Skill skill);
	void deleteSkill(Long skillId);
	List<Skill> getSkillsByUserId(Long userId);
}
