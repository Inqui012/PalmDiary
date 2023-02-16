package com.palmD.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CallAllGroups_dto {
	private Long groupsId;
	private String GroupsName;
	private String groupsBgcolor;
	private List<CallAllGroups_dto> childrens = new ArrayList<>();
}
