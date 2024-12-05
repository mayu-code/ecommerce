package com.main.ecommerce.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.entities.LoginUser;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.services.impl.AdminServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdminServiceImpl adminService;




    //user registration

    @PostMapping("/user/register")
	public ResponseEntity<User> createUser(@RequestBody User user) throws Exception{
		User u = null;
        User isExit = this.userService.getByEmail(user.getEmail());
		// User isExit = this.userRepository.findByEmail(user.getEmail());
		
		if(isExit!=null) {
			throw new Exception("user is already exits ");
		}
			u = user;
            

			Authentication authentication = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPassword());
			String jwtToken = JwtProvider.generateToken(authentication);
			AuthResponse authResponse = new AuthResponse(jwtToken,"user register successfully ");
			return authResponse;
	}
	@PostMapping("/user/login")
	public AuthResponse signin(@RequestBody  LoginUser user){
		Authentication authentication = authenticate(user.getEmail(),user.getPassword());
		String token = JwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(token,"user login successfully ");
		return authResponse;
	}
	
	private Authentication authenticate(String email,String password) {
		UserDetails details = detailService.loadUserByUsername(email);
		
		if(details ==null) {
			throw new BadCredentialsException("invalid user");
		}
		if(!passwordEncoder.matches(password, details.getPassword())) {
			throw new BadCredentialsException("password not match");
		}
		
		return new UsernamePasswordAuthenticationToken(details, null,details.getAuthorities());
	}


}
