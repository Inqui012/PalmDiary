package com.palmD.Service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.palmD.DTO.CommentsAddEdit_dto;
import com.palmD.Entity.Comments;
import com.palmD.Entity.Posts;
import com.palmD.Entity.Users;
import com.palmD.Repository.Comments_repo;
import com.palmD.Repository.Posts_repo;
import com.palmD.Repository.Users_repo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Comments_serv {
	private final Users_repo usersRepo;
	private final Comments_repo commentsRepo;
	private final Posts_repo postsRepo;
	
	public Comments addComment (CommentsAddEdit_dto commentsAddEditDto, String userId) {
		Posts parentPost = postsRepo.findById(commentsAddEditDto.getPostId()).orElseThrow(EntityNotFoundException::new);
		Users currentUser = usersRepo.findById(userId).orElseThrow(EntityNotFoundException::new);
		Comments addComment = Comments.createComment(commentsAddEditDto.getCommDetail(), parentPost, currentUser, null);			
		if(commentsAddEditDto.getParentCommId() != -1) {
			Comments parentComment = commentsRepo.findById(commentsAddEditDto.getParentCommId()).orElseThrow(EntityNotFoundException::new);			
			addComment = Comments.createComment(commentsAddEditDto.getCommDetail(), parentPost, currentUser, parentComment);
			Comments childComment = commentsRepo.save(addComment);
			return parentComment.appendChild(childComment); 
		} else {			
			return commentsRepo.save(addComment);
		}
	}
	
	public void editComment (CommentsAddEdit_dto commentsAddEditDto) {
		Comments editComment = commentsRepo.findById(commentsAddEditDto.getCommId()).orElseThrow(EntityNotFoundException::new);
		editComment.updateComment(commentsAddEditDto.getCommDetail());
	}
	
	public void deleteComment (Long commentId) {
		Comments deleteComment = commentsRepo.findById(commentId).orElseThrow(EntityNotFoundException::new);		
		List<Comments> childList = deleteComment.getChildComms();
		if(childList.size() == 0 || childList == null) {
			if(deleteComment.getParentCommId() != null) {
				Comments parentComment = commentsRepo.findById(deleteComment.getParentCommId().getCommId()).orElseThrow(EntityNotFoundException::new);
				List<Comments> parentsChildList = parentComment.getChildComms();
				int deleteIdx = -1;
				for(Comments comm : parentsChildList) {
					if(comm.getCommId() == deleteComment.getCommId()) {
						deleteIdx = parentsChildList.indexOf(comm);
					}
				}
				parentsChildList.remove(deleteIdx);
			}
			commentsRepo.delete(deleteComment);
		} else {
			deleteComment.updateComment("삭제된 댓글 입니다.");
		}
	}
	
	public List<Comments> callAllComments (Posts postId) {
		return commentsRepo.callAllTopParentComments(postId);
	}
}
