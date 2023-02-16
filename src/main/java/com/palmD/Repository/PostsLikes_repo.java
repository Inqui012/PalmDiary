package com.palmD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.palmD.Entity.Posts;
import com.palmD.Entity.PostsLikes;
import com.palmD.Entity.PostsLikes_PK;

public interface PostsLikes_repo extends JpaRepository<PostsLikes, PostsLikes_PK>{
	
	@Query("SELECT COUNT(p.postId) FROM PostsLikes p WHERE p.postId =:post")
	int countLikes (@Param("post") Posts post);
	
	Long countByPostId (Posts post);
}
