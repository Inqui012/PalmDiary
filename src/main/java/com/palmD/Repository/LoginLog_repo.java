package com.palmD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.palmD.Entity.LoginLog;

public interface LoginLog_repo extends JpaRepository<LoginLog, Long> {

}
