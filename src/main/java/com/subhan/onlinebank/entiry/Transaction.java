package com.subhan.onlinebank.entiry;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Table
@Entity
public class Transaction {
	@Id
	private String txId;
	private String accNum;
	private String narration;
	private String debit;
	private String credit;
	private double updatedBal;
	private String txType;	
	@CreationTimestamp
	private LocalDateTime created;

}
