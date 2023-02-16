package com.palmD.Entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Data
@Entity
@Table(name = "kanbanBoard")
public class KanbanBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long kanbanId;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@Column(nullable = false)
	private String kanbanName;
}
