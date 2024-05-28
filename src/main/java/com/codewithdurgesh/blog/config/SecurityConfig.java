package com.codewithdurgesh.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithdurgesh.blog.security.CustomUserDetailServce;
import com.codewithdurgesh.blog.security.JwtAuthenticationEntryPoint;
import com.codewithdurgesh.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	
	 public static final String[] PUBLIC_URLS = {
	            "/api/v1/auth/**",
	            "/v3/api-docs",
//	            "/v2/api-docs",
	            "/swagger-resources/**",
	            "/swagger-ui/**",
	            "/webjars/**"
	    };

	@Autowired
	private CustomUserDetailServce customUserDetailServce;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		/*
		 * http.csrf(csrf -> csrf.disable()) .cors(cors -> cors.disable())
		 * .authorizeHttpRequests((auth) ->
		 * auth.requestMatchers(PUBLIC_URLS)("/api/admin/**") .permitAll()
		 * .requestMatchers(HttpMethod.GET) .permitAll() .anyRequest() .authenticated()
		 * .exceptionHandling(ex ->
		 * ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
		 * //.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		 * .sessionmanagement(session ->
		 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 * //.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		 * 
		 * //http.addFilterBefore(this.jwtAuthenticationFilter,
		 * UsernamePasswordAuthenticationFilter),
		 * http.addFilterBefore(this.jwtAuthenticationFilter,
		 * UsernamePasswordAuthenticationFilter)
		 * http.authenticationProvider(daoAuthenticationProvider());
		 * 
		 * DefaultSecurityFilterChain defaultSecurityFilterChain=http.build();
		 * 
		 * return defaultSecurityFilterChain;
		 */
		
		   http.csrf(csrf -> csrf.disable())
	        .cors(cors -> cors.disable())
	        .authorizeHttpRequests(auth ->auth.requestMatchers(PUBLIC_URLS)
	        .permitAll()
	        .requestMatchers(HttpMethod.GET)
	        .permitAll()
	        .anyRequest()
	        .authenticated())
	        .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


	        http.addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        http.authenticationProvider(daoAuthenticationProvider());
	     
	      
	        return http.build();
	}
	
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * auth.userDetailsService(this.customUserDetailServce).passwordEncoder(
	 * passwordEncoder); }
	 */

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();

	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailServce);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
		
	}

}
