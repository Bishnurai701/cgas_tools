package com.oagreport.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.services.MiscJvService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ADMIN') or hasRole('OAG') or hasRole('DTCO')")
@RequestMapping("api/account/misc/jv")
public class MiscJvController {
	
	  final MiscJvService jvService;

	    public MiscJvController(MiscJvService jvMiscService) {
	        this.jvService = jvMiscService;
	    }
	    
	    
	    @PostMapping("/getAccount")
	    public ResponseEntity<?>  getAccount(@Valid @RequestBody AgencyRequestDto request)  {
	        return ResponseEntity.ok().body(
	                jvService.getAccount(request));
	    }

}
