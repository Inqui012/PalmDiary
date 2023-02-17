package com.palmD.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import com.palmD.DTO.CommentsResp_dto;
import com.palmD.DTO.PostsAddEdit_dto;
import com.palmD.DTO.PostsImges_dto;
import com.palmD.DTO.PostsResp_dto;
import com.palmD.Entity.Comments;
import com.palmD.Entity.Posts;
import com.palmD.Entity.PostsImges;
import com.palmD.Entity.Users;
import com.palmD.Repository.Comments_repo;
import com.palmD.Repository.PostsBookmarks_repo;
import com.palmD.Repository.PostsImges_repo;
import com.palmD.Repository.PostsLikes_repo;
import com.palmD.Repository.Posts_repo;
import com.palmD.Repository.Users_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Posts_serv {
	
	private final Users_repo usersRepo;
	private final Posts_repo postsRepo;
	private final PostsImges_repo postsImgesRepo;
	private final Comments_repo commentsRepo;
	private final PostsBookmarks_repo postsBookmarksRepo;
	private final PostsLikes_repo postsLikesRepo;
	
	public Posts addPost (PostsAddEdit_dto postsAddEditDto, String userId) {
		Users currentUser = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음"));
		Posts addPost = Posts.createPosts(postsAddEditDto, currentUser);
		return postsRepo.save(addPost);
	}
	
	public void editPost (PostsAddEdit_dto postsAddEditDto) {
		Posts editPost = postsRepo.findById(postsAddEditDto.getPostId()).orElseThrow(EntityNotFoundException::new);
		editPost.updatePosts(postsAddEditDto);
	}
	
	public void deletePost (Long postId) {
		Posts deletePost = postsRepo.findById(postId).orElseThrow(EntityNotFoundException::new);
		postsRepo.delete(deletePost);
	}
	
//	public List<PostsResp_dto> callAllPosts (String userId) {
//		Users requestedUser = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음"));
//		List<Posts> allPosts = postsRepo.findByUserIdOrderByRegDatetimeDesc(requestedUser);
//		if(allPosts.isEmpty()) throw new EntityNotFoundException("게시글이 존재하지 않습니다.");
//		List<PostsResp_dto> callAllPostsDto = new ArrayList<>();
//		for(Posts post : allPosts) {
//			PostsResp_dto respPost = PostsResp_dto.mappedOf(post, requestedUser);
//			List<PostsImges> allImges = postsImgesRepo.findByPostIdOrderByImgId(post);
//			for(PostsImges img : allImges) {
//				PostsImges_dto postImgesDto = PostsImges_dto.mappedOf(img);
//				respPost.getPostsImgList().add(postImgesDto);
//			}
//			List<Comments> allTopParentComments = commentsRepo.callAllTopParentComments(post);
//			for(Comments comm : allTopParentComments) {
//				CommentsResp_dto commentsRespDto = CommentsResp_dto.mappedOf(comm);
//				respPost.getPostsCommList().add(commentsRespDto);
//			}
//			respPost.setPostsLikesCount(postsLikesRepo.countByPostId(post));
//			respPost.setPostsBookmarkCount(postsBookmarksRepo.countByPostId(post));
//			respPost.setPostsCommentsCount(commentsRepo.countByPostId(post));
//			callAllPostsDto.add(respPost);
//		}
//		
//		return callAllPostsDto;
//	}
	
	public Page<PostsResp_dto> callAllPosts (String userId, Pageable pageable) {
		Users requestedUser = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음"));
		List<Posts> allPosts = postsRepo.findByUserIdOrderByRegDatetimeDesc(requestedUser, pageable);
		Long totalCount = postsRepo.countByUserId(requestedUser);
		if(allPosts.isEmpty()) throw new EntityNotFoundException("게시글이 존재하지 않습니다.");
		List<PostsResp_dto> callAllPostsDto = new ArrayList<>();
		for(Posts post : allPosts) {
			PostsResp_dto respPost = PostsResp_dto.mappedOf(post, requestedUser);
			List<PostsImges> allImges = postsImgesRepo.findByPostIdOrderByImgId(post);
			for(PostsImges img : allImges) {
				PostsImges_dto postImgesDto = PostsImges_dto.mappedOf(img);
				respPost.getPostsImgList().add(postImgesDto);
			}
			List<Comments> allTopParentComments = commentsRepo.callAllTopParentComments(post);
			for(Comments comm : allTopParentComments) {
				CommentsResp_dto commentsRespDto = CommentsResp_dto.convertFrom(comm);
				respPost.getPostsCommList().add(commentsRespDto);
			}
			respPost.setPostsLikesCount(postsLikesRepo.countByPostId(post));
			respPost.setPostsBookmarkCount(postsBookmarksRepo.countByPostId(post));
			respPost.setPostsCommentsCount(commentsRepo.countByPostId(post));
			callAllPostsDto.add(respPost);
		}
		return new PageImpl<>(callAllPostsDto, pageable, totalCount);
	}
	
	public List<String> callAllPostsDates (String userId) {
		Users requestedUser = usersRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없음"));
		List<Posts> allPosts = postsRepo.findByUserIdOrderByRegDatetimeDesc(requestedUser);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<String> allPostsDates = new ArrayList<>();
		for(Posts post : allPosts) {
			allPostsDates.add(post.getRegDatetime().format(formatter));
		}
		return allPostsDates;
	}
}
