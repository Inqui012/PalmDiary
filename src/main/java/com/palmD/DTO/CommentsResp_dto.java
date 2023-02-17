package com.palmD.DTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.palmD.Entity.Comments;

import lombok.Data;

@Data
public class CommentsResp_dto {
	private Long commId;
	private String userId;
	private String usersImg;
	private String usersImgUrl;
	private int commLevel;
	private Long parentCommId;
	private List<CommentsResp_dto> childCommResp = new ArrayList<>();
	private String commDetail;
	private String regDatetime;
	
	public static CommentsResp_dto convertFrom (Comments comments) {
		CommentsResp_dto dto = new CommentsResp_dto();
		dto.setCommDetail(comments.getCommDetail());
		dto.setCommId(comments.getCommId());
		dto.setUserId(comments.getUserId().getUsersId());
		dto.setCommLevel(comments.getCommIndentLevel());
		dto.setUsersImg(comments.getUserId().getUsersImg());
		dto.setUsersImgUrl(comments.getUserId().getUsersImgUrl());
		if(comments.getParentCommId() != null) dto.setParentCommId(comments.getParentCommId().getCommId());
		if(!comments.getChildComms().isEmpty()) {
			for(Comments child : comments.getChildComms()) {
				CommentsResp_dto childDto = CommentsResp_dto.convertFrom(child);
				dto.getChildCommResp().add(childDto);
			}
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		dto.setRegDatetime(comments.getRegDatetime().format(formatter));
		
		return dto;
	}
}
