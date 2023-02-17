package com.palmD.Service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.palmD.Entity.Posts;
import com.palmD.Entity.PostsBookmarks;
import com.palmD.Entity.PostsBookmarks_PK;
import com.palmD.Entity.Users;
import com.palmD.Repository.PostsBookmarks_repo;
import com.palmD.Repository.Posts_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostsBookmarks_serv {
	private final PostsBookmarks_repo postsBookmarksRepo;
	private final Posts_repo postsRepo;
	private final Users_serv usersServ;
	
	public PostsBookmarks toggleBookmarks(String userId, Long postId) {
		Users currentUser = usersServ.findUser(userId);
		Posts currentPost = postsRepo.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글이 존해하지 않습니다."));
		PostsBookmarks_PK pk = PostsBookmarks_PK.createBookmarksPk(currentUser.getUsersId(), currentPost.getPostId());
		PostsBookmarks existsBookmark = postsBookmarksRepo.findById(pk).orElse(null);
		if(existsBookmark != null) {
			postsBookmarksRepo.delete(existsBookmark);
			return null;
		} else {
			PostsBookmarks addBookmark = PostsBookmarks.createBookmark(currentUser, currentPost);
			return postsBookmarksRepo.save(addBookmark);
		}
	}
	
	public boolean checkBookmarks (Users user, Posts post) {
		PostsBookmarks_PK pk = PostsBookmarks_PK.createBookmarksPk(user.getUsersId(), post.getPostId());
		return postsBookmarksRepo.findById(pk).isPresent();
	}
	
	public Long countBookmarksByPost (Posts post) {
		return postsBookmarksRepo.countByPostId(post);
	}
}
