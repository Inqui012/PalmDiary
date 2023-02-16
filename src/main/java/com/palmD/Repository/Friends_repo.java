package com.palmD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.palmD.Entity.Friends;
import com.palmD.Entity.Friends_PK;

// JpaRepository 를 상속해올때 제니릭을 <엔티티명, PK 데이터타입> 으로 설정해주니까
// 복합키를 사용할때는 복합키용으로 만든 복합키 클래스를 지정해줘야함.
public interface Friends_repo extends JpaRepository<Friends, Friends_PK>{

}
