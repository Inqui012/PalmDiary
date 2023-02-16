package com.palmD.Config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmD.DTO.UsersLoginResp_dto;
import com.palmD.Service.LoginLog_serv;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final LoginLog_serv loginLogServ;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {    	
        System.out.println("LoginSuccessHandler.onAuthenticationSuccess");
//      Json으로 데이터를 변환해서 ajax로 돌려주는 기능.
//      https://programmer93.tistory.com/42
        ObjectMapper mapper = new ObjectMapper();
        UsersLoginResp_dto usersLoginRespDto = new UsersLoginResp_dto();
        usersLoginRespDto.setCode(HttpStatus.OK);
        usersLoginRespDto.setStatus("SUCCESS");
        usersLoginRespDto.setMessage("로그인 성공");
        
        loginLogServ.saveLogging(request, authentication.getName());
        
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(mapper.writeValueAsString(usersLoginRespDto));
        response.getWriter().flush();
    }
}
