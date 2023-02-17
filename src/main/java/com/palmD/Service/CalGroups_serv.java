package com.palmD.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.palmD.Constant.GroupsDiv;
import com.palmD.DTO.CalGroupsAddEdit_dto;
import com.palmD.DTO.CallAllGroups_dto;
import com.palmD.Entity.CalGroups;
import com.palmD.Entity.Users;
import com.palmD.Repository.CalGroups_repo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CalGroups_serv {
	
	private final CalGroups_repo calGroupsRepo;
	private final Users_serv usersServ;
	
	public List<CalGroups> initGroups (Users findUsers) {
		List<CalGroups> basicGroupsList = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			CalGroups basicGroups = new CalGroups();
			basicGroups.setGroupsNd("NONE");
			basicGroups.setUserId(findUsers);
			basicGroups.setGroupsBgcolor("#8a8cd9");
			basicGroups.setGroupsDiv(GroupsDiv.SCHEDULES);
			basicGroups.setGroupsSt("기본 일정 그룹");
			if(i == 1) {
				basicGroups.setGroupsDiv(GroupsDiv.ACCOUNTS);
				basicGroups.setGroupsSt("수입");
			}
			if(i == 2) {
				basicGroups.setGroupsDiv(GroupsDiv.ACCOUNTS);
				basicGroups.setGroupsSt("지출");
			}
			if(i == 3) {
				basicGroups.setGroupsDiv(GroupsDiv.MEMO);
				basicGroups.setGroupsSt("기본 메모 그룹");				
			}
			basicGroupsList.add(basicGroups);			
		}
		return calGroupsRepo.saveAll(basicGroupsList);
	}
	
	@Transactional(readOnly = true)
	public List<CallAllGroups_dto> callAllParentsGroups(String userId, GroupsDiv groupsDiv){
		Users currentUser = usersServ.findUser(userId);
		List<CallAllGroups_dto> allGroupsList = new ArrayList<>();
		List<CalGroups> allParents = calGroupsRepo.findParentsGroups(currentUser, groupsDiv);
		for(CalGroups parents : allParents) {
			CallAllGroups_dto parentsDto = new CallAllGroups_dto();
			parentsDto.setGroupsId(parents.getGroupsId());
			parentsDto.setGroupsName(parents.getGroupsSt());
			parentsDto.setGroupsBgcolor(parents.getGroupsBgcolor());
			List<CalGroups> allChilds = callAllChildGroups(currentUser, groupsDiv, parents.getGroupsSt());
			List<CallAllGroups_dto> childList = new ArrayList<>();
			for(CalGroups child : allChilds) {
				CallAllGroups_dto childsDto = new CallAllGroups_dto();
				childsDto.setGroupsId(child.getGroupsId());
				childsDto.setGroupsName(child.getGroupsNd());
				childsDto.setGroupsBgcolor(child.getGroupsBgcolor());
				childsDto.setChildrens(null);
				childList.add(childsDto);
			}
			parentsDto.setChildrens(childList);
			allGroupsList.add(parentsDto);
		}
		return allGroupsList;
	}
	
	@Transactional(readOnly = true)
	private List<CalGroups> callAllChildGroups(Users findUsers, GroupsDiv groupsDiv, String groupsSt) {
		return calGroupsRepo.findChildGroups(findUsers, groupsDiv, groupsSt);		
	}
	
	public CalGroups addGroups (CalGroupsAddEdit_dto CalGroupsAddEditdto, String userId) {
		Users currentUser = usersServ.findUser(userId);
		CalGroups groupsToAdd = CalGroups.createGroup(CalGroupsAddEditdto, currentUser);
		checkGroups(groupsToAdd, currentUser);
		return calGroupsRepo.save(groupsToAdd);
	}
	
	public CalGroups editGroups (CalGroupsAddEdit_dto CalGroupsAddEditdto) {
		CalGroups editGroup = findCalGroup(CalGroupsAddEditdto.getGroupId());
		editGroup.updateGroup(CalGroupsAddEditdto);
		return calGroupsRepo.save(editGroup);
	}
	
	public void deleteGroups (Long GroupId) {
		CalGroups deleteGroup = findCalGroup(GroupId);
		calGroupsRepo.delete(deleteGroup);
	}
	
	public CalGroups findCalGroup (Long calGroupsId) {
		return calGroupsRepo.findById(calGroupsId).orElseThrow(() -> new EntityNotFoundException("그룹을 찾을 수 없음"));
	}
	
	@Transactional(readOnly = true)
	private void checkGroups (CalGroups groupsToAdd, Users currentUser) {
		List<CalGroups> usersGroups = calGroupsRepo.findByUserIdAndGroupsDivOrderByGroupsStAscGroupsNdAsc(currentUser, groupsToAdd.getGroupsDiv());
		if(usersGroups == null) return;
		for(CalGroups g : usersGroups) {
			boolean isGroupsStSame = StringUtils.equals(g.getGroupsSt(), groupsToAdd.getGroupsSt());
			boolean isGroupsNdSame = StringUtils.equals(g.getGroupsNd(), groupsToAdd.getGroupsNd());
			if(isGroupsStSame && isGroupsNdSame) {
				throw new IllegalArgumentException("이미 존재하는 상위 그룹입니다.");
			};
		}
	}
}
