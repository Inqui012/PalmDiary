package com.palmD.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.palmD.DTO.MemosAddEdit_dto;
import com.palmD.DTO.MemosResp_dto;
import com.palmD.Entity.CalGroups;
import com.palmD.Entity.Memos;
import com.palmD.Entity.Users;
import com.palmD.Repository.CalGroups_repo;
import com.palmD.Repository.Memos_repo;
import com.palmD.Repository.Users_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Memos_serv {
	
	private final Memos_repo memosRepo;
	private final CalGroups_repo calGroupsRepo;
	private final Users_repo usersRepo;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	
	public Memos addMemo (MemosAddEdit_dto memosAddEditDto, String userId) {
		Users currentUser = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음"));
		CalGroups selectedGroup = calGroupsRepo.findById(memosAddEditDto.getGroupId()).orElseThrow(() -> new EntityNotFoundException("그룹을 찾을 수 없음"));
		Memos addMemo = Memos.createMemo(memosAddEditDto, currentUser, selectedGroup, formatter);
		return memosRepo.save(addMemo);
	}
	
	public Memos editMemo (MemosAddEdit_dto memosAddEditDto) {
		Memos editMemo = memosRepo.findById(memosAddEditDto.getMemoId()).orElseThrow(() -> new EntityNotFoundException("메모를 찾을 수 없음"));
		CalGroups selectedGroup = calGroupsRepo.findById(memosAddEditDto.getGroupId()).orElseThrow(() -> new EntityNotFoundException("그룹을 찾을 수 없음"));
		editMemo.updateMemo(memosAddEditDto, selectedGroup, formatter);
		return memosRepo.save(editMemo);
	}
	
	public void deleteMemo (Long memoId) {
		Memos deleteMemo = memosRepo.findById(memoId).orElseThrow(() -> new EntityNotFoundException("메모를 찾을 수 없음"));
		memosRepo.delete(deleteMemo);
	}
	
	public List<MemosResp_dto> callAllMemos (String userId) {
		Users currentUser = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음"));
		List<Memos> allMemos = memosRepo.findByUserId(currentUser);
		List<MemosResp_dto> allMemosResp = new ArrayList<>();
		for(Memos memo : allMemos) {
			MemosResp_dto memoResp = new MemosResp_dto();
			memoResp.setMemoDetail(memo.getMemoDetail());
			memoResp.setMemoDate(memo.getMemoDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			memoResp.setGroupId(memo.getGroupId().getGroupsId());
			allMemosResp.add(memoResp);
		}
		return allMemosResp;
	}
	
}
