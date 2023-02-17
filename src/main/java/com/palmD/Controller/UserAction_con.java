package com.palmD.Controller;

import java.security.Principal;
import java.util.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.Users;
import com.palmD.Service.CalGroups_serv;
import com.palmD.Service.Users_serv;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAction_con {
	
	private final Users_serv usersServ;
	private final CalGroups_serv calGroupsServ;

	
	@PostMapping("/regist")
	public @ResponseBody ResponseEntity user_register(@RequestBody @Valid UsersRegist_dto UserRegistDto,
														BindingResult bindingResult) {
		Map<String, String> errorMsg = new HashMap<>();
		if(bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().forEach(e -> errorMsg.put(e.getField(), e.getDefaultMessage()));
			return new ResponseEntity<Map<String, String>>(errorMsg, HttpStatus.BAD_REQUEST);
		}
		try {
			Users registedUser = usersServ.registerUser(UserRegistDto);
			calGroupsServ.initGroups(registedUser);
		} catch (Exception e) {
			errorMsg.put("Exception", e.getMessage());
			return new ResponseEntity<Map<String, String>>(errorMsg, HttpStatus.BAD_REQUEST);			
		}
		return new ResponseEntity<String> ("회원가입 완료", HttpStatus.OK);
	}

	@GetMapping("/delete")
	public @ResponseBody ResponseEntity<String> userDelete(Principal principal) {
		try {
			usersServ.deleteUser(principal.getName());			
		} catch (Exception e) {
			return new ResponseEntity<String> ("회원탈퇴중 오류 발생", HttpStatus.BAD_REQUEST);			
		}
		return new ResponseEntity<String> ("회원탈퇴 완료", HttpStatus.OK);
	}
}
