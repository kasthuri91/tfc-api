package com.tfc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.model.Ngo;
import com.tfc.model.Project;

public interface ProjectRepo  extends JpaRepository<Project, Long> {

}
