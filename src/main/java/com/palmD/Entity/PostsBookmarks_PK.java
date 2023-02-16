package com.palmD.Entity;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsBookmarks_PK implements Serializable {

	private static final long serialVersionUID = 8595479418369518500L;
	
	private String userId;
	private Long postId;
}
