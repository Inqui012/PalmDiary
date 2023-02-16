package com.palmD.Entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.palmD.DTO.CalGroupsAddEdit_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Service.CalGroups_serv;
import com.palmD.Service.Users_serv;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class GroupsTest {

	@Autowired
	private Users_serv usersServ;
	
	public Users register(String id, String pw, String email) {
		UsersRegist_dto usersRegistDto = new UsersRegist_dto();
		usersRegistDto.setRegistId(id);
		usersRegistDto.setRegistPw(pw);
		usersRegistDto.setRegistEmail(email);
		return usersServ.registerUser(usersRegistDto);
	}
	
	@Test
	@DisplayName("id Test")
	public void testId () {
		Users user = register("test", "1234", "aaa@aaa.com");
		CalGroupsAddEdit_dto groupsDto = new CalGroupsAddEdit_dto();
		groupsDto.setGroupsDiv("MEMO");
		groupsDto.setGroupsSt("Groups_1");
		groupsDto.setGroupsNd("NULL");
		groupsDto.setGroupsBgcolor("#8c8c8c");
		CalGroups test = CalGroups.createGroup(groupsDto, user);
		System.err.println(test.getGroupsId());
	}

}
