package com.codewithdurgesh.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenHelper {
 	                                      
	//Variable FOR JWT TOKEN VALIDITY(in MILISECOND)
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
    
//retrieve username from jwt token
    public String getusernameFromToken(String token)
    {
		return getClaimFromToken(token, Claims::getSubject);
    }
    
// retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token)
    {
		return getClaimFromToken(token, Claims::getExpiration);
    	
    }

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
	}
	
	//check if token is expired
	private Boolean isTokenExpired(String Token)
	{
		final Date expiration = getExpirationDateFromToken(Token);
		return expiration.before(new Date());
	}
	
	//generate Token from User
	public String generateToken(UserDetails userDetails)
	{
		Map<String, Object> claims = new HashMap();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) 
	{
		 return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				 .setIssuedAt(new Date(System.currentTimeMillis())) 
				   .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
				     .signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// While creating token-
	//1. define claims of the token like issuer, expiration, subject
	//2. sign the JWT using HS512 alogorithm secret key
	//3. according to JWS Compact serialization
	//   compaction of the JWT to a URL-SAFE STRING

	/*
	 * private String doGenerateToken(HashMap<String, Object> claims, String
	 * username, String subject) 
	 * {
	 * 
	 * return Jwts.builder().setClaims(claims).setSubject(subject) .setIssuedAt(new
	 * Date(System.currentTimeMillis())) .setExpiration(new
	 * Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY *
	 * 100)).signWith(SignatureAlgorithm.HS512, secret).compact(); 
	 * }
	 */
	
	//validate token
	public Boolean validateToken(String token, UserDetails userDetails)
	{
		final String username = getusernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
	 /* private Date getClaimFromToken(String token, Object object) {
	
	  return null;*/
	}

	 
	



 