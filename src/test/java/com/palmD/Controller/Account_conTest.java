package com.palmD.Controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.palmD.DTO.AccountsAddEdit_dto;
import com.palmD.DTO.CalGroupsAddEdit_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.Accounts;
import com.palmD.Entity.CalGroups;
import com.palmD.Entity.Users;
import com.palmD.Service.Accounts_serv;
import com.palmD.Service.CalGroups_serv;
import com.palmD.Service.Users_serv;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class Account_conTest {

	@Autowired
	private Users_serv usersServ;
	@Autowired
	private CalGroups_serv calGroupsServ;
	@Autowired
	private Accounts_serv accountsServ;
	
	public Users register(String id, String pw, String email) {
		UsersRegist_dto usersRegistDto = new UsersRegist_dto();
		usersRegistDto.setRegistId(id);
		usersRegistDto.setRegistPw(pw);
		usersRegistDto.setRegistEmail(email);
		return usersServ.registerUser(usersRegistDto);
	}
	
	public CalGroups groupsAdd(Users user) {
		CalGroupsAddEdit_dto groupsDto = new CalGroupsAddEdit_dto();
		groupsDto.setGroupsDiv("MEMO");
		groupsDto.setGroupsSt("Groups_1");
		groupsDto.setGroupsNd("NONE");
		groupsDto.setGroupsBgcolor("#8c8c8c");
		return calGroupsServ.addGroups(groupsDto, user.getUsersId());
	}
	
	@Test
	@DisplayName("AccountAddTest")
	void accountAddTest() {
		Users testuser = register("test", "1231233", "aaa@aaa");
		CalGroups testGroup = groupsAdd(testuser);
		AccountsAddEdit_dto dto = new AccountsAddEdit_dto();
		dto.setAccGroupId(testGroup.getGroupsId());
		dto.setAccMoney(1000);
		dto.setAccName("testttttt");
		dto.setAccStatus("INCOME");
		dto.setAccDetail("NONE");
		dto.setAccDate("2023-01-22T00:00:00");
		accountsServ.addAccount(dto, testuser.getUsersId());
	}

	@Test
	@DisplayName("AccountEditTest")
	void accountEditTest() {
		Users testuser = register("test", "1231233", "aaa@aaa");
		CalGroups testGroup = groupsAdd(testuser);
		AccountsAddEdit_dto dto = new AccountsAddEdit_dto();
		dto.setAccGroupId(testGroup.getGroupsId());
		dto.setAccMoney(1000);
		dto.setAccName("testttttt");
		dto.setAccStatus("INCOME");
		dto.setAccDetail("NONE");
		dto.setAccDate("2023-01-22T00:00:00");
		Accounts acc = accountsServ.addAccount(dto, testuser.getUsersId());

		AccountsAddEdit_dto dto2 = new AccountsAddEdit_dto();
		dto2.setAccId(acc.getAccId());
		dto2.setAccDate("2023-01-23T00:00:00");
		dto2.setAccMoney(2000);
		dto2.setAccGroupId(testGroup.getGroupsId());
		dto2.setAccName("testtttttsss");
		dto2.setAccStatus("EXPEND");
		dto2.setAccDetail("NONE");
		
		accountsServ.editAccount(dto2);
	}
	
	@Test
	@DisplayName("AccountDeleteTest")
	void accountDeleteTest () {
		Users testuser = register("test", "1231233", "aaa@aaa");
		CalGroups testGroup = groupsAdd(testuser);
		AccountsAddEdit_dto dto = new AccountsAddEdit_dto();
		dto.setAccGroupId(testGroup.getGroupsId());
		dto.setAccMoney(1000);
		dto.setAccName("testttttt");
		dto.setAccStatus("INCOME");
		dto.setAccDetail("NONE");
		dto.setAccDate("2023-01-22T00:00:00");
		Accounts acc = accountsServ.addAccount(dto, testuser.getUsersId());
		
		accountsServ.deleteAccount(acc.getAccId());
	}
	
	@Test
	@DisplayName("CallSumedValue")
	void callSumedValue () {
		Users testuser = register("test", "1231233", "aaa@aaa");
		CalGroups testGroup = groupsAdd(testuser);
		for(int i = 0; i < 5; i++) {
			AccountsAddEdit_dto dto = new AccountsAddEdit_dto();
			dto.setAccGroupId(testGroup.getGroupsId());
			dto.setAccMoney(1000);
			dto.setAccName("testttttt");
			dto.setAccStatus("INCOME");
			dto.setAccDetail("NONE");
			dto.setAccDate("2023-01-22T00:00:00");
			accountsServ.addAccount(dto, testuser.getUsersId());			
		}
		for(int i = 0; i < 5; i++) {
			AccountsAddEdit_dto dto = new AccountsAddEdit_dto();
			dto.setAccGroupId(testGroup.getGroupsId());
			dto.setAccMoney(2000);
			dto.setAccName("testttttt");
			dto.setAccStatus("EXPEND");
			dto.setAccDetail("NONE");
			dto.setAccDate("2023-01-26T00:00:00");
			accountsServ.addAccount(dto, testuser.getUsersId());			
		}
		for(int i = 0; i < 5; i++) {
			AccountsAddEdit_dto dto = new AccountsAddEdit_dto();
			dto.setAccGroupId(testGroup.getGroupsId());
			dto.setAccMoney(3000);
			dto.setAccName("testttttt");
			dto.setAccStatus("SAVING");
			dto.setAccDetail("NONE");
			dto.setAccDate("2023-01-23T00:00:00");
			accountsServ.addAccount(dto, testuser.getUsersId());			
		}
		
		Map<String, List<Object[]>> test = accountsServ.callSumedAccount(testuser.getUsersId());
	}
}
