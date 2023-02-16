package com.palmD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.palmD.Entity.Comments;
import com.palmD.Entity.Posts;

public interface Comments_repo extends JpaRepository<Comments, Long> {

	@Query("SELECT c FROM Comments c WHERE postId = :p AND parentCommId IS NULL ORDER BY commId")
	List<Comments> callAllTopParentComments(@Param("p") Posts postId);
	
	Long countByPostId(Posts postId);
}
