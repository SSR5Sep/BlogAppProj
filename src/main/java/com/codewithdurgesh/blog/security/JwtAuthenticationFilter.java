package com.codewithdurgesh.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component                                    //class
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
	//1. Get Token
		String requestToken = request.getHeader("Authorization");
		//Bearer 2352523sdgsg
		System.out.println(requestToken);
		
		String username=null;
		
		String token=null;
		
		if(requestToken != null && requestToken.startsWith("Bearer"))
		{
			token = requestToken.substring(7);
			
			try
			{
			username=this.jwtTokenHelper.getusernameFromToken(token);
			}

//when a method receives an argument of the wrong type or an inappropriate value.			
			catch(IllegalArgumentException e)
			{
			System.out.println("Unable to Jwt Token");
			}

// when attempting to parse a JSON Web Token (JWT) that is not properly formatted according to the JWT specification
			catch(MalformedJwtException e)
			{
			System.out.println("Invalid Jwt");	
			}
		}
		else
		{
			System.out.println("Jwt token does not start with Bearer");
		}
		
//once we get the token, now validate
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			//validating
			//it will take token and user detail and return true or false 
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				//shi chal rha hai
				//authentication karna hai
				
				//security context me authentication ko set krne ke liye object bna rhe hai authentication ka
				//to make authentication object
				UsernamePasswordAuthenticationToken	usernamePasswordAuthentication = 
						new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				//setting userdetail
				usernamePasswordAuthentication.setDetails
						(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
			}
			else
			{
				System.out.println("Invalid Jwt Token");
			}
		}
		
		else
		{
			System.out.println("UserName is null Or Contect is not null ");
		}
		
		filterChain.doFilter(request, response);
			
		
	}
}




