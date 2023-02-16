package com.palmD.DTO;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.palmD.Entity.Comments;
import com.palmD.Entity.Users;

import lombok.Data;

@Data
public class CommentsResp_dto {
	private Long commId;
	private Users userId;
	private int commLevel;
	private Comments parentCommId;
	private List<Comments> childComms = new ArrayList<>();
	private String commDetail;
	
	private static ModelMapper mapper = new ModelMapper();
	
	public static CommentsResp_dto mappedOf (Comments comments) {
		return mapper.map(comments, CommentsResp_dto.class);
	}
}
