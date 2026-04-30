package com.startupsync.com.userservice.controller;



import com.startupsync.com.userservice.entity.Skill;
import com.startupsync.com.userservice.service.interfaces.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/skill")
public class SkillController {

	@Autowired
	private SkillService skillService;

	@PostMapping("/create")
	public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
		Skill resp = skillService.createSkill(skill);
		return new ResponseEntity<Skill>(resp, HttpStatus.CREATED);
	}

	@GetMapping("/{skillId}")
	public ResponseEntity<Skill> getSkillById(@PathVariable Long skillId) {
		Skill resp = skillService.findSkillById(skillId);
		return new ResponseEntity<Skill>(resp, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Skill>> getAllSkills() {
		List<Skill> resp = skillService.findAllSkills();
		return new ResponseEntity<List<Skill>>(resp, HttpStatus.OK);
	}
	@PutMapping("/update")
	public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill) {
		Skill resp = skillService.updateSkill(skill);
		return new ResponseEntity<Skill>(resp, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{skillId}")
	public ResponseEntity<Long> deleteSkill(@PathVariable Long skillId) {
		skillService.deleteSkill(skillId);
		return new ResponseEntity<Long>(skillId, HttpStatus.NO_CONTENT);
	}

	@GetMapping("user/{userId}")
	public ResponseEntity<List<Skill>> getSkillsByUserId(@PathVariable("userId") Long userId){

		List<Skill> skill = skillService.getSkillsByUserId(userId);
		return new ResponseEntity<List<Skill>>(skill, HttpStatus.OK);

	}

}


