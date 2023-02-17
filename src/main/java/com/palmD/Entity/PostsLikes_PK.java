package com.palmD.Entity;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsLikes_PK implements Serializable{

	private static final long serialVersionUID = -6560869169572866004L;
	private String userId;
	private Long postId;

	public static PostsLikes_PK createPostsPk (String userId, Long postId) {
		PostsLikes_PK pk = new PostsLikes_PK();
		pk.setUserId(userId);
		pk.setPostId(postId);
		return pk;		
	}
}
