package com.palmD.Entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.palmD.Constant.GroupsDiv;
import com.palmD.DTO.CalGroupsAddEdit_dto;

import lombok.*;

@Data
@Entity
@Table(name = "calGroups")
public class CalGroups {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long groupsId;
	
	@JoinColumn(name = "userId")
	@OneToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@Enumerated(EnumType.STRING)
	private GroupsDiv groupsDiv;
	
	@Column(nullable = false)
	private String groupsSt;	
	private String groupsNd;
	
	@Column(length = 9)
	private String groupsBgcolor;
	
	public static CalGroups createGroup(CalGroupsAddEdit_dto calGroupsAddDto, Users users) {
		CalGroups group = new CalGroups();
		group.setUserId(users);
		
		String groupsDivString = calGroupsAddDto.getGroupsDiv();
		if(groupsDivString.equals("ACCOUNTS")) group.setGroupsDiv(GroupsDiv.ACCOUNTS);
		if(groupsDivString.equals("SCHEDULES")) group.setGroupsDiv(GroupsDiv.SCHEDULES);
		if(groupsDivString.equals("MEMO")) group.setGroupsDiv(GroupsDiv.MEMO);
		
		group.setGroupsSt(calGroupsAddDto.getGroupsSt());
		String groupsNdName = calGroupsAddDto.getGroupsNd();
		if(groupsNdName.equals("NULL")) group.setGroupsNd("NONE");
		else group.setGroupsNd(groupsNdName);
		
		group.setGroupsBgcolor(calGroupsAddDto.getGroupsBgcolor());
		return group;
	}
	
	public CalGroups updateGroup (CalGroupsAddEdit_dto CalGroupsAddEditdto) {
		this.groupsBgcolor = CalGroupsAddEditdto.getGroupsBgcolor();
		this.groupsSt = CalGroupsAddEditdto.getGroupsSt();
		this.groupsNd = CalGroupsAddEditdto.getGroupsNd();
		return this;
	}
}
