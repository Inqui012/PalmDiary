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

import com.palmD.DTO.MemosAddEdit_dto;
import com.palmD.Service.Memos_serv;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Memo")
public class Memo_con {
	
	private final Memos_serv memosServ;
	
	@PostMapping("/add")
	public @ResponseBody ResponseEntity<String> memoAdd (@RequestBody MemosAddEdit_dto memosAddEditDto, Principal principal) {
		try {
			memosServ.addMemo(memosAddEditDto, principal.getName());
		} catch (Exception e) {
			return new ResponseEntity<String>("메모 추가중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("메모 추가 완료", HttpStatus.OK);
	}
	
	@PostMapping("/edit")
	public @ResponseBody ResponseEntity<String> memoEdit (@RequestBody MemosAddEdit_dto memosAddEditDto) {
		try {
			memosServ.editMemo(memosAddEditDto);
		} catch (Exception e) {
			return new ResponseEntity<String>("메모 추가중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("메모 추가 완료", HttpStatus.OK);
	}
	
	@PostMapping("/delete/{memoId}")
	public @ResponseBody ResponseEntity<String> memoDelete (@RequestParam("memoId") Long memoId) {
		try {
			memosServ.deleteMemo(memoId);
		} catch (Exception e) {
			return new ResponseEntity<String>("메모 삭제중에 오류발생", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("메모 삭제 완료", HttpStatus.OK);
	}
}
