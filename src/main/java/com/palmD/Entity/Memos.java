package com.palmD.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.palmD.DTO.MemosAddEdit_dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "memos")
public class Memos extends BaseTime{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long memoId;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@JoinColumn(name = "groupId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private CalGroups groupId;
	
	@Column(nullable = false)
	private String memoDetail;

	@Column(nullable = false)
	private LocalDateTime memoDate;
	
	public static Memos createMemo (MemosAddEdit_dto memosAddEditDto, Users user, CalGroups group, DateTimeFormatter formatter) {
		Memos memos = new Memos();
		memos.setGroupId(group);
		memos.setUserId(user);
		memos.setMemoDate(LocalDateTime.parse(memosAddEditDto.getMemoDate(), formatter));
		memos.setMemoDetail(memosAddEditDto.getMemoDetail());
		return memos;
	}
	
	public Memos updateMemo (MemosAddEdit_dto memosAddEditDto, CalGroups group, DateTimeFormatter formatter) {
		this.memoDate = LocalDateTime.parse(memosAddEditDto.getMemoDate(), formatter);
		this.memoDetail = memosAddEditDto.getMemoDetail();
		this.groupId = group;
		return this;
	}
}
