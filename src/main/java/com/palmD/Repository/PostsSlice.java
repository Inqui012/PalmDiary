package com.palmD.Repository;

import java.util.List;
import org.springframework.data.domain.Pageable;

import com.palmD.Entity.Posts;
import com.palmD.Entity.Users;

public class PostsSlice implements PostsSlice_repo {

	@Override
	public List<Posts> slicedPageTest(Users userId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
