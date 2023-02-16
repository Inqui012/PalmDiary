package com.palmD.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.palmD.Constant.YesNo;
import com.palmD.Entity.Schedules;

import lombok.Data;

@Data
public class SchedulesRespNomal_dto {
	private String title;
	private String start;
	private String end;
	private boolean allDay;
	private String backgroundColor;
	private String borderColor;
	private String groupId;
	private boolean editable = true;

	private String details;
	private Long eventId;
	
	public static SchedulesRespNomal_dto responseNormalSche (Schedules schedules) {
		SchedulesRespNomal_dto normal = new SchedulesRespNomal_dto();
		normal.setTitle(schedules.getScheName());
		normal.setAllDay(schedules.getIsAllday() == YesNo.Y ? true : false);
		
		String color = schedules.getCalGroups().getGroupsBgcolor();
		normal.setGroupId(String.valueOf(schedules.getCalGroups().getGroupsId()));
		normal.setBackgroundColor(color);
		normal.setBorderColor(color);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		normal.setStart(schedules.getScheStartdate().format(formatter));
		LocalDateTime endDate = null;
//		endDate is exclusive
		if(normal.allDay) endDate = schedules.getScheEnddate().plusDays(1);
		else endDate = schedules.getScheEnddate();
		
		normal.setEnd(endDate.format(formatter));
		normal.setDetails(schedules.getScheDetail());
		normal.setEventId(schedules.getScheId());
		
		return normal;
	}
}
