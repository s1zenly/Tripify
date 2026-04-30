package com.startupsync.com.userservice.service.interfaces;



import com.startupsync.com.userservice.entity.Experience;

import java.util.List;

public interface ExperienceService {
	Experience createExperience(Experience experience);
	Experience findExperienceById(Long experienceId);
	List<Experience> findAllExperiences();
	Experience updateExperience(Experience experience);
	void deleteExperience(Long experienceId);
	List<Experience> getExperienceByUserId(Long userId);
}