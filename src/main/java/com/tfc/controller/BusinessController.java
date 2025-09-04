package com.tfc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.uuid.Generators;
import com.tfc.dto.AddBusinessRequest;
import com.tfc.dto.BusinessResponse;
import com.tfc.dto.DonationBusinsessResponse;
import com.tfc.dto.StatusUpdateResponse;
import com.tfc.dto.UpdateBusinessRequest;
import com.tfc.exception.ApplicationException;
import com.tfc.exception.CustomErrorResponse;
import com.tfc.exception.DataNotFoundException;
import com.tfc.exception.InvalidDataException;
import com.tfc.model.Business;
import com.tfc.model.Donation;
import com.tfc.repository.BusinessRepo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/mvp-api")
@ApiOperation(value = "/mvp-api", tags = "Business Controller", notes = "TFC Business  API")
public class BusinessController {
	@Autowired
	BusinessRepo repo;

	@Autowired
	Business business;
	
	@ApiOperation(value = "Fetch All Business")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = BusinessResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@GetMapping(path = "/business")
	public List<BusinessResponse> getBusinesses() throws DataNotFoundException {

		if (repo.findAll().isEmpty()) {
			throw new DataNotFoundException("No record found");
		}

		return _generateBusinessRes(repo.findAll());
		
	}

	@ApiOperation(value = "Fetch Business by ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = BusinessResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@GetMapping("/business/{id}")
	public BusinessResponse getBusiness(@PathVariable("id") long id) throws DataNotFoundException {

		 //return repo.findById(id)
		// .orElseThrow(() -> new DataNotFoundException("Business not found , id : "+id));

		if (!repo.findById(id).isPresent()) {
			throw new DataNotFoundException("Business not found , id : " + id);
		}
		
		return _generateBusinessRes(repo.getOne(id));
	}
	
	@ApiOperation(value = "Add new Business")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@PostMapping(path = "/business", consumes = { "application/json" })
	public StatusUpdateResponse addBusiness(@RequestBody AddBusinessRequest req) throws ApplicationException {

		try {
			business.setEmail(req.getEmail());
			business.setLink(req.getLink());
			business.setName(req.getName());
			//business.setToken(req.getToken());
			business.setCreatedAt(new Date());
			UUID uuid = Generators.timeBasedGenerator().generate();
			business.setToken(uuid.toString());
			business=repo.save(business);
		} catch (Exception e) {
			throw new ApplicationException();
		}

		return _generateResponseDataUpdate(business.getId());
	}

	@ApiOperation(value = "Update added Business with ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@PutMapping(path = "/business", consumes = { "application/json" })
	public StatusUpdateResponse updateBusiness(@RequestBody UpdateBusinessRequest req)
			throws DataNotFoundException, ApplicationException {

		if (!repo.findById(req.getId()).isPresent()) {
			throw new DataNotFoundException("Business not found , id : " + req.getId());
		}

		try {

			business = repo.getOne(req.getId());
			business.setEmail(req.getEmail());
			business.setLink(req.getLink());
			business.setName(req.getName());
			//business.setToken(req.getToken());
			business.setUpdatedAt(new Date());
			repo.save(business);

		} catch (Exception e) {
			throw new ApplicationException();
		}

		return _generateResponseDataUpdate(business.getId());
	}

	@ApiOperation(value = "Delete adedd Business by ID")
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "SUCCESS", response = StatusUpdateResponse.class),
					@ApiResponse(code = 404, message = "NOT FOUND!", response = CustomErrorResponse.class),
					@ApiResponse(code = 403, message = "FORBIDDEN!", response = CustomErrorResponse.class),
					@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR!", response=CustomErrorResponse.class)
	})
	@DeleteMapping("/business/{id}")
	public StatusUpdateResponse deleteBusiness(@PathVariable long id)
			throws DataNotFoundException, ApplicationException {

		if (!repo.findById(id).isPresent()) {
			throw new DataNotFoundException("Business not found , id : " + id);
		}

		try {
			Business b = repo.getOne(id);
			repo.delete(b);
		} catch (Exception e) {
			throw new ApplicationException();
		}
		return _generateResponseDataUpdate(id);

	}

	private StatusUpdateResponse _generateResponseDataUpdate(long id) {

		StatusUpdateResponse res = new StatusUpdateResponse();

		if (id != 0) {
			res.setId(id);
			res.setStatus(true);
		}

		return res;
	}

	private BusinessResponse _generateBusinessRes(Business business) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		BusinessResponse res = modelMapper.map(business, BusinessResponse.class);

		List<DonationBusinsessResponse> dresLs = new ArrayList<DonationBusinsessResponse>();

		for (Donation dz : business.getDonationList()) {
			ModelMapper modelMapper2 = new ModelMapper();
			modelMapper2.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			DonationBusinsessResponse dres = modelMapper2.map(dz, DonationBusinsessResponse.class);
			dresLs.add(dres);
		}

		return res;
	}

	private List<BusinessResponse> _generateBusinessRes(List<Business> business) {

		List<BusinessResponse> bresLs=new ArrayList<BusinessResponse>();
		
		for(Business bs:business) {
			
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			BusinessResponse res = modelMapper.map(bs, BusinessResponse.class);

			List<DonationBusinsessResponse> dresLs = new ArrayList<DonationBusinsessResponse>();

			for (Donation dz : bs.getDonationList()) {
				ModelMapper modelMapper2 = new ModelMapper();
				modelMapper2.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				DonationBusinsessResponse dres = modelMapper2.map(dz, DonationBusinsessResponse.class);
				dresLs.add(dres);
			}

			bresLs.add(res);
		}
		
		
		return bresLs;
	}
}