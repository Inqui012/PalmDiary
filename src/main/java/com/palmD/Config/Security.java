package com.palmD.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmD.Service.Users_serv;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {
	
	private final ObjectMapper mapper;
	private final JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler;
	private final JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler;
	private final JsonLogoutSuccessHandler jsonLogoutSuccessHandler;
	private final UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {
	
//		https://ckinan.com/blog/spring-security-credentials-from-json-request/
//		Json 으로 받아온 데이터로 로그인 기능 동작하게 하기.......
		JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter(mapper, jsonAuthenticationSuccessHandler, jsonAuthenticationFailureHandler);
		jsonAuthenticationFilter.setAuthenticationManager(authenticationManager());
		http.addFilterBefore(jsonAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.logout().logoutSuccessHandler(jsonLogoutSuccessHandler);
		
		http.authorizeRequests()
			.mvcMatchers("/css/**", "/js/**", "/lib/**", "/img/**").permitAll()
			.antMatchers(HttpMethod.POST, "/user/login").permitAll()
			.anyRequest().permitAll();
		return http.build();
	}
	
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return new ProviderManager(provider);
    }
}
