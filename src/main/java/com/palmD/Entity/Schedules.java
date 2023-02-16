package com.palmD.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.thymeleaf.util.StringUtils;

import com.palmD.Constant.ScheduleRecurrence;
import com.palmD.Constant.YesNo;
import com.palmD.DTO.SchedulesAddEdit_dto;

import lombok.*;

// Data 어노테이션이 equals, HashCode 메소드를 만들때 자동으로 부모 클래스의 값도 계산하는것 같은데
// 여기서의 부모는 딱히 그런게 필요한 애가 아니니까 부모클래스 부르지 말라고 속성 추가해줘야 하는듯.
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "schedules")
public class Schedules extends BaseTime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long scheId;
	
	@JoinColumn(name = "calGroups")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private CalGroups calGroups;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users userId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private YesNo isAllday;
	
	@Column(nullable = false)
	private String scheName;
	private String scheDetail;
	
	@Column(nullable = false)
	private LocalDateTime scheStartdate;
	
	@Column(nullable = false)
	private LocalDateTime scheEnddate;
	
	@Enumerated(EnumType.STRING)
	private ScheduleRecurrence recuFreq;
	private int recuInterval;
	
	public static Schedules createSchedule (SchedulesAddEdit_dto schedulesAddEditDto, Users user, CalGroups group, DateTimeFormatter formatter) {
		Schedules schedule = new Schedules();
		
		schedule.setUserId(user);
		schedule.setCalGroups(group);
		schedule.setScheName(schedulesAddEditDto.getScheName());
		schedule.setScheDetail(schedulesAddEditDto.getShceDetail());
		
		schedule.setIsAllday(StringUtils.equals(schedulesAddEditDto.getIsAllDay(), "TRUE") ? YesNo.Y : YesNo.N);
	
		if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "NONE")) {
			schedule.setRecuFreq(ScheduleRecurrence.NONE);
			schedule.setRecuInterval(0);
		}else {
			if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "DAILY")) schedule.setRecuFreq(ScheduleRecurrence.DAILY);
			if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "WEEKLY")) schedule.setRecuFreq(ScheduleRecurrence.WEEKLY);
			if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "MONTHLY")) schedule.setRecuFreq(ScheduleRecurrence.MONTHLY);
			if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "YEARLY")) schedule.setRecuFreq(ScheduleRecurrence.YEARLY);
			schedule.setRecuInterval(schedulesAddEditDto.getReccurInterval());
		}
		
		schedule.setScheStartdate(LocalDateTime.parse(schedulesAddEditDto.getStrDate(), formatter));
		schedule.setScheEnddate(LocalDateTime.parse(schedulesAddEditDto.getEndDate(), formatter));
		
		return schedule;
	}
	
	public void updateSchedule (SchedulesAddEdit_dto schedulesAddEditDto, CalGroups group, DateTimeFormatter formatter) {
		this.calGroups = group;
		this.isAllday = StringUtils.equals(schedulesAddEditDto.getIsAllDay(), "TRUE") ? YesNo.Y : YesNo.N;
		this.scheDetail = schedulesAddEditDto.getShceDetail();
		this.scheName = schedulesAddEditDto.getScheName();
		
		if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "NONE")) {
			this.recuFreq = ScheduleRecurrence.NONE;
			this.recuInterval = 0;
		}else {
			if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "DAILY")) this.recuFreq = ScheduleRecurrence.DAILY;
			if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "WEEKLY")) this.recuFreq = ScheduleRecurrence.WEEKLY;
			if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "MONTHLY")) this.recuFreq = ScheduleRecurrence.MONTHLY;
			if(StringUtils.equals(schedulesAddEditDto.getReccurFreq(), "YEARLY")) this.recuFreq = ScheduleRecurrence.YEARLY;
			this.recuInterval = schedulesAddEditDto.getReccurInterval();
		}
		this.scheStartdate = LocalDateTime.parse(schedulesAddEditDto.getStrDate(), formatter);
		this.scheEnddate = LocalDateTime.parse(schedulesAddEditDto.getEndDate(), formatter);
	}
}
