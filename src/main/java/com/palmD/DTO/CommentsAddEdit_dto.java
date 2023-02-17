package com.palmD.DTO;

import lombok.Data;

@Data
public class CommentsAddEdit_dto {
	private Long commId;
	private Long postId;
	private Long parentCommId;
	private String commDetail;	
}
