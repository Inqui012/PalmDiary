package com.palmD.DTO;

import org.modelmapper.ModelMapper;
import com.palmD.Entity.Users;
import lombok.Data;

@Data
public class UsersProfile_dto {
	private String usersId;
	private String usersRole;
	private String usersPw;
	private String usersEmail;
	private String usersNickname;
	private String usersInfo;	
	private String usersImg;
	private String usersImgUrl;
	
	private static ModelMapper mapper = new ModelMapper();
	public static UsersProfile_dto mappedOf (Users user) {
		return mapper.map(user, UsersProfile_dto.class);
	}
}
