package com.palmD.Controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import com.palmD.Constant.GroupsDiv;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Service.CalGroups_serv;
import com.palmD.Service.Memos_serv;
import com.palmD.Service.Posts_serv;
import com.palmD.Service.Schedules_serv;
import com.palmD.Service.Users_serv;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{usersId}")
public class UserPage_con {
	
	private final CalGroups_serv calGroupsServ;
	private final Users_serv usersServ;
	private final Schedules_serv seSchedulesServ;
	private final Memos_serv memosServ;
	private final Posts_serv postsServ;
	
//	유저 메인화면
	@GetMapping("/")
	public String userHome (@PathVariable String usersId, Model model, Principal principal) {
		model.addAttribute("UserRegistDto", new UsersRegist_dto());
		if(principal == null) model.addAttribute("UserRegistDto",  new UsersRegist_dto());
		else {
			try {
				model.addAttribute("User", usersServ.loadUserProfile(principal.getName()));				
			} catch (Exception e) {
				return "ErrPage/UserNotFound";
			}
		};
		return "UserPage/Home";
	}

// 유저 캘린더 화면
	@GetMapping("/Schedule")
	public String scheduleView (@PathVariable("usersId") String userId, Model model, Principal principal) {
		Map<String, Object> attr = new HashMap<>();
		if(principal == null) attr.put("UserRegistDto",  new UsersRegist_dto());
		else {
			try {
				attr.put("User", usersServ.loadUserProfile(principal.getName()));				
			} catch (Exception e) {
				return "ErrPage/UserNotFound";
			}
		};

		attr.put("GroupsSche", calGroupsServ.callAllParentsGroups(userId, GroupsDiv.SCHEDULES));
		attr.put("GroupsAccn", calGroupsServ.callAllParentsGroups(userId, GroupsDiv.ACCOUNTS));
		attr.put("GroupsMemo", calGroupsServ.callAllParentsGroups(userId, GroupsDiv.MEMO));
		attr.put("UserEvents", seSchedulesServ.callAllSchedules(userId));
		attr.put("UserMemos", memosServ.callAllMemos(userId));
		
		if(principal != null && StringUtils.equals(userId, principal.getName())) {
			attr.put("OwnedUser", true);
		} else {
			attr.put("OwnedUser", false);
		}
		model.addAllAttributes(attr);
		return "UserPage/Schedule";
	}
	
	@GetMapping("/Post")
	public String postView (@PathVariable("usersId") String userId, Model model, Principal principal) {
		Map<String, Object> attr = new HashMap<>();
		Pageable pageable = PageRequest.of(0, 5);
		if(principal == null) {
			attr.put("UserRegistDto",  new UsersRegist_dto());
			attr.put("OwnedUser", false);
		} else {
			try {
				attr.put("User", usersServ.loadUserProfile(principal.getName()));
			} catch (Exception e) {
				model.addAllAttributes(attr);
				return "ErrPage/UserNotFound";
			}
			if(StringUtils.equals(userId, principal.getName())) attr.put("OwnedUser", true);
		};
		try {
			attr.put("UserPosts", postsServ.callAllPosts(userId, pageable));
			attr.put("PostsDates", postsServ.callAllPostsDates(userId));
		} catch (Exception e) {
			model.addAllAttributes(attr);
			return "ErrPage/PostNotFound";
		}
		model.addAllAttributes(attr);
		return "UserPage/Post";
	}
	
//	@GetMapping("/test")
//	public String test (@PathVariable("usersId") String userId, Optional<Integer> page, Model model, Principal principal) {
//		Map<String, Object> attr = new HashMap<>();
//		if(principal == null) {
//			attr.put("UserRegistDto",  new UsersRegist_dto());
//			attr.put("OwnedUser", false);
//		} else {
//			try {
//				attr.put("User", usersServ.loadUserProfile(principal.getName()));
//			} catch (Exception e) {
//				model.addAllAttributes(attr);
//				return "ErrPage/UserNotFound";
//			}
//			if(StringUtils.equals(userId, principal.getName())) attr.put("OwnedUser", true);
//		};
//		model.addAllAttributes(attr);
//
//		model.addAttribute("testpage", postsServ.callAllPoststest(userId, pageable));
//		return "Testing/pageTest";
//	}
}
