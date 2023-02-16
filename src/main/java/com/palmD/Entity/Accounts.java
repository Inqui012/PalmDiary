package com.palmD.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.thymeleaf.util.StringUtils;

import com.palmD.Constant.AccStatus;
import com.palmD.DTO.AccountsAddEdit_dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "accounts")
public class Accounts extends BaseTime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accId;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@JoinColumn(name = "groupId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private CalGroups groupId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccStatus accStatus;
	
	@Column(nullable = false)
	private LocalDateTime accDate;

	@Column(nullable = false)
	private String accName;
	private String accDetail;
	
	@Column(nullable = false)
	private int accMoney;
	
	public static Accounts createAccount (AccountsAddEdit_dto accountsAddEditDto, Users user, CalGroups group, DateTimeFormatter formatter) {
		Accounts account = new Accounts();
		account.setAccName(accountsAddEditDto.getAccName());
		account.setAccDetail(accountsAddEditDto.getAccDetail());
		account.setAccDate(LocalDateTime.parse(accountsAddEditDto.getAccDate(), formatter));
		account.setAccMoney(accountsAddEditDto.getAccMoney());
		account.setGroupId(group);
		account.setUserId(user);
		
		if(StringUtils.equals(accountsAddEditDto.getAccStatus(), "INCOME")) account.setAccStatus(AccStatus.INCOME);
		if(StringUtils.equals(accountsAddEditDto.getAccStatus(), "EXPEND")) account.setAccStatus(AccStatus.EXPEND);
		if(StringUtils.equals(accountsAddEditDto.getAccStatus(), "SAVING")) account.setAccStatus(AccStatus.SAVING);
		
		return account;	
	}
	
	public Accounts updateAccount (AccountsAddEdit_dto accountsAddEditDto, CalGroups group, DateTimeFormatter formatter) {
		this.accName = accountsAddEditDto.getAccName();
		this.accDate = LocalDateTime.parse(accountsAddEditDto.getAccDate(), formatter);
		this.accDetail = accountsAddEditDto.getAccDetail();
		this.accMoney = accountsAddEditDto.getAccMoney();
		this.groupId = group;
		
		if(StringUtils.equals(accountsAddEditDto.getAccStatus(), "INCOME")) this.accStatus = AccStatus.INCOME;
		if(StringUtils.equals(accountsAddEditDto.getAccStatus(), "EXPEND")) this.accStatus = AccStatus.EXPEND;
		if(StringUtils.equals(accountsAddEditDto.getAccStatus(), "SAVING")) this.accStatus = AccStatus.SAVING;
		
		return this;
	}
}
