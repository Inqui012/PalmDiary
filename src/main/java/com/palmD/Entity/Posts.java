package com.palmD.Entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.palmD.DTO.PostsAddEdit_dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "posts")
public class Posts extends BaseTime{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long postId;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@Column(nullable = false)
	private String postTitle;

	@Column(nullable = false)
	@Lob
	private String postDetail;
	
	public static Posts createPosts (PostsAddEdit_dto postsAddEditDto, Users user) {
		Posts post = new Posts();
		post.setUserId(user);
		post.setPostTitle(postsAddEditDto.getPostTitle());
		post.setPostDetail(postsAddEditDto.getPostDetail());
		return post;
	}
	
	public void updatePosts (PostsAddEdit_dto postsAddEditDto) {
		this.postTitle = postsAddEditDto.getPostTitle();
		this.postDetail = postsAddEditDto.getPostDetail();
	}
}
