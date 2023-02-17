package com.palmD.Controller;

import java.security.Principal;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.palmD.DTO.CommentsAddEdit_dto;
import com.palmD.Service.Comments_serv;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Comment")
public class Comments_con {
	
	private final Comments_serv commentsServ;
	
	@PostMapping("/add")
	public ResponseEntity addComment (@RequestBody CommentsAddEdit_dto commentsAddEditDto, Principal principal) {
		System.err.println(principal);
		if(principal == null) return new ResponseEntity<String>("Need Login", HttpStatus.BAD_REQUEST);
		commentsServ.addComment(commentsAddEditDto, principal.getName());
		return new ResponseEntity<String>("댓글 작성 완료", HttpStatus.OK);
	}
}
