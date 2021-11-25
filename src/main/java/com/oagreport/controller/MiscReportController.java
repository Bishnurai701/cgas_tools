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
import com.oagreport.services.MiscReportService;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/account/misc/report")
public class MiscReportController {

	
    final MiscReportService reportService;

    public MiscReportController(MiscReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/MaLePa208")
    public ResponseEntity<?>  getMaLePa208Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa208Report(request));
    }

    @PostMapping("/MaLePa209")
    public ResponseEntity<?>  getMaLePa209Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa209Report(request));
    }

    @PostMapping("/MaLePa211")
    public ResponseEntity<?>  getMaLePa211Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa211Report(request));
    }
	
	
}
