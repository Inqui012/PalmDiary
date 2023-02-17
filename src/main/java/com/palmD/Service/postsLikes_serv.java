package com.palmD.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.palmD.Entity.Posts;
import com.palmD.Entity.PostsLikes;
import com.palmD.Entity.PostsLikes_PK;
import com.palmD.Entity.Users;
import com.palmD.Repository.PostsLikes_repo;
import com.palmD.Repository.Posts_repo;
import com.palmD.Repository.Users_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class postsLikes_serv {
	private final PostsLikes_repo postsLikesRepo;
	private final Posts_repo postsRepo;
	private final Users_repo usersRepo;
	
	public PostsLikes addLikes (String userId, Long postId) {
		Users currentUser = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음"));
		Posts currentPost = postsRepo.findById(postId).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없음"));
		PostsLikes_PK pk = PostsLikes_PK.createPostsPk(currentUser, currentPost);
		if(postsLikesRepo.findById(pk).isPresent()) {
			throw new EntityExistsException("Already Exist");
		} else {
			PostsLikes addLike = PostsLikes.createLike(currentUser, currentPost);			
			return postsLikesRepo.save(addLike);
		}
	}
}
