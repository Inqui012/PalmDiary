package com.palmD.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.palmD.DTO.PostsAddEdit_dto;
import com.palmD.DTO.PostsResp_dto;
import com.palmD.Entity.Posts;
import com.palmD.Entity.Users;
import com.palmD.Repository.Posts_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Posts_serv {
	
	private final Users_serv usersServ;
	private final Posts_repo postsRepo;
	private final PostsImges_serv postsImgesServ;
	private final Comments_serv commentsServ;
	private final PostsBookmarks_serv postsBookmarksServ;
	private final postsLikes_serv postsLikesServ;
	
	public Posts addPost (PostsAddEdit_dto postsAddEditDto, String userId) {
		Users currentUser = usersServ.findUser(userId);
		Posts addPost = Posts.createPosts(postsAddEditDto, currentUser);
		return postsRepo.save(addPost);
	}
	
	public void editPost (PostsAddEdit_dto postsAddEditDto) {
		Posts editPost = findPostsById(postsAddEditDto.getPostId());
		editPost.updatePosts(postsAddEditDto);
	}
	
	public void deletePost (Long postId) {
		Posts deletePost = postsRepo.findById(postId).orElseThrow(EntityNotFoundException::new);
		postsRepo.delete(deletePost);
	}
		
	public Page<PostsResp_dto> callAllPosts (String userId, Pageable pageable, String viewUserId) {
		Users requestedUser = usersServ.findUser(userId);
		List<Posts> allPosts = findPostsByUser(requestedUser, pageable);
		Long totalCount = postsRepo.countByUserId(requestedUser);
		List<PostsResp_dto> callAllPostsDto = new ArrayList<>();
		for(Posts post : allPosts) {
			PostsResp_dto respPost = PostsResp_dto.mappedOf(post, requestedUser);
			respPost.setPostsImgList(postsImgesServ.findPostImgConvert(post));
			respPost.setPostsCommList(commentsServ.findCommentConvert(post));
			respPost.setPostsLikesCount(postsLikesServ.countLikesByPost(post));
			
			respPost.setPostsBookmarkCount(postsBookmarksServ.countBookmarksByPost(post));
			respPost.setPostsCommentsCount(commentsServ.countCommentsByPost(post));

			if(viewUserId != null) {
				Users viewUser = usersServ.findUser(viewUserId);
				respPost.setPostsLike(postsLikesServ.checkLikes(viewUser, post));	
				respPost.setPostsBookmark(postsBookmarksServ.checkBookmarks(viewUser, post));
			}
			
			callAllPostsDto.add(respPost);
		}
		return new PageImpl<>(callAllPostsDto, pageable, totalCount);
	}
	
	public List<Posts> findPostsByUser (Users requestedUser, Pageable pageable) {
		List<Posts> foundPostList = postsRepo.findByUserIdOrderByRegDatetimeDesc(requestedUser, pageable);
		if(foundPostList.isEmpty()) throw new EntityNotFoundException("해당 유저의 게시글이 존재하지 않습니다.");
		return foundPostList;
	}
	
	public Posts findPostsById (Long postId) {
		return postsRepo.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글이 존해하지 않습니다."));
	}
	
	public List<String> callAllPostsDates (String userId) {
		Users requestedUser = usersServ.findUser(userId);
		List<Posts> allPosts = postsRepo.findByUserIdOrderByRegDatetimeDesc(requestedUser);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<String> allPostsDates = new ArrayList<>();
		for(Posts post : allPosts) {
			allPostsDates.add(post.getRegDatetime().format(formatter));
		}
		return allPostsDates;
	}
}
