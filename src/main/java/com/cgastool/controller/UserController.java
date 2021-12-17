package com.cgastool.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgastool.datatablesrepository.AgencyRepository;
import com.cgastool.datatablesrepository.TreasuryOfficeRepository;
import com.cgastool.domain.Agency;
import com.cgastool.domain.AgencyRequestDto;
import com.cgastool.domain.ServiceResponse;
import com.cgastool.domain.TreasuryOffice;
import com.cgastool.domain.User;
import com.cgastool.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN') or hasRole('FCGO') or hasRole('DTCO') or hasRole('FCGO_USER') or hasRole('PTCO')")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TreasuryOfficeRepository treasuryOfficeRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	@GetMapping("/resource/getTreasuryOffice")
	public ServiceResponse getTreasuryOffice(Principal principal) {

		String userName = principal.getName();
		Optional<User> currentUser = userRepository.findByUserName(userName);
		String oIdentity = currentUser.get().getoIdentity();
		Iterable<TreasuryOffice> treasuryOffices = null;
		Iterable<Agency> agencies = null;

		if (oIdentity.equals("ADMIN") || oIdentity.equals("FCGO")) {
			treasuryOffices = treasuryOfficeRepository.findAll();

		}

		if (oIdentity.equals("DTCO") || oIdentity.equals("PTCO")) {
			treasuryOffices = treasuryOfficeRepository.findTreasuryOfficeById(currentUser.get().getOfficeId());
			agencies = agencyRepository.findAgenciesByTreasuryOfficeId(currentUser.get().getOfficeId());
		}

		Map<String, Object> result = new HashMap<>();
		result.put("treasuryOffices", treasuryOffices);
		result.put("agencies", agencies);

		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}

	@PostMapping("/resource/getAgencies")
	public ServiceResponse getAgencies(@RequestBody AgencyRequestDto request) {
		Iterable<Agency> agencies = null;
		agencies = agencyRepository.findAgenciesByTreasuryOfficeId(request.getTreasuryOfficeId());
		Map<String, Object> result = new HashMap<>();
		result.put("agencies", agencies);
		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}

}
