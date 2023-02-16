package com.palmD.DTO;

import lombok.Data;

@Data
public class SchedulesAddEdit_dto {
	private Long shceId;
	private String scheName;
	private String shceDetail;
	private Long shceGroupId;
	private String strDate;
	private String endDate;
	private String isAllDay;
	private int reccurInterval;
	private String reccurFreq;	
}
