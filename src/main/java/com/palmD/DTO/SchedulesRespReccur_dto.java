package com.palmD.DTO;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

import com.palmD.Constant.ScheduleRecurrence;
import com.palmD.Constant.YesNo;
import com.palmD.Entity.Schedules;

import lombok.Data;

@Data
public class SchedulesRespReccur_dto {
	private String title;
	private Rrule rrule;
	private boolean allDay;
	private String backgroundColor;
	private String borderColor;
	private String groupId;
	private boolean editable = true;
	private String details;
	private Long duration;
	private Long eventId;
	
	@Data
	public static class Rrule {
		private int interval;
		private String freq;
		private String dtstart;
	}
	
	public static SchedulesRespReccur_dto responseReccurSche (Schedules schedules) {
		SchedulesRespReccur_dto reccur = new SchedulesRespReccur_dto();
		reccur.setTitle(schedules.getScheName());
		reccur.setAllDay(schedules.getIsAllday() == YesNo.Y ? true : false);
		
		String color = schedules.getCalGroups().getGroupsBgcolor();
		reccur.setGroupId(String.valueOf(schedules.getCalGroups().getGroupsId()));
		reccur.setBackgroundColor(color);
		reccur.setBorderColor(color);
		reccur.setDetails(schedules.getScheDetail());
		reccur.setEventId(schedules.getScheId());
		Duration strDate = Duration.between(schedules.getScheStartdate(), schedules.getScheEnddate());
		reccur.setDuration(strDate.toMillis());
		
		Rrule rrule = new Rrule();
		rrule.setInterval(schedules.getRecuInterval());
		if(schedules.getRecuFreq() == ScheduleRecurrence.DAILY) rrule.setFreq("DAILY");
		if(schedules.getRecuFreq() == ScheduleRecurrence.WEEKLY) rrule.setFreq("WEEKLY");
		if(schedules.getRecuFreq() == ScheduleRecurrence.MONTHLY) rrule.setFreq("MONTHLY");
		if(schedules.getRecuFreq() == ScheduleRecurrence.YEARLY) rrule.setFreq("YEARLY");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		rrule.setDtstart(schedules.getScheStartdate().format(formatter));
		reccur.setRrule(rrule);
		
		return reccur;		
	}
}
