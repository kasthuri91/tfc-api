package com.tfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.model.Donation;
import com.tfc.model.Ngo;

public interface NgoRepo extends JpaRepository<Ngo, Long> {

}
