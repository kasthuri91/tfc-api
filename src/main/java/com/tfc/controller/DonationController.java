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

import com.tfc.dto.AddDonationRequest;
import com.tfc.dto.BusinessDnResponse;
import com.tfc.dto.BusinessResponse;
import com.tfc.dto.DonationBusinsessResponse;
import com.tfc.dto.DonationResponse;
import com.tfc.dto.ProjectNgoResponse;
import com.tfc.dto.StatusUpdateResponse;
import com.tfc.dto.UpdateDonationRequest;
import com.tfc.exception.ApplicationException;
import com.tfc.exception.CustomErrorResponse;
import com.tfc.exception.DataNotFoundException;
import com.tfc.model.Business;
import com.tfc.model.Donation;
import com.tfc.model.Project;
import com.tfc.repository.BusinessRepo;
import com.tfc.repository.DonationRepo;
import com.tfc.repository.ProjectRepo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/mvp-api")
@ApiOperation(value = "/mvp-api", tags = "Donation Controller", notes = "TFC Donation  API")
public class DonationController {

	@Autowired
	DonationRepo repoDtn;
	
	@Autowired
	BusinessRepo repoBns;
	
	@Autowired
	ProjectRepo repoPro;

	@Autowired
	Donation donation;
	
	@Autowired
	Business business;
	
	@Autowired
	Project project;
	
	@ApiOperation(value = "Fetch All Donations")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = DonationResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@GetMapping(path = "/donation")
	public List<DonationResponse> geDonations(@RequestHeader("api-key") String key) throws DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(repoDtn.findAll().isEmpty()) {
			throw new DataNotFoundException("No record found");
		}
		
		return _generateDonationRes(repoDtn.findAll());
	}
	
	@ApiOperation(value = "Fetch Donation by ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = DonationResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@GetMapping("/donation/{id}")
	public DonationResponse getBusiness(@RequestHeader("api-key") String key , @PathVariable("id") long id) throws DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if (!repoDtn.findById(id).isPresent()) {
			throw new DataNotFoundException("Donation not found , id : " + id);
		}
		
		return _generateDonationRes(repoDtn.getOne(id));
	}

	@ApiOperation(value = "Add new Donation")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@PostMapping(path = "/donation", consumes = { "application/json" })
	public StatusUpdateResponse addDonation(@RequestHeader("api-key") String key,@RequestBody AddDonationRequest req) throws ApplicationException, DataNotFoundException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(!repoBns.findById(req.getBusinessId()).isPresent()) {
			throw new  DataNotFoundException("Business not found , id : "+req.getBusinessId());
		}
		if(!repoPro.findById(req.getProjectId()).isPresent()) {
			throw new  DataNotFoundException("Project not found , id : "+req.getProjectId());
		}
		
		try {
			
			project=repoPro.getOne(req.getProjectId());
			business=repoBns.getOne(req.getBusinessId());
			
			donation.setAmount(req.getAmount());
			donation.setValidated(req.isValidated());
			donation.setCreatedAt(new Date());
			donation.setBusiness(business);
			donation.setProject(project);
			donation.setProjectNgoId(project.getNgo().getId());
			donation=repoDtn.save(donation);
			
		}catch(Exception e) {
			System.out.println("Error : "+e);
			throw new  ApplicationException();
		}
		
		return _generateResponseDataUpdate(donation.getId());
	}

	@ApiOperation(value = "Update added Donation with ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@PutMapping(path = "/donation", consumes = { "application/json" })
	public StatusUpdateResponse updateDonation(@RequestHeader("api-key") String key,@RequestBody UpdateDonationRequest req) throws DataNotFoundException, ApplicationException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(!repoDtn.findById(req.getId()).isPresent()) {
			throw new  DataNotFoundException("Donation not found , id : "+req.getId());
		}
		
		try {
			
			donation=repoDtn.getOne(req.getId());
			donation.setAmount(req.getAmount());
			//donation.setBusinessId(req.getBusinessId());
			donation.setValidated(req.isValidated());
			donation.setUpdatedAt(new Date());
			repoDtn.save(donation);
		
		}catch(Exception e) {
			throw new  ApplicationException();
		}
		
		return _generateResponseDataUpdate(donation.getId());
	}

	@ApiOperation(value = "Delete Donation by ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@DeleteMapping("/donation/{id}")
	public StatusUpdateResponse deleteDonation(@RequestHeader("api-key") String key,@PathVariable long id) throws DataNotFoundException, ApplicationException {
		
		Business b=null;
		b=repoBns.findByToken(key);
		
		if(b==null) {
			throw new  DataNotFoundException("Business not found , key : "+key);
		}
		
		if(!repoDtn.findById(id).isPresent()) {
			throw new  DataNotFoundException("Donation id not found , id : "+id);
		}
		
		try {
			donation=repoDtn.getOne(id);
			repoDtn.delete(donation);
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
	
	private DonationResponse _generateDonationRes(Donation donation) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		DonationResponse res = modelMapper.map(donation, DonationResponse.class);

		BusinessDnResponse bres = modelMapper.map(donation.getBusiness(), BusinessDnResponse.class);
		res.setBusiness(bres);
		
		ProjectNgoResponse pres = modelMapper.map(donation.getProject(), ProjectNgoResponse.class);
		res.setProject(pres);
		
		return res;
	}
	
	private List<DonationResponse> _generateDonationRes(List<Donation> donation) {

		List<DonationResponse> donLs=new ArrayList<DonationResponse>();
		
		for(Donation d:donation) {
			
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			DonationResponse res = modelMapper.map(d, DonationResponse.class);

			BusinessDnResponse bres = modelMapper.map(d.getBusiness(), BusinessDnResponse.class);
			res.setBusiness(bres);
			
			ProjectNgoResponse pres = modelMapper.map(d.getProject(), ProjectNgoResponse.class);
			res.setProject(pres);
			
			donLs.add(res);
		}
		return donLs;
	}
}
