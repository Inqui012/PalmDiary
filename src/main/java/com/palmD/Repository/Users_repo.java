package com.palmD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.palmD.Entity.Users;

public interface Users_repo extends JpaRepository<Users, String> {
}
