package com.palmD.Controller;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.palmD.DTO.CalGroupsAddEdit_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.CalGroups;
import com.palmD.Entity.Users;
import com.palmD.Service.CalGroups_serv;
import com.palmD.Service.Users_serv;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CalGroups_conTest {
	
	@Autowired
	private Users_serv usersServ;
	@Autowired
	private CalGroups_serv calGroupsServ;
	
	public Users register(String id, String pw, String email) {
		UsersRegist_dto usersRegistDto = new UsersRegist_dto();
		usersRegistDto.setRegistId(id);
		usersRegistDto.setRegistPw(pw);
		usersRegistDto.setRegistEmail(email);
		return usersServ.registerUser(usersRegistDto);
	}
	
	@Test
	@DisplayName("GroupsAdd")
	void testGroupsAdd() {
		Users testUser = this.register("users", "12341234", "abc@abc.com");
		CalGroupsAddEdit_dto groupsDto = new CalGroupsAddEdit_dto();
		groupsDto.setGroupsDiv("MEMO");
		groupsDto.setGroupsSt("Groups_1");
		groupsDto.setGroupsNd("NULL");
		groupsDto.setGroupsBgcolor("#8c8c8c");
		System.err.println(testUser.getUsersId());
		System.err.println("here");
		calGroupsServ.addGroups(groupsDto, testUser.getUsersId());
	}
	
	@Test
	@DisplayName("GroupsEdit")
	void testGroupsEdit() {
		Users testUser = this.register("users", "12341234", "abc@abc.com");
		CalGroupsAddEdit_dto groupsAddDto = new CalGroupsAddEdit_dto();
		groupsAddDto.setGroupsDiv("MEMO");
		groupsAddDto.setGroupsSt("Groups_1");
		groupsAddDto.setGroupsNd("NULL");
		groupsAddDto.setGroupsBgcolor("#8c8c8c");
		CalGroups group = calGroupsServ.addGroups(groupsAddDto, testUser.getUsersId());
		
		CalGroupsAddEdit_dto groupsAddDto2 = new CalGroupsAddEdit_dto();
		groupsAddDto2.setGroupId(group.getGroupsId());
		groupsAddDto2.setGroupsDiv("MEMO");
		groupsAddDto2.setGroupsSt("Groups_1");
		groupsAddDto2.setGroupsNd("NONE");
		groupsAddDto2.setGroupsBgcolor("#8c8c8c");
		calGroupsServ.editGroups(groupsAddDto2);
	}

	@Test
	@DisplayName("GroupsDelete")
	void testGroupsDelete () {
		Users testUser = this.register("users", "12341234", "abc@abc.com");
		CalGroupsAddEdit_dto groupsAddDto = new CalGroupsAddEdit_dto();
		groupsAddDto.setGroupsDiv("MEMO");
		groupsAddDto.setGroupsSt("Groups_1");
		groupsAddDto.setGroupsNd("NULL");
		groupsAddDto.setGroupsBgcolor("#8c8c8c");
		CalGroups group = calGroupsServ.addGroups(groupsAddDto, testUser.getUsersId());
		
		calGroupsServ.deleteGroups(group.getGroupsId());
	}
}
