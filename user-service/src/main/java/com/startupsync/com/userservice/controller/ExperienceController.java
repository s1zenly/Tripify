package com.startupsync.com.userservice.controller;



import com.startupsync.com.userservice.entity.Experience;
import com.startupsync.com.userservice.service.interfaces.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/experience")
public class ExperienceController {

	@Autowired
	private ExperienceService experienceService;

	@PostMapping("/create")
	public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) {
		Experience resp = experienceService.createExperience(experience);
		return new ResponseEntity<Experience>(resp, HttpStatus.CREATED);
	}

	@GetMapping("/{experienceId}")
	public ResponseEntity<Experience> getExperienceById(@PathVariable Long experienceId) {
		Experience resp = experienceService.findExperienceById(experienceId);
		return new ResponseEntity<Experience>(resp, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Experience>> getAllExperiences() {
		List<Experience> resp = experienceService.findAllExperiences();
		return new ResponseEntity<List<Experience>>(resp, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Experience> updateExperience(@RequestBody Experience experience) {
		Experience resp = experienceService.updateExperience(experience);
		return new ResponseEntity<Experience>(resp, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{experienceId}")
	public ResponseEntity<Long> deleteExperience(@PathVariable Long experienceId) {
		experienceService.deleteExperience(experienceId);
		return new ResponseEntity<Long>(experienceId, HttpStatus.NO_CONTENT);
	}

	@GetMapping("user/{userId}")
	public ResponseEntity<List<Experience>> getExperienceByUserId(@PathVariable("userId") Long userId){
		List<Experience> experience = experienceService.getExperienceByUserId(userId);
		return new ResponseEntity<List<Experience>>(experience, HttpStatus.OK);

	}

}


