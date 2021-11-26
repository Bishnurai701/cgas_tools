package com.oagreport.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oagreport.domain.ReportRequestModel;
import com.oagreport.services.HrisReportService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hris/report")
@PreAuthorize("hasRole('ADMIN') or hasRole('OAG') or hasRole('DTCO')")
public class HrisReportController {

	  final HrisReportService reportService;

	    public HrisReportController(HrisReportService reportService) {
	        this.reportService = reportService;
	    }

	    @PostMapping("/getDeductionReport")
	    public ResponseEntity<?>  getDeductionReport(@Valid @RequestBody ReportRequestModel request)  {
	        return ResponseEntity.ok().body(
	                reportService.getDeductionReport(request));
	    }

	    @PostMapping("/getPaymentDeductionReport")
	    public ResponseEntity<?>  getPaymentDeductionReport(@Valid @RequestBody ReportRequestModel request)  {
	        return ResponseEntity.ok().body(
	                reportService.getPaymentDeductionReport(request));
	    }
	
}
