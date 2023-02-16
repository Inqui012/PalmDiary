package com.palmD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.palmD.Entity.Posts;
import com.palmD.Entity.PostsBookmarks;
import com.palmD.Entity.PostsBookmarks_PK;

public interface PostsBookmarks_repo extends JpaRepository<PostsBookmarks, PostsBookmarks_PK> {
	
	@Query("SELECT COUNT(p.postId) FROM PostsBookmarks p WHERE p.postId =:post")
	int countBookmarks(@Param("post") Posts post);
	
	Long countByPostId (Posts post);
}
