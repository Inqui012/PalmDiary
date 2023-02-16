package com.palmD.Entity;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.palmD.DTO.CommentsAddEdit_dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "comments")
public class Comments extends BaseTime{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commId;
	
	@JoinColumn(name = "postId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Posts postId;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
//	joincolumn 의 name 값은 테이블에 무슨 이름으로 저장할지를 지정하는거고
//	어떤 컬럼을 fk 로 가져올건지는 referencedColumnName 로 설정함. 기본으로는 해당 클래스의 id 값을 가져오긴 하는데
//	이렇게 자기 자신 컬럼을 가져와서 에러날때는 이렇게 하는듯?
	@JoinColumn(name = "parentCommId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Comments parentCommId;
//	private int commRef;
//	private int commRefStep;
	private int commIndentLevel;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCommId", orphanRemoval = true)
	private List<Comments> childComms = new ArrayList<>();
	
	private String commDetail;
	
	public static Comments createComment (String commDetail, Posts post, Users user, Comments parentComment) {
		Comments comment = new Comments();
		comment.setCommDetail(commDetail);
		comment.setPostId(post);
		comment.setUserId(user);
		if(parentComment != null) {
			comment.setParentCommId(parentComment);
			comment.setCommIndentLevel(parentComment.getCommIndentLevel() + 1);		
		} else {
			comment.setCommIndentLevel(0);
		}
		return comment;
	}
	
	public Comments appendChild (Comments child) {
		this.childComms.add(child);
		return child;
	}
	
	public void updateComment (String commDetail) {
		this.commDetail = commDetail;
	}
}
