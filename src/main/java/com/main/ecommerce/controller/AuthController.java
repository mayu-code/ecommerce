package com.main.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.ResponseEntity.AuthResponse;
import com.main.ecommerce.entities.Admin;
import com.main.ecommerce.entities.LoginAdmin;
import com.main.ecommerce.entities.LoginUser;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.jwtSecurity.CustomAdminDetailsService;
import com.main.ecommerce.jwtSecurity.CustomUserDetailsService;
import com.main.ecommerce.jwtSecurity.jwtProvider;
import com.main.ecommerce.services.impl.AdminServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:5173/", "http://localhost:5174/" })
public class AuthController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private CustomAdminDetailsService adminDetailsService;

	@Autowired
	private AdminServiceImpl adminService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// user registration

	@PostMapping("/user/register")
	public ResponseEntity<AuthResponse> createUser(@RequestBody User user) throws Exception {
		User u = null;
		AuthResponse auth = new AuthResponse();
		User isExit = this.userService.getByEmail(user.getEmail());

		if (isExit != null) {
			throw new Exception("user is already exits ");
		}
		u = user;
		u.setRegistationDate(LocalDateTime.now());
		u.setPassword(passwordEncoder().encode(user.getPassword()));
		this.userService.registerUser(u);
		Authentication authentication = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPassword());
		String jwtToken = jwtProvider.generateToken(authentication);
		auth.setToken(jwtToken);
		auth.setStatus(HttpStatus.OK);
		auth.setMessage("User register succusfull");

		return auth;
	}

	@PostMapping("/user/login")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginUser loginRequest) {
		AuthResponse response = new AuthResponse();

		// Fetch user details by email
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
		if (userDetails == null) {
			auth.setStatus(HttpStatus.UNAUTHORIZED);
			auth.setMessage("Invalid email or password");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(auth);
		}

		// Validate the password
		boolean isPasswordValid = passwordEncoder().matches(
				loginRequest.getPassword(),
				userDetails.getPassword() // Encoded password
		);

		if (!isPasswordValid) {
			auth.setStatus(HttpStatus.UNAUTHORIZED);
			auth.setMessage("Invalid email or password");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(auth);
		}
		Authentication authentication = userAuthenticate(userDetails.getUsername(), loginRequest.getPassword());

		String token = jwtProvider.generateToken(authentication);
		User loginUser = this.userService.getByEmail(loginRequest.getEmail());
		loginUser.setLoginDate(LocalDateTime.now());
		this.userService.registerUser(loginUser);

		// Prepare response
		response.setToken(token);
		response.setStatus(HttpStatus.OK);
		response.setMessage("User login successful");

		return ResponseEntity.ok(auth);
	}

	// authentication for user 
	private Authentication userAuthenticate(String email, String password) {
		UserDetails details = userDetailsService.loadUserByUsername(email);

		if (details == null) {
			throw new BadCredentialsException("invalid user");
		}

		return new UsernamePasswordAuthenticationToken(details, password, details.getAuthorities());

	}

	// admin registration

	@PostMapping("/admin/register")
	public ResponseEntity<AuthResponse> createAdmin(@RequestBody Admin admin) throws Exception {
		Admin a = null;
		AuthResponse response = new AuthResponse();
		Admin isExit = this.adminService.getByEmail(admin.getEmail());
		if (isExit != null) {
			throw new Exception("admin is already exits ");
		}

		a = admin;
		a.setPassword(passwordEncoder().encode(admin.getPassword()));
		this.adminService.saveAdmin(admin);
		Authentication authentication = new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword());
		String jwtToken = jwtProvider.generateToken(authentication);
		auth.setToken(jwtToken);
		auth.setStatus(HttpStatus.OK);
		auth.setMessage("Admin register succussfull");
		return auth;

	}

	@PostMapping("/admin/login")
	public ResponseEntity<AuthResponse> adminLogin(@RequestBody LoginAdmin loginRequest) {
		AuthResponse response = new AuthResponse();

		// Fetch admin details by email
		UserDetails adminDetails = adminDetailsService.loadUserByUsername(loginRequest.getEmail());
		if (adminDetails == null) {
			auth.setStatus(HttpStatus.UNAUTHORIZED);
			auth.setMessage("Invalid email or password");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(auth);
		}

		// Validate the password
		boolean isPasswordValid = passwordEncoder().matches(
				loginRequest.getPassword(),
				adminDetails.getPassword() // Encoded password
		);

		if (!isPasswordValid) {
			auth.setStatus(HttpStatus.UNAUTHORIZED);
			auth.setMessage("Invalid email or password");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(auth);
		}

		// Authenticate the admin
		Authentication authentication = adminAuthenticate(adminDetails.getUsername(), loginRequest.getPassword());

		// Generate JWT token
		String token = jwtProvider.generateToken(authentication);

		// Prepare response
		auth.setToken(token);
		auth.setStatus(HttpStatus.OK);
		auth.setMessage("Admin login successful");

		return ResponseEntity.ok(auth);
	}

	private Authentication adminAuthenticate(String email, String password) {
		UserDetails details = adminDetailsService.loadUserByUsername(email);
		if (details == null) {
			throw new BadCredentialsException("invalid admin");
		}

		return new UsernamePasswordAuthenticationToken(details, password, details.getAuthorities());

	}
}
