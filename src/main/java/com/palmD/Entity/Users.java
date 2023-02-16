package com.palmD.Entity;

import javax.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.palmD.Constant.UsersRole;
import com.palmD.DTO.UsersRegist_dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class Users extends BaseTime{
	
	@Id
	private String usersId;
	
	@Enumerated(EnumType.STRING)
	private UsersRole usersRole;
	
	@Column(nullable = false)
	private String usersPw;
	
	@Column(nullable = false)
	private String usersEmail;
	
	private String usersNickname;
	private String usersInfo;	
	private String usersImg;
	private String usersImgUrl;
	
	public static Users registUser (UsersRegist_dto userRegistDto, PasswordEncoder encoder) {
		Users user = new Users();
		user.setUsersId(userRegistDto.getRegistId());
		user.setUsersPw(encoder.encode(userRegistDto.getRegistPw()));
		user.setUsersEmail(userRegistDto.getRegistEmail());
		user.setUsersRole(UsersRole.USER);
		user.setUsersNickname(userRegistDto.getRegistId());
		user.setUsersInfo("새로운 소개글을 입력하세요.");
		user.setUsersImg("default");
		user.setUsersImgUrl("/palmD/defaultUserImg/default.png");
		return user;
	}
	
	public void updateUser () {
		
	}
}
