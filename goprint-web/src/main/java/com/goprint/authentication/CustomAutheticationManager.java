package com.goprint.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.goprint.service.UserDetailService;
/**
 * Custom Authentication Provider for Basic Authentication
 * @author vinod Bhatkotti
 *
 */
@Component
public class CustomAutheticationManager implements AuthenticationProvider{
	
	@Autowired
	UserDetailService userService;
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		Long id = userService.findById(username, password);
		if(id == null){
			throw new BadCredentialsException("Wrong Credentials");
		}
		UserInfo userInfo = new UserInfo(id, username);
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_VERIFIED"));
		return new UsernamePasswordAuthenticationToken(userInfo, password, grantedAuths);
	}

	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}




}
