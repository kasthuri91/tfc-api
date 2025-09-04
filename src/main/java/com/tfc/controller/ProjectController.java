package com.tfc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.dto.AddNgoRequest;
import com.tfc.dto.AddProjectRequest;
import com.tfc.dto.BusinessResponse;
import com.tfc.dto.DonationBusinsessResponse;
import com.tfc.dto.ProjectNgoResponse;
import com.tfc.dto.ProjectResponse;
import com.tfc.dto.StatusUpdateResponse;
import com.tfc.dto.UpdateNgoRequest;
import com.tfc.dto.UpdateProjectRequest;
import com.tfc.exception.ApplicationException;
import com.tfc.exception.CustomErrorResponse;
import com.tfc.exception.DataNotFoundException;
import com.tfc.model.Business;
import com.tfc.model.Donation;
import com.tfc.model.Ngo;
import com.tfc.model.Project;
import com.tfc.repository.BusinessRepo;
import com.tfc.repository.NgoRepo;
import com.tfc.repository.ProjectRepo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/mvp-api")
@ApiOperation(value = "/mvp-api", tags = "Business Controller", notes = "TFC Business  API")
public class ProjectController {

	@Autowired
	ProjectRepo proRepo;
	
	@Autowired
	NgoRepo ngoRepo;

	@Autowired
	Project project;
	
	@Autowired
	Ngo ngo;
	
	@Autowired
	BusinessRepo repoBns;
	
	@ApiOperation(value = "Fetch All Projects")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = ProjectResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@GetMapping(path = "/project")
	public List<ProjectResponse> getProject(@RequestHeader("api-key") String key) throws DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(proRepo.findAll().isEmpty()) {
			throw new DataNotFoundException("No record found");
		}
		
		return _generateProjectRes(proRepo.findAll());
	}
	
	@ApiOperation(value = "Fetch project with ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = ProjectResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@GetMapping("/project/{id}")
	public ProjectResponse getProject(@RequestHeader("api-key") String key,@PathVariable("id") long id) throws DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if (!proRepo.findById(id).isPresent()) {
			throw new DataNotFoundException("Business not found , id : " + id);
		}
		
		return _generateProjectRes(proRepo.getOne(id));
	
	}
	
	@ApiOperation(value = "Add new project")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@PostMapping(path = "/project", consumes = { "application/json" })
	public StatusUpdateResponse addProject(@RequestHeader("api-key") String key,@RequestBody AddProjectRequest req) throws ApplicationException, DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(!ngoRepo.existsById(req.getNgoId())) {
			throw new DataNotFoundException("Ngo not found, id : "+req.getNgoId());
		}
		
		try {
			ngo=ngoRepo.getOne(req.getNgoId());
			project.setLink(req.getLink());
			project.setName(req.getName());
			project.setCreatedAt(new Date());
			project.setCostUnit(req.getCostUnit());
			project.setLocation(req.getLocation());
			project.setNgo(ngo);
			proRepo.save(project);
		}catch(Exception e) {
			System.out.println("Error : "+e);
			throw new  ApplicationException();
		}
		
		return _generateResponseDataUpdate(ngo.getId());
	}

	@ApiOperation(value = "Updated added project with ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@PutMapping(path = "/project", consumes = { "application/json" })
	public StatusUpdateResponse updateProject(@RequestHeader("api-key") String key,@RequestBody UpdateProjectRequest req) throws DataNotFoundException, ApplicationException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(!proRepo.existsById(req.getId())) {
			throw new DataNotFoundException("Project not found, id : "+req.getId());
		}
		if(!ngoRepo.existsById(req.getNgoId())) {
			throw new DataNotFoundException("Ngo not found, id : "+req.getNgoId());
		}
		
		try {
			
			ngo=ngoRepo.getOne(req.getNgoId());
			project=proRepo.getOne(req.getId());
			project.setLink(req.getLink());
			project.setName(req.getName());
			project.setCostUnit(req.getCostUnit());
			project.setLocation(req.getLocation());
			project.setNgo(ngo);
			project.setUpdatedAt(new Date());
			proRepo.save(project);
			
		}catch(Exception e) {
			throw new  ApplicationException();
		}
		
		return _generateResponseDataUpdate(ngo.getId());
	}

	@ApiOperation(value = "Delete project by ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@DeleteMapping("/project/{id}")
	public StatusUpdateResponse deleteProject(@RequestHeader("api-key") String key,@PathVariable long id) throws DataNotFoundException, ApplicationException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(!proRepo.findById(id).isPresent()) {
			throw new  DataNotFoundException("Project not found , id : "+id);
		}
		
		try {
			project=proRepo.getOne(id);
			proRepo.delete(project);
		}catch(Exception e) {
			throw new  ApplicationException();
		}
		return _generateResponseDataUpdate(id);
	}

	private StatusUpdateResponse _generateResponseDataUpdate(long id) {

		StatusUpdateResponse res = new StatusUpdateResponse();

		if (id!=0) {
			res.setId(id);
			res.setStatus(true);
		}

		return res;
	}

	private ProjectResponse _generateProjectRes(Project project) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ProjectResponse res = modelMapper.map(project, ProjectResponse.class);
		ProjectNgoResponse ngoRes=modelMapper.map(project.getNgo(), ProjectNgoResponse.class);
		res.setNgo(ngoRes);
		
		List<DonationBusinsessResponse> dresLs = new ArrayList<DonationBusinsessResponse>();

		for (Donation dz : project.getDonationList()) {
			ModelMapper modelMapper2 = new ModelMapper();
			modelMapper2.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			DonationBusinsessResponse dres = modelMapper2.map(dz, DonationBusinsessResponse.class);
			dresLs.add(dres);
		}

		return res;
	}

	private List<ProjectResponse> _generateProjectRes(List<Project> project) {

		List<ProjectResponse> proLs=new ArrayList<ProjectResponse>();
		
		for(Project p:project) {
			
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			ProjectResponse res = modelMapper.map(p, ProjectResponse.class);
			ProjectNgoResponse ngoRes=modelMapper.map(p.getNgo(), ProjectNgoResponse.class);
			res.setNgo(ngoRes);
			
			List<DonationBusinsessResponse> dresLs = new ArrayList<DonationBusinsessResponse>();

			for (Donation dz : p.getDonationList()) {
				ModelMapper modelMapper2 = new ModelMapper();
				modelMapper2.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				DonationBusinsessResponse dres = modelMapper2.map(dz, DonationBusinsessResponse.class);
				dresLs.add(dres);
			}

			proLs.add(res);
		}
		
		return proLs;
	}
	
	
}
