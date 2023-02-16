package com.palmD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.palmD.Entity.Posts;
import com.palmD.Entity.PostsImges;

public interface PostsImges_repo extends JpaRepository<PostsImges, Long> {
	List<PostsImges> findByPostIdOrderByImgId(Posts postId);
}
