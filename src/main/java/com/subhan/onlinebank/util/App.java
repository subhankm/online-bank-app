package com.subhan.onlinebank.util;

import java.util.Random;

public class App {
	    
	
	public static final String STMT = "statement";
	// UNIQUE ID STARTS
	public static final String CUST_ID_STARTS = "C";
	public static final String ACC_ID_STARTS = "9";
	public static final String TRANSFER_TX_STARTS = "TXT";
	public static final String DEPOSIT_TX_STARTS = "TXD";
	public static final String WITHDRAW_TX_STARTS = "TXW";
	// Customer status
	public static final String C1 = "DOCUMENTS_PENDING";
	public static final String C2= "VERIFICATION_PENDING";
	public static final String C3 = "APPROVAL_PENDING";
	public static final String C4 = "APPROVAL_REJECTED";
	public static final String IN_ACTIVE = "IN_ACTIVE";
	public static final String ACTIVE = "ACTIVE";
	
	// Response messages
	
	public static final String ACC_CREATED_SUUCESS = "AccountNumber and CustonerId has been created ";
	public static final String SUCCESS = "success";
	public static final String INVALID = "invalid";
	public static final String FAILED = "failed";
	public static final String INVALID_ACC = "invalid account ";
	public static final String INSUF_AMT = "Insufficient Amount";
	
       //	TxNarrationType
	
	public static final String NARRATION_DEBITED_TO_ACC = "Debited bal to a/c :";
	public static final String NARRATION_CREDITED_FROM_ACC = "Credited bal from a/c :";
	public static final String NARRATION_DEBITED_FROM_ATM = "Debited(Withdraw) bal from ATM :";
	public static final String NARRATION_CREDITED_FROM_BANK = "Credited(Deposit) bal from Bank :";

//	TxType
	public static final String D_TRANSFER_FROM = "DEBITED(TRANSFER_FROM)";
	public static final String C_TRANSFER_TO = "CREDITED(TRANSFER_TO)";
	public static final String D_WITHDRAW = "DEBITED(WITHDRAW)";
	public static final String C_DEPOSIT = "CREDITED(DEPOSIT)";

	static Random rand = new Random();

	public static synchronized String getNum(int len) {
		String txStart = "";
		for (int i = 0; i < len; i++)
			txStart += Integer.toString(rand.nextInt(10));
		return txStart;
	}
	
	
}
