package com.api.parkingcontrol.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
         
		 .httpBasic()
         .and()
         .authorizeRequests()
         .antMatchers(HttpMethod.GET, "/parking-spot/**").permitAll()
         .antMatchers(HttpMethod.POST, "/parking-spot/").hasRole("USER")
         .antMatchers(HttpMethod.DELETE, "/parking-spot/**").hasRole("ADMIN")
         .anyRequest().authenticated()
         .and()
         .csrf().disable();
         	}
	
	/*Autenticação com userDetailsService*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
		
		
	}
	
	
	/*Autenticação em memória*/
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("thiago")
		.password(passwordEncoder().encode("123456"))
		.roles("ADMIN");
		
		
	}*/
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
