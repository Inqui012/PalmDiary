package com.palmD.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.palmD.Entity.Posts;
import com.palmD.Entity.Users;

public interface Posts_repo extends JpaRepository<Posts, Long> {
	Long countByUserId (Users userId);
	List<Posts> findByUserIdOrderByRegDatetimeDesc(Users userId);
	List<Posts> findByUserIdOrderByRegDatetimeDesc(Users userId, Pageable pageable);
}
