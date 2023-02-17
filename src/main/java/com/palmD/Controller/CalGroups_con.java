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

import com.palmD.DTO.CalGroupsAddEdit_dto;
import com.palmD.Service.CalGroups_serv;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/CalGroups")
public class CalGroups_con {
	
	private final CalGroups_serv groupsServ;
	
	@PostMapping("/add")
	public @ResponseBody ResponseEntity<String> groupsAdd (@RequestBody CalGroupsAddEdit_dto CalGroupsAddEditdto, Principal principal) {

		try {
			groupsServ.addGroups(CalGroupsAddEditdto, principal.getName());	
		} catch (Exception e) {
			return new ResponseEntity<String>("그룹 추가중에 에러 발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("그룹추가 성공", HttpStatus.OK);
	}
	
	@PostMapping("/edit")
	public @ResponseBody ResponseEntity<String> groupsEdit (@RequestBody CalGroupsAddEdit_dto CalGroupsAddEditdto) {
		try {
			groupsServ.editGroups(CalGroupsAddEditdto);
		} catch (Exception e) {
			return new ResponseEntity<String>("그룹 수정중에 에러 발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("그룹수정 성공", HttpStatus.OK);
	}
	
	@PostMapping("/delete/{groupId}")
	public @ResponseBody ResponseEntity<String> groupsDelete (@RequestParam("groupId") Long groupId) {
		try {
			groupsServ.deleteGroups(groupId);
		} catch (Exception e) {
			return new ResponseEntity<String>("그룹 삭제중에 에러 발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("그룹삭제 성공", HttpStatus.OK);
	}
}
