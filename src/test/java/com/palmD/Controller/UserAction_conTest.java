package com.palmD.Controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.palmD.DTO.UsersLogin_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.Users;
import com.palmD.Service.Users_serv;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@AutoConfigureMockMvc
//Junit 에서는 이걸로는 의존성 주입이 안되는가보네?
//@RequiredArgsConstructor
class UserAction_conTest {

	@Autowired
	private Users_serv usersServ;
	@Autowired
	private MockMvc mockMvc;
	
	public Users register(String id, String pw, String email) {
		UsersRegist_dto usersRegistDto = new UsersRegist_dto();
		usersRegistDto.setRegistId(id);
		usersRegistDto.setRegistPw(pw);
		usersRegistDto.setRegistEmail(email);
		return usersServ.registerUser(usersRegistDto);
	}

	@Test
	@DisplayName("Register")
	public void testRegister() {
		this.register("testUser", "1234", "abc@abc");
	}
	
//	커스텀 필터로 하는 로그인은 어떻게 테스트하는지 모르겠네 
	@Test
	@DisplayName("Login_Success")
	public void testLoginSuccess () throws Exception {
		this.register("testuser", "12341234", "abc@abc.com");
		UsersLogin_dto u = new UsersLogin_dto();
//		u.setLoginId(null);
//		mockMvc.perform(post("/user/login").with(null).;
	}
	
	@Test
	@DisplayName("Login_Fail")
	public void testLoginFail () throws Exception {
		this.register("testuser", "12341234", "abc@abc.com");
		mockMvc.perform(formLogin("/user/login").userParameter("userId").passwordParam("userPw").user("testuser").password("12341235"))
		.andExpect(SecurityMockMvcResultMatchers.unauthenticated());
	}
	
	@Test
	@DisplayName("deleteUser")
	void deleteUserTest () {
		Users testUser = this.register("testUser", "1234", "abc@abc");
		usersServ.deleteUser(testUser.getUsersId());
	}
	
}
