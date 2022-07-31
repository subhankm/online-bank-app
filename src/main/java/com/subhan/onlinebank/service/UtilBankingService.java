package com.subhan.onlinebank.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subhan.onlinebank.OnlineBankAppApplication;
import com.subhan.onlinebank.exception.InsufficientBalanceException;
import com.subhan.onlinebank.exception.InvalidAccountNumException;
import com.subhan.onlinebank.repository.AccountRepository;
import com.subhan.onlinebank.repository.CustomerRepository;
import com.subhan.onlinebank.repository.TransactionRepository;
import com.subhan.onlinebank.util.App;

@Service
public class UtilBankingService {
	 Logger log = LoggerFactory.getLogger(UtilBankingService.class);

	@Autowired
	public AccountRepository accRepo;
	@Autowired
	public CustomerRepository custRepo;
	@Autowired
	public TransactionRepository txRepo;

	public double getBalByAcc(String accNum) {
		log.info("getBalByAcc() called ");
		  isValidAccount(accNum);
		return accRepo.getBalByAcc(accNum);
	}
	public void updateBalByAcc(String accNum, double amt) {
		accRepo.updateBalByAcc(accNum, amt);
	}
	

	public boolean isCustomerExist(String customerId) {
		return custRepo.existsById(customerId);
	}

	public boolean isAccountExist(String accNum) {
		return accRepo.existsById(accNum);

	}

	public void isValidAccount(String accNum) {
		if (!accRepo.existsById(accNum)) {
			log.error("Ivalid Account number :" + accNum);
			throw new InvalidAccountNumException("Ivalid Account number :"+ accNum);
		}
		
	}
	public void checkValidForWithDraw(String accNum,double withdrawAmt) {
		if (withdrawAmt > getBalByAcc(accNum)) {
			log.error("Insufficient funds !");
			throw new InsufficientBalanceException("Insufficient funds");
		}
		
	}

	public boolean isStatementTxIdExist(String txId) {
		return txRepo.existsById(txId);

	}

	/*---------Unique Id generate ---------*/
	public static synchronized String getNum(int len) {
		String txStart = "";
		for (int i = 0; i < len; i++)
			txStart += Integer.toString(new Random().nextInt(10));
		return txStart;
	}

	public String createNewCustID() {
		String cid = "C" + getNum(5);
		if (isCustomerExist(cid))
			createNewCustID();
		return cid;
	}

	public String createNewAccID() {
		String accId = "9" + getNum(8);
		if (isAccountExist(accId))
			createNewAccID();
		return accId;
	}

	public String createNewTxID(String txtype) {
		String txId = "TX" + getNum(6);
		if (txtype.equalsIgnoreCase(App.STMT)) {
			if (isStatementTxIdExist(txId))
				createNewTxID(txtype);
		} else {
         log.warn("something went wrong while creating tx id ");
		}
		return txId;
	}

}
