package com.palmD.Controller;


import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.palmD.DTO.CalGroupsAddEdit_dto;
import com.palmD.DTO.SchedulesAddEdit_dto;
import com.palmD.DTO.SchedulesRespReccur_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.CalGroups;
import com.palmD.Entity.Schedules;
import com.palmD.Entity.Users;
import com.palmD.Service.CalGroups_serv;
import com.palmD.Service.Schedules_serv;
import com.palmD.Service.Users_serv;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class Schedule_conTest {

	@Autowired
	private Users_serv usersServ;
	@Autowired
	private Schedules_serv schedulesServ;
	@Autowired
	private CalGroups_serv calGroupsServ;

	
	public Users register(String id, String pw, String email) {
		UsersRegist_dto usersRegistDto = new UsersRegist_dto();
		usersRegistDto.setRegistId(id);
		usersRegistDto.setRegistPw(pw);
		usersRegistDto.setRegistEmail(email);
		return usersServ.registerUser(usersRegistDto);
	}
	
	public CalGroups makeGroup () {
		Users testUser = register("test", "12341234", "aaa@aaa.aaa");
		CalGroupsAddEdit_dto dto = new CalGroupsAddEdit_dto();
		dto.setGroupsBgcolor("#ffffff");
		dto.setGroupsDiv("ACCOUNTS");
		dto.setGroupsSt("testgroup");
		dto.setGroupsNd("NONE");
		return calGroupsServ.addGroups(dto, testUser.getUsersId());
	}
	
	@Test
	@DisplayName("addScheduleTest")
	@Transactional
	void addScheduleTest() {
		register("test1", "12341234", "aaa@aaa.aaa");
		CalGroups testGroup = makeGroup();
		SchedulesAddEdit_dto dto = new SchedulesAddEdit_dto();
		dto.setScheName("test1");
		dto.setIsAllDay("TRUE");
		dto.setShceDetail("NONE");
		dto.setStrDate("2023-02-29T00:00:00");
		dto.setEndDate("2023-03-10T00:00:00");
		dto.setReccurFreq("NONE");
		dto.setReccurInterval(0);
		dto.setShceGroupId(testGroup.getGroupsId());
		
		schedulesServ.addSchedule(dto, "test");
	}
	
	@Test
	@DisplayName("editScheduleTest")
	@Transactional
	void editScheduleTest() {
		CalGroups testGroup = makeGroup();
		SchedulesAddEdit_dto dto = new SchedulesAddEdit_dto();
		dto.setScheName("test1");
		dto.setIsAllDay("TRUE");
		dto.setShceDetail("NONE");
		dto.setStrDate("2023-02-29T00:00:00");
		dto.setEndDate("2023-03-10T00:00:00");
		dto.setReccurFreq("NONE");
		dto.setReccurInterval(0);
		dto.setShceGroupId(testGroup.getGroupsId());		
		Schedules schedule = schedulesServ.addSchedule(dto, "test");
		
		SchedulesAddEdit_dto dto2 = new SchedulesAddEdit_dto();
		dto2.setShceId(schedule.getScheId());
		dto2.setScheName("edited");
		dto2.setIsAllDay("TRUE");
		dto2.setShceDetail("NONE");
		dto2.setStrDate("2023-02-29T00:00:00");
		dto2.setEndDate("2023-03-10T00:00:00");
		dto2.setReccurFreq("NONE");
		dto2.setReccurInterval(0);
		dto2.setShceGroupId(schedule.getCalGroups().getGroupsId());
		schedulesServ.editSchedule(dto2);
	}

	@Test
	@DisplayName("deleteScheduleTest")
	@Transactional
	void deleteScheduleTest() {
		CalGroups testGroup = makeGroup();
		SchedulesAddEdit_dto dto = new SchedulesAddEdit_dto();
		dto.setScheName("test1");
		dto.setIsAllDay("TRUE");
		dto.setShceDetail("NONE");
		dto.setStrDate("2023-02-29T00:00:00");
		dto.setEndDate("2023-03-10T00:00:00");
		dto.setReccurFreq("NONE");
		dto.setReccurInterval(0);
		dto.setShceGroupId(testGroup.getGroupsId());		
		Schedules schedule = schedulesServ.addSchedule(dto, "test");
		
		schedulesServ.deleteSchedule(schedule.getScheId());
	}

	@Test
	@DisplayName("callScheduleTest")
	@Transactional
	void callScheduleTest () {
		CalGroups testGroup = makeGroup();
		SchedulesAddEdit_dto dto = new SchedulesAddEdit_dto();
		dto.setScheName("test1");
		dto.setIsAllDay("TRUE");
		dto.setShceDetail("NONE");
		dto.setStrDate("2023-02-29T10:00:00");
		dto.setEndDate("2023-02-29T12:00:00");
		dto.setReccurFreq("WEEKLY");
		dto.setReccurInterval(2);
		dto.setShceGroupId(testGroup.getGroupsId());		
		Schedules schedule = schedulesServ.addSchedule(dto, "test");
		SchedulesRespReccur_dto.responseReccurSche(schedule);
	}
}
