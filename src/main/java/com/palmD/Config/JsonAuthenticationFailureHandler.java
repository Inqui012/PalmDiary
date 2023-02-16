package com.palmD.Config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmD.DTO.UsersLoginResp_dto;

@Component
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("LoginFailureHandler.onAuthenticationFailure");
        ObjectMapper mapper = new ObjectMapper();
        UsersLoginResp_dto usersLoginRespDto = new UsersLoginResp_dto();
        usersLoginRespDto.setCode(HttpStatus.FORBIDDEN);
        usersLoginRespDto.setStatus("FAIL");
        usersLoginRespDto.setMessage("로그인 실패");
        
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(mapper.writeValueAsString(usersLoginRespDto));
        response.getWriter().flush();
    }
}
