package com.palmD.Entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "kanbanItem")
public class KanbanItem extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long kanbanItemId;
	
	@JoinColumn(name = "kanbanId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private KanbanBoard kanbanId;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@Column(nullable = false)
	private String kanbanItemName;
	private String kanbanItemDetail;
}
