package com.startupsync.com.userservice.service;



import com.startupsync.com.userservice.entity.Experience;
import com.startupsync.com.userservice.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExperienceService implements com.startupsync.com.userservice.service.interfaces.ExperienceService {

	@Autowired
	private ExperienceRepository experienceRepository;

	@Override
	public Experience createExperience(Experience experience) {
		return experienceRepository.save(experience);
	}

	@Override
	public Experience findExperienceById(Long experienceId) {
		return experienceRepository.findById(experienceId).orElse(null);
	}

	@Override
	public List<Experience> findAllExperiences() {
		return experienceRepository.findAll();
	}

	@Override
	public Experience updateExperience(Experience experience) {
		return experienceRepository.save(experience);
	}

	@Override
	public void deleteExperience(Long experienceId) {
		experienceRepository.deleteById(experienceId);
	}

	@Override
	public List<Experience> getExperienceByUserId(Long userId) {
		return experienceRepository.findByUserId(userId);
	}

}

