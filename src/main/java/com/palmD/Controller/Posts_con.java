package com.palmD.Controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.palmD.DTO.PostsAddEdit_dto;
import com.palmD.DTO.PostsResp_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.Posts;
import com.palmD.Service.PostsImges_serv;
import com.palmD.Service.Posts_serv;
import com.palmD.Service.Users_serv;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Post")
public class Posts_con {
	
	private final Posts_serv postsServ;
	private final Users_serv usersServ;
	private final PostsImges_serv postsImgesServ;
	
	@PostMapping("/toggleAdd")
	public String replaceFrag () {
		return "Frag/PostsFrag :: post-addEdit";
	}
	
	@PostMapping("/loadMore")
	public String ste (@RequestParam("userId") String userId, @RequestParam("targetPage") String pageNum, Principal principal, Model model) {
		Map<String, Object> attr = new HashMap<>();
		Pageable pageable = PageRequest.of(Integer.parseInt(pageNum), 5);

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
			System.err.println("con1");
			attr.put("UserPosts", postsServ.callAllPosts(userId, pageable));
			System.err.println("con2");
			attr.put("PostsDates", postsServ.callAllPostsDates(userId));
			System.err.println("con3");
		} catch (Exception e) {
			model.addAllAttributes(attr);
			return "ErrPage/PostNotFound :: postErr";
		}
		model.addAllAttributes(attr);
		System.err.println("????");
		return "UserPage/Post :: post-view";
	}

	
	@PostMapping("/add")
	public ResponseEntity postAdd(@RequestPart(value = "postTexts") PostsAddEdit_dto postsAddEditDto, 
								  @RequestParam(value = "postImages", required = false) List<MultipartFile> postImages, Principal principal) {
		try {
			Posts addPost = postsServ.addPost(postsAddEditDto, principal.getName());
			if(postImages != null) {
				postsImgesServ.addPostsImges(postImages, addPost);				
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("게시글 업로드 완료", HttpStatus.OK);
	}
}
