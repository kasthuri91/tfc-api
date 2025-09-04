package com.tfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfc.model.Business;

@Repository
public interface BusinessRepo extends JpaRepository<Business, Long> {

	 Business findByToken(String token);
	
}
