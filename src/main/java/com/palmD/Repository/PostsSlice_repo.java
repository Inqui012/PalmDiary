package com.palmD.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.palmD.Entity.Posts;
import com.palmD.Entity.Users;

public interface PostsSlice_repo {
	List<Posts> slicedPageTest(Users userId, Pageable pageable);
}
