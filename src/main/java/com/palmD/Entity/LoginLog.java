package com.palmD.Entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Data
@Entity
@Table(name = "loginLog")
public class LoginLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long loginLogId;
	
	private LocalDateTime loginDatetime;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	private String loginIp;
	
	public static LoginLog logging (String ip, Users user) {
		LoginLog log = new LoginLog();
		log.setUserId(user);
		log.setLoginIp(ip);
		log.setLoginDatetime(LocalDateTime.now());
		return log;
	}
}
