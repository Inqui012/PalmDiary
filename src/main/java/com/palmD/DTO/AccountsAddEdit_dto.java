package com.palmD.DTO;

import lombok.Data;

@Data
public class AccountsAddEdit_dto {
	private Long accId;
	private int accMoney;
	private String accName;
	private String accDetail;
	private Long accGroupId;
	private String accStatus;
	private String accDate;
}
