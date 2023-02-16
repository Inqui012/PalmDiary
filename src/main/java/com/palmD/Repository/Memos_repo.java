package com.palmD.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.palmD.Entity.Memos;
import com.palmD.Entity.Users;

public interface Memos_repo extends JpaRepository<Memos, Long> {

	List<Memos> findByUserId(Users user);
	@Query("SELECT m.memoDate FROM Memos m WHERE m.userId = :u ORDER BY m.memoDate")
	List<LocalDateTime> findDayHasMemos(@Param("u") Users user);
}
