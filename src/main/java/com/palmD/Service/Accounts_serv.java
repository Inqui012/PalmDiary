package com.palmD.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.palmD.Constant.AccStatus;
import com.palmD.DTO.AccountsAddEdit_dto;
import com.palmD.Entity.Accounts;
import com.palmD.Entity.CalGroups;
import com.palmD.Entity.Users;
import com.palmD.Repository.Accounts_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Accounts_serv {
	
	private final Accounts_repo accountsRepo;
	private final CalGroups_serv calGroupsServ;
	private final Users_serv usersServ;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	
	public Accounts addAccount (AccountsAddEdit_dto accountsAddEditDto, String userId) {
		Users currentUser = usersServ.findUser(userId);
		CalGroups selectedGroup = calGroupsServ.findCalGroup(accountsAddEditDto.getAccGroupId());
		Accounts addAcount = Accounts.createAccount(accountsAddEditDto, currentUser, selectedGroup, formatter);
		return accountsRepo.save(addAcount);
	}
	
	public void editAccount (AccountsAddEdit_dto accountsAddEditDto) {
		Accounts editAccount = findAccount(accountsAddEditDto.getAccId());
		CalGroups selectedGroup = calGroupsServ.findCalGroup(accountsAddEditDto.getAccGroupId());
		editAccount.updateAccount(accountsAddEditDto, selectedGroup, formatter);
	}
	
	public void deleteAccount (Long accId) {
		Accounts deleteAccount = findAccount(accId);
		accountsRepo.delete(deleteAccount);
	}
	
	public Accounts findAccount (Long accountId) {
		return accountsRepo.findById(accountId).orElseThrow(() -> new EntityNotFoundException("내역을 찾을 수 없습니다."));
	}
	
	public Map<String, List<Accounts>> callAllAccounts (String userId) {
		Users currentUser = usersServ.findUser(userId);
		List<Accounts> allIncomeAccounts = accountsRepo.findAllByAccStatus(currentUser, AccStatus.INCOME);
		List<Accounts> allExpendAccounts = accountsRepo.findAllByAccStatus(currentUser, AccStatus.EXPEND);
		List<Accounts> allSavingAccounts = accountsRepo.findAllByAccStatus(currentUser, AccStatus.SAVING);

		Map<String, List<Accounts>> allAccounts = new HashMap<>();
		allAccounts.put("allIncomeAccounts", allIncomeAccounts);
		allAccounts.put("allExpendAccounts", allExpendAccounts);
		allAccounts.put("allSavingAccounts", allSavingAccounts);
		
		return allAccounts;
	}
	
	public Map<String, List<Object[]>> callSumedAccount (String userId) {
		Users currentUser = usersServ.findUser(userId);
		List<Object[]> sumIncomeAccounts = accountsRepo.getSumByAccStatus(currentUser, AccStatus.INCOME);
		List<Object[]> sumExpendAccounts = accountsRepo.getSumByAccStatus(currentUser, AccStatus.EXPEND);
		List<Object[]> sumSavingAccounts = accountsRepo.getSumByAccStatus(currentUser, AccStatus.SAVING);
		Map<String, List<Object[]>> sumAllAccounts = new HashMap<>();
		sumAllAccounts.put("sumIncomeAccounts", sumIncomeAccounts);
		sumAllAccounts.put("sumExpendAccounts", sumExpendAccounts);
		sumAllAccounts.put("sumSavingAccounts", sumSavingAccounts);
		return sumAllAccounts;
	}
}
