package com.subhan.onlinebank.dto;

import lombok.Data;

@Data
public class TransferResp {
	private String fromAcc;
	private String toAcc;
	private double availableAmount;
	private String message;
	private String status;
}
