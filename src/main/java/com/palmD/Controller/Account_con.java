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

import com.palmD.DTO.AccountsAddEdit_dto;
import com.palmD.DTO.SchedulesAddEdit_dto;
import com.palmD.Service.Schedules_serv;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Account")
public class Account_con {
	
	private final Schedules_serv schedulesServ;
	
	@PostMapping("/add")
	public @ResponseBody ResponseEntity accountAdd (@RequestBody AccountsAddEdit_dto accountsAddEditDto, Principal principal) {
		try {
			
		} catch (Exception e) {
			return new ResponseEntity<String>("일정 추가중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("일정 추가 완료", HttpStatus.OK);
	}
	
	@PostMapping("/edit")
	public @ResponseBody ResponseEntity accountEdit (@RequestBody AccountsAddEdit_dto accountsAddEditDto) {
		try {
			
		} catch (Exception e) {
			return new ResponseEntity<String>("일정 추가중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("일정 추가 완료", HttpStatus.OK);
	}
	
	@PostMapping("/delete/{accId}")
	public @ResponseBody ResponseEntity accountDelete (@RequestParam("accId") Long accId) {
		try {
			
		} catch (Exception e) {
			return new ResponseEntity<String>("일정 삭제중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("일정 삭제 완료", HttpStatus.OK);
	}
}
