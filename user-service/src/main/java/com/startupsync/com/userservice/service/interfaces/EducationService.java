package com.startupsync.com.userservice.service.interfaces;



import com.startupsync.com.userservice.entity.Education;

import java.util.List;

public interface EducationService {
	Education createEducation(Education education);
	Education findEducationById(Long educationId);
	List<Education> findAllEducations();
	Education updateEducation(Education education);
	void deleteEducation(Long educationId);
	List<Education> getEducationByUserId(Long userId);
}