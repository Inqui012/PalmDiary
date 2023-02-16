package com.palmD.DTO;

import lombok.*;

@Data
public class UsersLogin_dto {
	private String loginId;
	private String loginPw;
	private boolean isRemember;
}
