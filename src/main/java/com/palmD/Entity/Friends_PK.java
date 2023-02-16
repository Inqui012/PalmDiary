package com.palmD.Entity;

import java.io.Serializable;

import lombok.*;

@Data
// 파라메터가 없는 생성자를 자동으로 작성해주는 lombok 어노테이션.
// 의존성 주입할때 쓰던 @RequiredArgsConstructor 와 비슷한 애들인것 같네.
// @RequiredArgsConstructor 는 final 값이나 @NonNull 설정된 필드값만 파라메터로 받는 생성자를 작성한대.
@NoArgsConstructor
// 해당 클래스에 있는 모든 필드값을 파라메터로 받는 생성자를 생성하는 어노테이션.
@AllArgsConstructor
public class Friends_PK implements Serializable{
	private static final long serialVersionUID = -2720292959593892551L;
//	여기 변수명은 엔티티의 변수명과 동일해야함.
	private String userId;
	private String friendId;	
}
