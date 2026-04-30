package com.startupsync.com.userservice.repository;


import com.startupsync.com.userservice.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
	List<Experience> findByUserId(Long userId);
}
