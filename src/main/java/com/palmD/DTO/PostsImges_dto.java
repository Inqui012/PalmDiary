package com.palmD.DTO;

import org.modelmapper.ModelMapper;

import com.palmD.Entity.PostsImges;

import lombok.Data;

@Data
public class PostsImges_dto {
	private Long imgId;
	private String imgOriName;
	private String imgUrl;
	
	private static ModelMapper mapper = new ModelMapper();
	public static PostsImges_dto convertFrom(PostsImges postsImges) {
		return mapper.map(postsImges, PostsImges_dto.class);
	}
}
