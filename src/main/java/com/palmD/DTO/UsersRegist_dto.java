package com.palmD.DTO;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import lombok.*;

@Data
public class UsersRegist_dto {
	
	@NotBlank(message = "아이디를 입력해 주세요.")
	private String registId;
	
	@NotEmpty(message = "비밀번호를 입력해 주세요.")
	@Length(min = 8, max = 20, message = "비밀번호는 최소 8자, 최대 20자 입니다.")
	private String registPw;
	
	@Email(message = "이메일 형식이 아닙니다.")
	@NotEmpty(message = "이메일을 입력해 주세요.")
	private String registEmail;
	
}
