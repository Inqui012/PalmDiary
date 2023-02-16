package com.palmD.Controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.palmD.DTO.SchedulesAddEdit_dto;
import com.palmD.Service.Schedules_serv;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Schedule")
public class Schedule_con {
	
	private final Schedules_serv schedulesServ;
	
	@PostMapping("/add")
	public @ResponseBody ResponseEntity<String> scheduleAdd (@RequestBody SchedulesAddEdit_dto schedulesAddEditDto, Principal principal) {
		try {
			schedulesServ.addSchedule(schedulesAddEditDto, principal.getName());
		} catch (Exception e) {
			return new ResponseEntity<String>("일정 추가중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("일정 추가 완료", HttpStatus.OK);
	}
	
	@PostMapping("/edit")
	public @ResponseBody ResponseEntity<String> scheduleEdit (@RequestBody SchedulesAddEdit_dto schedulesAddEditDto) {
		try {
			schedulesServ.editSchedule(schedulesAddEditDto);
		} catch (Exception e) {
			return new ResponseEntity<String>("일정 추가중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("일정 추가 완료", HttpStatus.OK);
	}
	
	@PostMapping("/delete/{scheId}")
	public @ResponseBody ResponseEntity<String> scheduleDelete (@RequestParam("scheId") Long scheId) {
		try {
			schedulesServ.deleteSchedule(scheId);
		} catch (Exception e) {
			return new ResponseEntity<String>("일정 삭제중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("일정 삭제 완료", HttpStatus.OK);
	}
}
