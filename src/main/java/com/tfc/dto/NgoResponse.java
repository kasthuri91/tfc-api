package com.tfc.dto;

import java.util.Date;
import java.util.List;

public class NgoResponse {

	private long id;
	private String name;
	private String sector;
	private String solution;
	private String link;
	private Date updatedAt;
	private Date createdAt;
	private List<ProjectNgoResponse> projectList;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	public List<ProjectNgoResponse> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<ProjectNgoResponse> projectList) {
		this.projectList = projectList;
	}
	
}
