package com.palmD.Entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "postsLikes")
@IdClass(PostsLikes_PK.class)
public class PostsLikes extends BaseTime implements Serializable {
	
	private static final long serialVersionUID = 4975230023122675333L;

	@Id
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@Id
	@JoinColumn(name = "postId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Posts postId;
	
	public static PostsLikes createLike (Users user, Posts post) {
		PostsLikes like = new PostsLikes();
		like.setUserId(user);
		like.setPostId(post);
		return like;
	}
}
