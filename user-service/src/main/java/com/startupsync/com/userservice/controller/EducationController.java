package com.startupsync.com.userservice.controller;



import com.startupsync.com.userservice.entity.Education;
import com.startupsync.com.userservice.service.interfaces.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/education")
public class EducationController {

	@Autowired
	private EducationService educationService;

	@PostMapping("/create")
	public ResponseEntity<Education> createEducation(@RequestBody Education education) {
		Education resp = educationService.createEducation(education);
		return new ResponseEntity<Education>(resp, HttpStatus.CREATED);
	}

	@GetMapping("/{educationId}")
	public ResponseEntity<Education> getEducationById(@PathVariable Long educationId) {
		Education resp = educationService.findEducationById(educationId);
		return new ResponseEntity<Education>(resp, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Education>> getAllEducations() {
		List<Education> resp = educationService.findAllEducations();
		return new ResponseEntity<List<Education>>(resp, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Education> updateEducation(@RequestBody Education education) {
		Education resp = educationService.updateEducation(education);
		return new ResponseEntity<Education>(resp, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{educationId}")
	public ResponseEntity<Long> deleteEducation(@PathVariable Long educationId) {
		educationService.deleteEducation(educationId);
		return new ResponseEntity<Long>(educationId, HttpStatus.NO_CONTENT);
	}
	@GetMapping("user/{userId}")
	public ResponseEntity<List<Education>> getEducationByUserId(@PathVariable("userId") Long userId){
		List<Education> eduation = educationService.getEducationByUserId(userId);
		return new ResponseEntity<List<Education>>(eduation, HttpStatus.OK);

	}

}

