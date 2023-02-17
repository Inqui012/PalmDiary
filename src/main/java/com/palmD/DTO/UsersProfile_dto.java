package com.palmD.DTO;

import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import com.palmD.Entity.Users;
import lombok.Data;

@Data
public class UsersProfile_dto {
	private String usersId;
	private String usersEmail;
	private String usersNickname;
	private String usersInfo;	
	private String usersImg;
	private String usersImgUrl;
	private String regDatetime;
	
	private static ModelMapper mapper = new ModelMapper();
	public static UsersProfile_dto mappedOf (Users user) {
		UsersProfile_dto dto = mapper.map(user, UsersProfile_dto.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		dto.setRegDatetime(user.getRegDatetime().format(formatter));
		return dto;
	}
}
