package com.palmD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.palmD.Constant.GroupsDiv;
import com.palmD.Entity.CalGroups;
import com.palmD.Entity.Users;

public interface CalGroups_repo extends JpaRepository<CalGroups, Long>{
	List<CalGroups> findByUserIdAndGroupsDivOrderByGroupsStAscGroupsNdAsc(Users userId, GroupsDiv groupsDiv);
	
	@Query("SELECT c FROM CalGroups c WHERE c.userId = :u AND c.groupsDiv = :gdiv AND c.groupsSt = :gst AND c.groupsNd != 'NONE' ORDER BY c.groupsNd ASC ")
	List<CalGroups> findChildGroups(@Param("u") Users userId, @Param("gdiv") GroupsDiv groupsDiv, @Param("gst") String groupsSt);

	@Query("SELECT c FROM CalGroups c WHERE c.userId = :u AND c.groupsDiv = :gdiv AND c.groupsNd = 'NONE' ORDER BY c.groupsSt ASC")
	List<CalGroups> findParentsGroups(@Param("u") Users userId, @Param("gdiv") GroupsDiv groupsDiv);
}
