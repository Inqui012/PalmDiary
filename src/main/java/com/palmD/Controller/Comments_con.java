package com.palmD.Controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.palmD.DTO.CommentsAddEdit_dto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Comment")
public class Comments_con {
	
	@PostMapping("/add")
	public ResponseEntity addComment (@RequestBody CommentsAddEdit_dto commentsAddEditDto, Principal principal) {
		return new ResponseEntity<String>("댓글 작성 완료", HttpStatus.OK);
	}
}
