package com.ukees.controller;

import com.ukees.model.User;
import com.ukees.security.JwtTokenUtil;
import com.ukees.security.dto.JwtRequest;
import com.ukees.security.dto.JwtResponse;
import com.ukees.service.EmployeeService;
import com.ukees.service.LoginSessionService;
import com.ukees.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

	private static final String USER_DISABLED_MESSAGE = "USER_DISABLED";
	private static final String INVALID_CREDENTIALS_MESSAGE = "INVALID_CREDENTIALS";
	private static final String AUTH_HEADER = "Authorization";

	@Autowired LoginSessionService sessionService;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired EmployeeService employeeService;
	@Autowired UserService userService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/signup")
	public JwtResponse signUp(@RequestBody JwtRequest request) {
		User user = employeeService.createEmployee(request);
		JwtResponse jwtResponse = createSession(user);
		return jwtResponse;
	}

	@DeleteMapping("/logout")
	public void logOut(@RequestHeader(AUTH_HEADER) String token) {
		sessionService.logout(token.substring(7));
	}

	@GetMapping("/refresh-token")
	public JwtResponse refreshToken() {
		String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getUser(userId);
		JwtResponse jwtResponse = generateJwtResponse(user);
		sessionService.refreshSession(user, jwtResponse.getAccessToken(), jwtResponse.getRefreshToken());
		return jwtResponse;
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.email, authenticationRequest.password));
		User user = userService.findByEmail(authenticationRequest.email);

		JwtResponse jwtResponse = createSession(user);
		return jwtResponse;
	}

	private JwtResponse generateJwtResponse(User user) {
		String token = jwtTokenUtil.generateToken(user, "access");
		String refreshToken = jwtTokenUtil.generateToken(user, "refresh");
		return new JwtResponse(token, refreshToken, user.getId());
	}

	private JwtResponse createSession(User user) {
		JwtResponse jwtResponse = generateJwtResponse(user);
		sessionService.createSession(user.getEmail(), jwtResponse.getAccessToken(), jwtResponse.getRefreshToken());
		return jwtResponse;
	}
}
