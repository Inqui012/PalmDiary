package com.palmD.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.palmD.Constant.AccStatus;
import com.palmD.Entity.Accounts;
import com.palmD.Entity.Users;

public interface Accounts_repo extends JpaRepository<Accounts, Long> {
	@Query("SELECT a.accDate, SUM(a.accMoney) FROM Accounts a WHERE a.userId = :u AND a.accStatus = :accS GROUP BY a.accDate ORDER BY a.accDate")
	List<Object[]> getSumByAccStatus (@Param("u") Users user, @Param("accS") AccStatus accStatus);
	
	@Query("SELECT a FROM Accounts a WHERE a.userId = :u AND a.accStatus = :accS")
	List<Accounts> findAllByAccStatus (@Param("u") Users user, @Param("accS") AccStatus accStatus);
}
