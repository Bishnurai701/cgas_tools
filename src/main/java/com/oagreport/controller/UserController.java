package com.oagreport.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.SecurityUtils;
import com.oagreport.datatablesrepository.AgencyRepository;
import com.oagreport.datatablesrepository.TreasuryOfficeRepository;
import com.oagreport.domain.Agency;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.Department;
import com.oagreport.domain.ReportRequestModel;
import com.oagreport.domain.Role;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TreasuryOffice;
import com.oagreport.domain.User;
import com.oagreport.repository.UserRepository;
import com.oagreport.services.HrisReportService;
import com.oagreport.services.JvTreasuryService;
import com.oagreport.services.MiscJvService;
import com.oagreport.services.ResourceService;
import com.oagreport.services.TreasuryReportService;






@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN') or hasRole('OAG') or hasRole('DTCO')")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private TreasuryReportService treasuryReportService;
	
	@Autowired
	private HrisReportService hrisReportService;
	
	@Autowired
	private TreasuryOfficeRepository treasuryOfficeRepository;
	
	 @Autowired 
	 private AgencyRepository agencyRepository;
	
	   
		@GetMapping("/resource/getTreasuryOffice")
		public ServiceResponse getTreasuryOffice(Principal principal)  {
			
			 String username= principal.getName();
			 Optional<User> currentUser = userRepository.findByUsername(username);
			 String oIdentity = currentUser.get().getoIdentity();
			 Iterable<TreasuryOffice> treasuryOffices = null;
			 Iterable<Agency> agencies = null;

		        if(oIdentity.equals("ADMIN")||oIdentity.equals("OAG"))
		        {
		            treasuryOffices = treasuryOfficeRepository.findAll();
		            
		        }


		        if (oIdentity.equals("DTCO")){
		            treasuryOffices = treasuryOfficeRepository.findTreasuryOfficeById(currentUser.get().getOfficeId());
		            agencies = agencyRepository.findAgenciesByTreasuryOfficeId(currentUser.get().getOfficeId());
		        }



		        Map<String, Object> result = new HashMap<>();
		        result.put("treasuryOffices", treasuryOffices);
		        result.put("agencies",agencies);

		        ServiceResponse response=new ServiceResponse(true,result);
		        return response;
		}
		
		@PostMapping("/resource/getAgencies")
	    public ServiceResponse getAgencies(@RequestBody AgencyRequestDto request)
	    {
	        Iterable<Agency> agencies = null;
	        agencies = agencyRepository.findAgenciesByTreasuryOfficeId(request.getTreasuryOfficeId());
	        Map<String, Object> result = new HashMap<>();
	        result.put("agencies",agencies);
	        ServiceResponse response=new ServiceResponse(true,result);
	        return response;
	    }
		
		 
		
		  @PostMapping("/MaLePa17") public ResponseEntity<?>
		  getMaLePa17Report(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( treasuryReportService.GetMaLePa17Report(request));
		  }
		  
		  @PostMapping("/MaLePa22") public ResponseEntity<?>
		  getMaLePa22Report(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( treasuryReportService.GetMaLePa22Report(request));
		  }
		  
		  @PostMapping("/MaLePa208") public ResponseEntity<?>
		  getMaLePa208Report(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( treasuryReportService.GetMaLePa208Report(request));
		  }
		  
		  
		  @PostMapping("/MaLePa209") public ResponseEntity<?>
		  getMaLePa209Report(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( treasuryReportService.GetMaLePa209Report(request));
		  }
		  
		  @PostMapping("/MaLePa210") public ResponseEntity<?>
		  getMaLePa210Report(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( treasuryReportService.GetMaLePa210Report(request));
		  }
		  
		  @PostMapping("/MaLePa211") public ResponseEntity<?>
		  getMaLePa211Report(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( treasuryReportService.GetMaLePa211Report(request));
		  }
		  
		  @PostMapping("/MaLePa213") public ResponseEntity<?>
		  getMaLePa213Report(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( treasuryReportService.GetMaLePa213Report(request));
		  }
		  
		  
		  @PostMapping("/MaLePa214") public ResponseEntity<?>
		  getMaLePa214Report(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( treasuryReportService.GetMaLePa214Report(request));
		  }
		  
		  
		  
		  //hrisReports
		  
		  @PostMapping("/getDeductionReport") public ResponseEntity<?>
		  getDeductionReport(@Valid @RequestBody ReportRequestModel request) { return
		  ResponseEntity.ok().body( hrisReportService.getDeductionReport(request)); }
		  
		  @PostMapping("/getPaymentDeductionReport") public ResponseEntity<?>
		  getPaymentDeductionReport(@Valid @RequestBody ReportRequestModel request) {
		  return ResponseEntity.ok().body(
		  hrisReportService.getPaymentDeductionReport(request)); }
		 
	

}
