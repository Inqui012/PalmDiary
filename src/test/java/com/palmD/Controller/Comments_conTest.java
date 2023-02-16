package com.palmD.Controller;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.palmD.DTO.CommentsAddEdit_dto;
import com.palmD.DTO.PostsAddEdit_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.Comments;
import com.palmD.Entity.Posts;
import com.palmD.Entity.Users;
import com.palmD.Service.CalGroups_serv;
import com.palmD.Service.Comments_serv;
import com.palmD.Service.Posts_serv;
import com.palmD.Service.Users_serv;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class Comments_conTest {

	@Autowired
	private Users_serv usersServ;
	@Autowired
	private Posts_serv postsServ;
	@Autowired
	private Comments_serv commentsServ;
	
	public Users register(String id, String pw, String email) {
		UsersRegist_dto usersRegistDto = new UsersRegist_dto();
		usersRegistDto.setRegistId(id);
		usersRegistDto.setRegistPw(pw);
		usersRegistDto.setRegistEmail(email);
		return usersServ.registerUser(usersRegistDto);
	}
	
	public Posts post() {
		Users user = register("test", "1234", "aaaaa@aaaa.aaa");
		PostsAddEdit_dto dto = new PostsAddEdit_dto();
		dto.setPostDetail("post detail");
		dto.setPostTitle("post title");
		return postsServ.addPost(dto, user.getUsersId());
	}

	@Test
	@DisplayName("commentParentAddTest")
	public void commAddTest () {
		Posts post = post();
		Users comUser = register("test1", "123", "aaa@aaaa.ccc");
		CommentsAddEdit_dto dto = new CommentsAddEdit_dto();
		dto.setCommDetail("comment");
		dto.setPostId(post.getPostId());
		dto.setParentCommId((long) -1);
		
		commentsServ.addComment(dto, comUser.getUsersId());
	}
	
	@Test
	@DisplayName("commentChildAddTest")
	public void commAddTest2 () {
		Posts post = post();
		Users comUser = register("test1", "123", "aaa@aaaa.ccc");
		CommentsAddEdit_dto dto = new CommentsAddEdit_dto();
		dto.setCommDetail("comment2");
		dto.setPostId(post.getPostId());
		dto.setParentCommId((long) -1);
		
		Comments parent = commentsServ.addComment(dto, comUser.getUsersId());
		
		CommentsAddEdit_dto dto2 = new CommentsAddEdit_dto();
		Users comUser2 = register("test2", "123", "aaa@aaaa.ccc");
		dto2.setCommDetail("comm2");
		dto2.setParentCommId(parent.getCommId());
		dto2.setPostId(post.getPostId());
		
		Comments child1 = commentsServ.addComment(dto2, comUser2.getUsersId());
		
		CommentsAddEdit_dto dto3 = new CommentsAddEdit_dto();
		Users comUser3 = register("test3", "123", "aaa@aaaa.ccc");
		dto3.setCommDetail("comm3");
		dto3.setParentCommId(child1.getCommId());
		dto3.setPostId(post.getPostId());
		
		Comments child2 = commentsServ.addComment(dto3, comUser3.getUsersId());
		System.err.println(child1.getChildComms().size());
		System.err.println(parent.getChildComms().get(0).getCommId());
	}
	
	@Test
	@DisplayName("commentChildDeleteTest")
	public void commAddTest3 () {
		Posts post = post();
		Users comUser = register("test1", "123", "aaa@aaaa.ccc");
		CommentsAddEdit_dto dto = new CommentsAddEdit_dto();
		dto.setCommDetail("comment2");
		dto.setPostId(post.getPostId());
		dto.setParentCommId((long) -1);
		
		Comments parent = commentsServ.addComment(dto, comUser.getUsersId());
		
		CommentsAddEdit_dto dto2 = new CommentsAddEdit_dto();
		Users comUser2 = register("test2", "123", "aaa@aaaa.ccc");
		dto2.setCommDetail("comm2");
		dto2.setParentCommId(parent.getCommId());
		dto2.setPostId(post.getPostId());
		
		Comments child1 = commentsServ.addComment(dto2, comUser2.getUsersId());
		System.err.println("before delete : " + parent.getChildComms().size());
		commentsServ.deleteComment(child1.getCommId());
		System.err.println("after delete : " + parent.getChildComms().size());
	}

	@Test
	@DisplayName("commentParentDeleteTest")
	public void commAddTest4 () {
		Posts post = post();
		Users comUser = register("test1", "123", "aaa@aaaa.ccc");
		CommentsAddEdit_dto dto = new CommentsAddEdit_dto();
		dto.setCommDetail("comment2");
		dto.setPostId(post.getPostId());
		dto.setParentCommId((long) -1);
		
		Comments parent = commentsServ.addComment(dto, comUser.getUsersId());
		
		CommentsAddEdit_dto dto2 = new CommentsAddEdit_dto();
		Users comUser2 = register("test2", "123", "aaa@aaaa.ccc");
		dto2.setCommDetail("comm2");
		dto2.setParentCommId(parent.getCommId());
		dto2.setPostId(post.getPostId());
		
		Comments child1 = commentsServ.addComment(dto2, comUser2.getUsersId());
		
		commentsServ.deleteComment(parent.getCommId());
		System.err.println(parent.getCommDetail());
	}
}
