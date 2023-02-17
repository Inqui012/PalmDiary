package com.palmD.Entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "bookmarks")
@IdClass(PostsBookmarks_PK.class)
public class PostsBookmarks extends BaseTime implements Serializable {

	private static final long serialVersionUID = 6434012080261184420L;
	
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

	public static PostsBookmarks createBookmark (Users user, Posts post) {
		PostsBookmarks bookmark = new PostsBookmarks();
		bookmark.setUserId(user);
		bookmark.setPostId(post);
		return bookmark;
	}
}
