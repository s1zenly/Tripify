package com.startupsync.com.userservice.service;



import com.startupsync.com.userservice.entity.Education;
import com.startupsync.com.userservice.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EducationService implements com.startupsync.com.userservice.service.interfaces.EducationService {

	@Autowired
	private EducationRepository educationRepository;

	@Override
	public Education createEducation(Education education) {
		return educationRepository.save(education);
	}

	@Override
	public Education findEducationById(Long educationId) {
		return educationRepository.findById(educationId).orElse(null);
	}

	@Override
	public List<Education> findAllEducations() {
		return educationRepository.findAll();
	}

	@Override
	public Education updateEducation(Education education) {
		return educationRepository.save(education);
	}

	@Override
	public void deleteEducation(Long educationId) {
		educationRepository.deleteById(educationId);
	}

	@Override
	public List<Education> getEducationByUserId(Long userId) {
		return educationRepository.findByUserId(userId);
	}

}

