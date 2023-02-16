package com.palmD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.palmD.Entity.Schedules;
import com.palmD.Entity.Users;

public interface Schedules_repo extends JpaRepository<Schedules, Long> {
	List<Schedules> findByUserId(Users usersId);
}
