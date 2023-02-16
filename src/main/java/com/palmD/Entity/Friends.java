package com.palmD.Entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Data
@Entity
@Table(name = "friends")
// 복합키를 사용할때는 따로 복합키를 지정하는 클래스를 만들어서 불러와야 함.
@IdClass(Friends_PK.class)
// 복합키 사용을 위해 Serializable 를 상속하는데... 이건 뭘까
// JVM 에서 외부로 데이터를 보내고 받을 수 있도록 객체를 바이트화 시키거나 하는 인터페이스라는데
// 영속성 컨텍스트는 엔티티의 PK를 이용해서 엔티티를 관리, 근데 복합키를 쓰면 PK가 다른 클래스가 되니까... 
// 다른클래스에서 PK 값을 불러오고, 그걸로 여기서 데이터를 찾는....과정이 추가되는.........
// 이 과정에서 이런 직렬화가 필요하다는것 같은데.........
public class Friends implements Serializable{
//	객체를 직렬화 하게 되면 필요한 필드. 값은 지정해주는게 좋다는것 같은데 이 값으로 뭘 하는지는....
	private static final long serialVersionUID = 5027375811805575024L;

	@Id
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@Id
	@JoinColumn(name = "friendId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users friendId;
}
