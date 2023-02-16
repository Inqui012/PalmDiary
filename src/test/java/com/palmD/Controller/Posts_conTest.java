package com.palmD.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.palmD.DTO.CommentsAddEdit_dto;
import com.palmD.DTO.PostsAddEdit_dto;
import com.palmD.DTO.PostsResp_dto;
import com.palmD.DTO.UsersRegist_dto;
import com.palmD.Entity.Comments;
import com.palmD.Entity.Posts;
import com.palmD.Entity.Users;
import com.palmD.Repository.Comments_repo;
import com.palmD.Service.CalGroups_serv;
import com.palmD.Service.Comments_serv;
import com.palmD.Service.Posts_serv;
import com.palmD.Service.Users_serv;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class Posts_conTest {

	@Autowired
	private Users_serv usersServ;
	@Autowired
	private Posts_serv postsServ;
	@Autowired
	private Comments_serv commentsServ;
	@Autowired
	private Comments_repo commentsRepo;
	
	public Users register(String id, String pw, String email) {
		UsersRegist_dto usersRegistDto = new UsersRegist_dto();
		usersRegistDto.setRegistId(id);
		usersRegistDto.setRegistPw(pw);
		usersRegistDto.setRegistEmail(email);
		return usersServ.registerUser(usersRegistDto);
	}

	@Test
	@DisplayName("postAddTest")
	public void postAdd () {
		Users testUser = register("test", "1234", "aaaa@aaaaa");
		PostsAddEdit_dto dto = new PostsAddEdit_dto();
		dto.setPostDetail("detail");
		dto.setPostTitle("title");
		postsServ.addPost(dto, testUser.getUsersId());
	}
	
	@Test
	@DisplayName("postEditTest")
	public void postEdit () {
		Users testUser = register("test", "1234", "aaaa@aaaaa");
		PostsAddEdit_dto dto = new PostsAddEdit_dto();
		dto.setPostDetail("detail");
		dto.setPostTitle("title");
		Posts post = postsServ.addPost(dto, testUser.getUsersId());
		
		PostsAddEdit_dto dto1 = new PostsAddEdit_dto();
		dto1.setPostDetail("detail11");
		dto1.setPostTitle("title111");
		dto1.setPostId(post.getPostId());
		
		postsServ.editPost(dto1);
	}
	
	@Test
	@DisplayName("postDeleteTest")
	public void postDelte () {
		Users testUser = register("test", "1234", "aaaa@aaaaa");
		PostsAddEdit_dto dto = new PostsAddEdit_dto();
		dto.setPostDetail("detail");
		dto.setPostTitle("title");
		Posts post = postsServ.addPost(dto, testUser.getUsersId());
		
		postsServ.deletePost(post.getPostId());
	}
	
	@Test
	@DisplayName("postCallTest")
	public void postcall () {
		Users testUser = register("test", "1234", "aaaa@aaaaa");
		for (int i = 0; i < 10; i ++) {
			PostsAddEdit_dto dto = new PostsAddEdit_dto();
			dto.setPostDetail("detail" + i);
			dto.setPostTitle("title" + i);
			Posts post = postsServ.addPost(dto, testUser.getUsersId());			
			for (int j = 0; i < 3; i ++) {
				Users comUser = register("test" + j, "123", "aaa@aaaa.ccc");
				CommentsAddEdit_dto dtoCom = new CommentsAddEdit_dto();
				dtoCom.setCommDetail("comment" + j);
				dtoCom.setPostId(post.getPostId());
				dtoCom.setParentCommId((long) -1);
				
				Comments parent = commentsServ.addComment(dtoCom, comUser.getUsersId());
				
				Users comUser1 = register("test" + j*2, "123", "aaa@aaaa.ccc");
				CommentsAddEdit_dto dtoCom1 = new CommentsAddEdit_dto();
				dtoCom1.setCommDetail("comment" + j*2);
				dtoCom1.setPostId(post.getPostId());
				dtoCom1.setParentCommId(parent.getCommId());
				commentsServ.addComment(dtoCom1, comUser1.getUsersId());
			}
		}
		
		List<PostsResp_dto> called = postsServ.callAllPosts("test");
//		System.err.println(called.get(0).getPostsBookmarkCount());
//		System.err.println(called.get(0).getPostsLikesCount());
//		System.err.println(called.get(0).getPostsCommList().get(0).getCommDetail());
//		System.err.println(called.get(0).getPostsCommList().get(0).getChildComms().size());
	}
	
	@Test
	@DisplayName("postHasCommDeleteTest")
	public void postHasCommDeleteTest () {
		Users testUser = register("test", "1234", "aaaa@aaaaa");
		Long postNum = -1L;
		PostsAddEdit_dto dto = new PostsAddEdit_dto();
		dto.setPostDetail("detail");
		dto.setPostTitle("title");
		Posts post = postsServ.addPost(dto, testUser.getUsersId());	
		Comments tesing = null;
		for (int j = 0; j < 3; j ++) {
			Users comUser = register("test" + j, "123", "aaa@aaaa.ccc");
			CommentsAddEdit_dto dtoCom = new CommentsAddEdit_dto();
			dtoCom.setCommDetail("comment" + j);
			dtoCom.setPostId(post.getPostId());
			dtoCom.setParentCommId((long) -1);
			
			Comments parent = commentsServ.addComment(dtoCom, comUser.getUsersId());
			tesing = parent;
			
			Users comUser1 = register("test" + j*2, "123", "aaa@aaaa.ccc");
			CommentsAddEdit_dto dtoCom1 = new CommentsAddEdit_dto();
			dtoCom1.setCommDetail("comment" + j*2);
			dtoCom1.setPostId(post.getPostId());
			dtoCom1.setParentCommId(parent.getCommId());
			commentsServ.addComment(dtoCom1, comUser1.getUsersId());
		}
		postNum = post.getPostId();
		
		postsServ.deletePost(postNum);
		
		System.err.println(tesing.getCommDetail());
//		System.err.println(called.get(0).getPostsBookmarkCount());
//		System.err.println(called.get(0).getPostsLikesCount());
//		System.err.println(called.get(0).getPostsCommList().get(0).getCommDetail());
//		System.err.println(called.get(0).getPostsCommList().get(0).getChildComms().size());
	}
}
