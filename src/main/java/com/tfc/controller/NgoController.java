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

import com.tfc.dto.AddBusinessRequest;
import com.tfc.dto.AddNgoRequest;
import com.tfc.dto.BusinessResponse;
import com.tfc.dto.DonationBusinsessResponse;
import com.tfc.dto.NgoResponse;
import com.tfc.dto.ProjectNgoResponse;
import com.tfc.dto.StatusUpdateResponse;
import com.tfc.dto.UpdateBusinessRequest;
import com.tfc.dto.UpdateNgoRequest;
import com.tfc.exception.ApplicationException;
import com.tfc.exception.CustomErrorResponse;
import com.tfc.exception.DataNotFoundException;
import com.tfc.model.Business;
import com.tfc.model.Donation;
import com.tfc.model.Ngo;
import com.tfc.model.Project;
import com.tfc.repository.BusinessRepo;
import com.tfc.repository.NgoRepo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/mvp-api")
@ApiOperation(value = "/mvp-api", tags = "Ngo Controller", notes = "TFC Business  API")
public class NgoController {

	@Autowired
	NgoRepo repo;

	@Autowired
	Ngo ngo;
	
	@Autowired
	BusinessRepo repoBns;
	
	@ApiOperation(value = "Fetch All Ngos")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = NgoResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@GetMapping(path = "/ngo")
	public List<NgoResponse> getNgo(@RequestHeader("api-key") String key) throws DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(repo.findAll().isEmpty()) {
			throw new DataNotFoundException("No record found");
		}
		
		return _generateNgoRes(repo.findAll());
	}
	
	@ApiOperation(value = "Fetch Ngo with ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = NgoResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@GetMapping("/ngo/{id}")
	public NgoResponse getNgo(@RequestHeader("api-key") String key,@PathVariable("id") long id) throws DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if (!repo.findById(id).isPresent()) {
			throw new DataNotFoundException("Business not found , id : " + id);
		}
		
		return _generateNgoRes(repo.getOne(id));
	
	}
	
	@ApiOperation(value = "Add new Ngo")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@PostMapping(path = "/ngo", consumes = { "application/json" })
	public StatusUpdateResponse addNgo(@RequestHeader("api-key") String key,@RequestBody AddNgoRequest req) throws ApplicationException, DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		try {
			ngo.setSector(req.getSector());
			ngo.setLink(req.getLink());
			ngo.setName(req.getName());
			ngo.setSolution(req.getSolution());
			ngo.setCreatedAt(new Date());
			ngo=repo.save(ngo);
		}catch(Exception e) {
			throw new  ApplicationException();
		}
		
		return _generateResponseDataUpdate(ngo.getId());
	}

	@ApiOperation(value = "Update Ngo by ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@PutMapping(path = "/ngo", consumes = { "application/json" })
	public StatusUpdateResponse updateNgo(@RequestHeader("api-key") String key,@RequestBody UpdateNgoRequest req) throws DataNotFoundException, ApplicationException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(!repo.findById(req.getId()).isPresent()) {
			throw new  DataNotFoundException("Ngo not found , id : "+req.getId());
		}
		
		try {
			
			ngo=repo.getOne(req.getId());
			ngo.setSector(req.getSector());
			ngo.setLink(req.getLink());
			ngo.setName(req.getName());
			ngo.setSolution(req.getSolution());
			ngo.setUpdatedAt(new Date());
			repo.save(ngo);
		
		}catch(Exception e) {
			throw new  ApplicationException();
		}
		
		return _generateResponseDataUpdate(ngo.getId());
	}

	@ApiOperation(value = "Delete Ngo by ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@DeleteMapping("/ngo/{id}")
	public StatusUpdateResponse deleteNgo(@RequestHeader("api-key") String key,@PathVariable long id) throws DataNotFoundException, ApplicationException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(!repo.findById(id).isPresent()) {
			throw new  DataNotFoundException("Ngo not found , id : "+id);
		}
		
		try {
			ngo=repo.getOne(id);
			repo.delete(ngo);
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

	private NgoResponse _generateNgoRes(Ngo ngo) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		NgoResponse res = modelMapper.map(ngo, NgoResponse.class);
		
		List<ProjectNgoResponse> ngoProjectLs = new ArrayList<ProjectNgoResponse>();

		for (Project dz : ngo.getProjectList()) {
			ModelMapper modelMapper2 = new ModelMapper();
			modelMapper2.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			ProjectNgoResponse ngoProject = modelMapper2.map(dz, ProjectNgoResponse.class);
			ngoProjectLs.add(ngoProject);
		}

		return res;
	}

	private List<NgoResponse> _generateNgoRes(List<Ngo> ngo) {

		List<NgoResponse> ngosLs=new ArrayList<NgoResponse>();
		
		for(Ngo no:ngo) {
			
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			NgoResponse res = modelMapper.map(no, NgoResponse.class);
			
			List<ProjectNgoResponse> ngoProjectLs = new ArrayList<ProjectNgoResponse>();

			for (Project dz : no.getProjectList()) {
				ModelMapper modelMapper2 = new ModelMapper();
				modelMapper2.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				ProjectNgoResponse ngoProject = modelMapper2.map(dz, ProjectNgoResponse.class);
				ngoProjectLs.add(ngoProject);
			}
			ngosLs.add(res);
		}
		
		
		return ngosLs;
	}
	
}
