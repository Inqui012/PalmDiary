package com.palmD.DTO;

import org.springframework.http.HttpStatus;

import lombok.*;

@Data
public class UsersLoginResp_dto {
	private HttpStatus code;
	private String status;
	private String message;
}
