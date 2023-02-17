package com.palmD.Service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.palmD.Entity.Posts;
import com.palmD.Entity.PostsLikes;
import com.palmD.Entity.PostsLikes_PK;
import com.palmD.Entity.Users;
import com.palmD.Repository.PostsLikes_repo;
import com.palmD.Repository.Posts_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class postsLikes_serv {
	private final PostsLikes_repo postsLikesRepo;
	private final Posts_repo postsRepo;
	private final Users_serv usersServ;
	
	public PostsLikes toggleLikes (String userId, Long postId) {
		Users currentUser = usersServ.findUser(userId);
		Posts currentPost = postsRepo.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글이 존해하지 않습니다."));
		PostsLikes_PK pk = PostsLikes_PK.createPostsPk(currentUser.getUsersId(), currentPost.getPostId());
		PostsLikes existsLike = postsLikesRepo.findById(pk).orElse(null);
		if(existsLike != null) {
			postsLikesRepo.delete(existsLike);
			return null;
		} else {
			PostsLikes addLike = PostsLikes.createLike(currentUser, currentPost);			
			return postsLikesRepo.save(addLike);
		}
	}
	
	public boolean checkLikes (Users user, Posts post) {
		PostsLikes_PK pk = PostsLikes_PK.createPostsPk(user.getUsersId(), post.getPostId());
		return postsLikesRepo.findById(pk).isPresent();
	}
	
	public Long countLikesByPost (Posts post) {
		return postsLikesRepo.countByPostId(post);
	}
}
