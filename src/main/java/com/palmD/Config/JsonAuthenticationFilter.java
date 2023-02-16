package com.palmD.Config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmD.DTO.UsersLogin_dto;

public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String DEFAULT_LOGIN_REQ_URL = "/user/login";
	private static final String HTTP_METHOD = "POST";
	private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQ_MATCHER 
		= new AntPathRequestMatcher(DEFAULT_LOGIN_REQ_URL, HTTP_METHOD);
	
	private final ObjectMapper mapper;
	
//	생성자
	public JsonAuthenticationFilter (ObjectMapper mapper,
					AuthenticationSuccessHandler authenticationSuccessHandler,
					AuthenticationFailureHandler authenticationFailureHandler) {
		super(DEFAULT_LOGIN_PATH_REQ_MATCHER);
		this.mapper = mapper;
		setAuthenticationSuccessHandler(authenticationSuccessHandler);
		setAuthenticationFailureHandler(authenticationFailureHandler);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		UsersLogin_dto userLoginDto = mapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), UsersLogin_dto.class);

        String username = userLoginDto.getLoginId();
        String password = userLoginDto.getLoginPw();

        if (username == null || password == null) {
            throw new AuthenticationServiceException("DATA IS MISS");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
	
	
}
