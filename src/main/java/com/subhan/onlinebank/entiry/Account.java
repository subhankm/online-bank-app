package com.subhan.onlinebank.entiry;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;


////@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
@Data
@Entity
@Table
public class Account {
	@Id
	private String accNum;	
	private double bal;
	private String custId;
	@UpdateTimestamp	
	private LocalDateTime updated;	
	
}
