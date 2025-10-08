package com.transporte.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transporte.dto.AuthenticationRequest;
import com.transporte.dto.AuthenticationResponse;
import com.transporte.dto.UserContextDTO;
import com.transporte.security.JwtUtil;
import com.transporte.service.impl.UserDetailsServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;

	private final UserDetailsServiceImpl userDetailsService;

	private final JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthToken(@Valid @RequestBody AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@GetMapping("/context")
    public ResponseEntity<UserContextDTO> getContext(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(userDetailsService.getContext(username));
    }
}
