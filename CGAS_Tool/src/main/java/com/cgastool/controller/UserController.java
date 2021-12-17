package com.oagreport.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.ReportRequestModel;
import com.oagreport.services.HrisReportService;
import com.oagreport.services.JvTreasuryService;
import com.oagreport.services.ResourceService;
import com.oagreport.services.TreasuryReportService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class UserController {
	
	@Autowired
	private JvTreasuryService jvService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private TreasuryReportService treasuryReportService;
	
	@Autowired
	private HrisReportService hrisReportService;
	
	   @PostMapping("/getAccount")
	    public ResponseEntity<?>  getAccount(@Valid @RequestBody AgencyRequestDto request)  {
	        return ResponseEntity.ok().body(
	                jvService.getAccount(request));
	    }
	   
		@PostMapping("/getTreasuryOffice")
		public ResponseEntity<?>  getTreasuryOffice()  {
			return ResponseEntity.ok().body(
					resourceService.getTreasuryOffice());
		}
		
		 
		 @PostMapping("/MaLePa17")
		    public ResponseEntity<?>  getMaLePa17Report(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		        		treasuryReportService.GetMaLePa17Report(request));
		    }

		    @PostMapping("/MaLePa22")
		    public ResponseEntity<?>  getMaLePa22Report(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		        		treasuryReportService.GetMaLePa22Report(request));
		    }

		    @PostMapping("/MaLePa208")
		    public ResponseEntity<?>  getMaLePa208Report(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		        		treasuryReportService.GetMaLePa208Report(request));
		    }


		    @PostMapping("/MaLePa209")
		    public ResponseEntity<?>  getMaLePa209Report(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		        		treasuryReportService.GetMaLePa209Report(request));
		    }

		    @PostMapping("/MaLePa210")
		    public ResponseEntity<?>  getMaLePa210Report(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		        		treasuryReportService.GetMaLePa210Report(request));
		    }

		    @PostMapping("/MaLePa211")
		    public ResponseEntity<?>  getMaLePa211Report(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		        		treasuryReportService.GetMaLePa211Report(request));
		    }

		    @PostMapping("/MaLePa213")
		    public ResponseEntity<?>  getMaLePa213Report(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		        		treasuryReportService.GetMaLePa213Report(request));
		    }


		    @PostMapping("/MaLePa214")
		    public ResponseEntity<?>  getMaLePa214Report(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		        		treasuryReportService.GetMaLePa214Report(request));
		    }
		    
		    
		    
		    //hrisReports
		    @PostMapping("/getDeductionReport")
		    public ResponseEntity<?>  getDeductionReport(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		                hrisReportService.getDeductionReport(request));
		    }

		    @PostMapping("/getPaymentDeductionReport")
		    public ResponseEntity<?>  getPaymentDeductionReport(@Valid @RequestBody ReportRequestModel request)  {
		        return ResponseEntity.ok().body(
		                hrisReportService.getPaymentDeductionReport(request));
		    }
	

}
