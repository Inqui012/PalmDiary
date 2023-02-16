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

}
