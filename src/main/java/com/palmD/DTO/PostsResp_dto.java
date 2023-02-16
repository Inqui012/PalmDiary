package com.palmD.DTO;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.modelmapper.ModelMapper;

import com.palmD.Entity.Posts;
import com.palmD.Entity.Users;

import lombok.Data;

@Data
public class PostsResp_dto {
	private Long postId;
	private String postTitle;
	private String postDetail;
	private String regDatetime;
	
	private String usersImg;
	private String usersImgUrl;
	
	private Long postsLikesCount;
	private Long postsBookmarkCount;
	private Long postsCommentsCount;
	
	private List<PostsImges_dto> postsImgList = new ArrayList<>();
	private List<CommentsResp_dto> postsCommList = new ArrayList<>();
	
	private static ModelMapper mapper = new ModelMapper();
	public static PostsResp_dto mappedOf (Posts post, Users user) {
		PostsResp_dto resp = mapper.map(post, PostsResp_dto.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		resp.setRegDatetime(post.getRegDatetime().format(formatter));
		resp.setUsersImgUrl(user.getUsersImgUrl());
		resp.setUsersImg(user.getUsersImg());
		return resp;
	}
}
