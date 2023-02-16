package com.palmD.Entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "postsImges")
public class PostsImges extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long imgId;
	
	@JoinColumn(name = "postId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Posts postId;
	
	@Column(nullable = false)
	private String imgName;

	@Column(nullable = false)
	private String imgOriName;
	
	@Column(nullable = false)
	private String imgUrl;
	
	public static PostsImges createPostsImges (String imgName, String imgOriName, String imgUrl) {
		PostsImges postsImges = new PostsImges();
		postsImges.setImgName(imgName);
		postsImges.setImgOriName(imgOriName);
		postsImges.setImgUrl(imgUrl);
		return postsImges;
	}
	
	public void updatePostsImges (String imgName, String imgOriName, String imgUrl) {
		this.imgName = imgName;
		this.imgOriName = imgOriName;
		this.imgUrl = imgUrl;
	}
}
