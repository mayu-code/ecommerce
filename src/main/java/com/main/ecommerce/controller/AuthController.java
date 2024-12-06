package com.main.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class AuthController {
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
   
    @Autowired
    private CustomAdminDetailsService adminDetailsService;

    @Autowired
    private AdminServiceImpl adminService;


    //user registration

    @PostMapping("/user/register")
	public ResponseEntity<String> createUser(@RequestBody User user) throws Exception{
		User u = null;
        User isExit = this.userService.getByEmail(user.getEmail());
		// User isExit = this.userRepository.findByEmail(user.getEmail());
		
		if(isExit!=null) {
			throw new Exception("user is already exits ");
		}

        u = user;
        u.setRegistationDate(LocalDateTime.now());
        this.userService.registerUser(u);
        Authentication authentication = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPassword());
        String jwtToken = jwtProvider.generateToken(authentication);
        // AuthResponse authResponse = new AuthResponse(jwtToken,"user register successfully ");
        return new ResponseEntity<>(jwtToken,HttpStatus.OK);
	}

	@PostMapping("/user/login")
	public ResponseEntity<String> signin(@RequestBody  LoginUser user){
        User loginUser = this.userService.getByEmail(user.getEmail());
        loginUser.setLoginDate(LocalDateTime.now());
		Authentication authentication = userAuthenticate(loginUser.getEmail(),loginUser.getPassword());
		String token = jwtProvider.generateToken(authentication);
		// AuthResponse authResponse = new AuthResponse(token,"user login successfully ");
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	
	private Authentication userAuthenticate(String email,String password) {
		UserDetails details = userDetailsService.loadUserByUsername(email);
		
		if(details ==null) {
			throw new BadCredentialsException("invalid user");
		}
		// if(!passwordEncoder.matches(password, details.getPassword())) {
		// 	throw new BadCredentialsException("password not match");
		// }

        return new UsernamePasswordAuthenticationToken(details, password,details.getAuthorities());
		
	}


    //admin registration

    @PostMapping("/admin/register")
	public ResponseEntity<String> createAdmin(@RequestBody Admin admin) throws Exception{
		Admin a = null;
        Admin isExit = this.adminService.getByEmail(admin.getEmail());
		// User isExit = this.userRepository.findByEmail(user.getEmail());
		
		if(isExit!=null) {
			throw new Exception("admin is already exits ");
		}

        a = admin;
        this.adminService.saveAdmin(admin);
        Authentication authentication = new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword());
        String jwtToken = jwtProvider.generateToken(authentication);
        // AuthResponse authResponse = new AuthResponse(jwtToken,"user register successfully ");
        return new ResponseEntity<>(jwtToken,HttpStatus.OK);
	}

	@PostMapping("/admin/login")
	public ResponseEntity<String> adminLogin(@RequestBody  LoginAdmin admin){
		Authentication authentication = adminAuthenticate(admin.getEmail(),admin.getPassword());
		String token = jwtProvider.generateToken(authentication);
		// AuthResponse authResponse = new AuthResponse(token,"user login successfully ");
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	
	private Authentication adminAuthenticate(String email,String password) {
		UserDetails details = adminDetailsService.loadUserByUsername(email);
		
		if(details ==null) {
			throw new BadCredentialsException("invalid admin");
		}
		// if(!passwordEncoder.matches(password, details.getPassword())) {
		// 	throw new BadCredentialsException("password not match");
		// }

        return new UsernamePasswordAuthenticationToken(details, password,details.getAuthorities());
		
	}
}
