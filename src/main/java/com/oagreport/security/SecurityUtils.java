package com.oagreport.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.User;
import com.oagreport.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@Transactional
public class SecurityUtils {

    @Autowired
    UserRepository userRepository;

    public SecurityUtils() {
    }
    public static String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            userName=authentication.getName();
           // UserDetails userDetails = (UserDetails) authentication.getPrincipal();
           // userName = userDetails.getUsername();
        }
        return userName;
    }

    public  User getUserDetail() {
        String username=getUserName();
        User user=userRepository.findByUsername(username).get();
        return user;
    }

    public static Set<String> getRoles() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        return roles;
    }

    public static boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

}

/*
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        } else {
            String username = principal.toString();
        }
 */
