package com.tfc.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tfc.model.Business;
import com.tfc.model.Project;

public class DonationResponse {

	private long id;
	private BigDecimal amount;
	private boolean validated;
	private Date updatedAt;
	private Date createdAt;
	private long projectNgoId;
	private BusinessDnResponse business;
	private ProjectNgoResponse project;
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
	public long getProjectNgoId() {
		return projectNgoId;
	}
	public void setProjectNgoId(long projectNgoId) {
		this.projectNgoId = projectNgoId;
	}
	public BusinessDnResponse getBusiness() {
		return business;
	}
	public void setBusiness(BusinessDnResponse business) {
		this.business = business;
	}
	public ProjectNgoResponse getProject() {
		return project;
	}
	public void setProject(ProjectNgoResponse project) {
		this.project = project;
	}
	
	
}
