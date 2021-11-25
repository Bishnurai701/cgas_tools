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
import com.oagreport.services.TreasuryReportService;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('OAG') or hasRole('ADMIN') or hasRole('DTCO')")
@RequestMapping("/api/account/treasury/report")
public class TreasuryReportController {

    final TreasuryReportService reportService;

    public TreasuryReportController(TreasuryReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/MaLePa17")
    public ResponseEntity<?>  getMaLePa17Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa17Report(request));
    }

    @PostMapping("/MaLePa22")
    public ResponseEntity<?>  getMaLePa22Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa22Report(request));
    }

    @PostMapping("/MaLePa208")
    public ResponseEntity<?>  getMaLePa208Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa208Report(request));
    }


    @PostMapping("/MaLePa209")
    public ResponseEntity<?>  getMaLePa209Report(@RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa209Report(request));
    }
    

    @PostMapping("/MaLePa210")
    public ResponseEntity<?>  getMaLePa210Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa210Report(request));
    }

    @PostMapping("/MaLePa211")
    public ResponseEntity<?>  getMaLePa211Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa211Report(request));
    }

    @PostMapping("/MaLePa213")
    public ResponseEntity<?>  getMaLePa213Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa213Report(request));
    }


    @PostMapping("/MaLePa214")
    public ResponseEntity<?>  getMaLePa214Report(@Valid @RequestBody ReportRequestModel request)  {
        return ResponseEntity.ok().body(
                reportService.GetMaLePa214Report(request));
    }
	
}
