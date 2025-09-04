package com.tfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.model.Donation;

public interface DonationRepo extends JpaRepository<Donation, Long> {
}
