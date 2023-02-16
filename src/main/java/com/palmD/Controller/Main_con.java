package com.palmD.Controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.palmD.DTO.UsersRegist_dto;

@Controller
public class Main_con {

	@GetMapping("/")
	public String main_page (Model model, Principal principal) {
		model.addAttribute("UserRegistDto", new UsersRegist_dto());
		if(principal == null) return "Main";
		else return "redirect:/" + principal.getName() + "/";
	}
}
