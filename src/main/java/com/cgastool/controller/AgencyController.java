package com.cgastool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


import com.cgastool.datatablesrepository.AgencyRepository;
import com.cgastool.domain.Agency;
import com.cgastool.domain.AgencyDto;
import com.cgastool.domain.ServiceResponse;
import com.cgastool.repository.AgencyRepo;
import com.cgastool.services.AgencyServce;
import com.cgastool.services.AgencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.client.http.HttpHeaders;

@RestController
@RequestMapping("/app/agency")
public class AgencyController {
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private AgencyServce agencyServce;
	
	@Autowired
	EntityManager entityManager;
	
	
	@GetMapping("/getcodee/{codee}")
	public List<ObjectNode> getIdByCode(@PathVariable(required = true) String codee)
	{
		Query query =entityManager.createNativeQuery("SELECT ID,FISCALYEAR_ID,N_DESCRIPTION FROM IFMIS_C_AGENCY WHERE CODE=:agency_code",Tuple.class);
		query.setParameter("agency_code", codee);
		List<Tuple> resultList = query.getResultList();
		List<ObjectNode> json = _toJson(resultList);
		return json; 
		}
	private List<ObjectNode> _toJson(List<Tuple> results) {

		List<ObjectNode> json = new ArrayList<ObjectNode>();

		ObjectMapper mapper = new ObjectMapper();

		for (Tuple t : results)
		{
		    List<TupleElement<?>> cols = t.getElements();

		    ObjectNode one = mapper.createObjectNode();

		    for (TupleElement col : cols)
		    {
		    	one.put(col.getAlias(), t.get(col.getAlias()).toString());
		    }

		    json.add(one);
		}

		return json;
	}
	
	
	
	@GetMapping("/getcode/{code}")
	public String getCode(@PathVariable(required = true) String code)
	{
		return  agencyService.getCodeService(code);
		 //System.out.println(list.size());
		 //return list;
	}
	
	
		@GetMapping("/getId/{id}")
	   public ResponseEntity<?> GetById(@PathVariable(required = true) long id) {
	        ServiceResponse response=agencyService.getById(id);

	        if(response.isSuccess()) {
	            return ResponseEntity.ok().body(response);
	        }else{
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	  
	
	  @GetMapping("/getAllAgency") 
	  public List<Agency> getAllAgency()
		{
			 List<Agency> list=  agencyService.getAllAgencyService();
			 System.out.println(list.size());
			 return list;

		}
	  
	  @GetMapping("/getidByCode/{agencycode}")
	   public List<Map<String, Object>> getidByCode(@PathVariable(required = true) String agencycode) {
		  List<Map<String, Object>> list= agencyService.getIdByCodeService(agencycode);  
		  return list;
	    }
	  
	  @GetMapping("/getcodelist/{ag_code}")
	  public List<Map<String, Object>> getExpenses(@PathVariable("ag_code") String ag_code)
		{
		  List<Map<String, Object>> list= agencyServce.getAgencyCodeService(ag_code);
			 System.out.println(list.size());
			 return list;

		}
	
	        
		
	}
	

