package com.palmD.Entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@Getter
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public class BaseTime {
	@Column(updatable = false)
	@CreatedDate
	private LocalDateTime regDatetime;
	
	@LastModifiedDate
	private LocalDateTime updateDatetime;
}
