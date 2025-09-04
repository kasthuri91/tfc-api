package com.tfc.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Donation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private BigDecimal amount;
	private boolean validated;
	
	@Column(name="updated_at")
	private Date updatedAt;
	
	@Column(name="created_at")
	private Date createdAt;
	
	//@Column(name="business_id")
	//private long businessId;
	
	@Column(name="project_ngo_id")
	private long projectNgoId;
	
	@ManyToOne
	@JoinColumn(name = "business_id", nullable = false)
	private Business business;
	
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public long getProjectNgoId() {
		return projectNgoId;
	}

	public void setProjectNgoId(long projectNgoId) {
		this.projectNgoId = projectNgoId;
	}
	
	
}
