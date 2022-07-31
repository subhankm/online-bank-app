package com.subhan.onlinebank.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.subhan.onlinebank.dto.CreateAccReq;
import com.subhan.onlinebank.dto.CreateAccResp;
import com.subhan.onlinebank.dto.DepositReq;
import com.subhan.onlinebank.dto.DepositResp;
import com.subhan.onlinebank.dto.TransferReq;
import com.subhan.onlinebank.dto.TransferResp;
import com.subhan.onlinebank.dto.WithdrawReq;
import com.subhan.onlinebank.dto.WithdrawResp;
import com.subhan.onlinebank.entiry.Account;
import com.subhan.onlinebank.entiry.Customer;
import com.subhan.onlinebank.entiry.Transaction;
import com.subhan.onlinebank.exception.DuplicateDataException;
import com.subhan.onlinebank.repository.AccountRepository;
import com.subhan.onlinebank.repository.CustomerRepository;
import com.subhan.onlinebank.repository.TransactionRepository;
import com.subhan.onlinebank.util.App;

@Service
public class OnlineBankService {
	Logger log = LoggerFactory.getLogger(OnlineBankService.class);
	@Autowired
	public AccountRepository accRepo;
	@Autowired
	public CustomerRepository custRepo;
	@Autowired
	public TransactionRepository txRepo;
	@Autowired
	public UtilBankingService utilService;

	// create account
	public CreateAccResp createAccount(CreateAccReq req) {
		// check duplicate validation (email and phone must be unique)
		log.info("Customer data validation checking...");
		if (custRepo.existsByEmail(req.getEmail())) {
			log.error("Filed to create account. Email id already exist :"+req.getEmail());
			throw new DuplicateDataException("Filed to create account. Email id already exist :"+req.getEmail());
		}
		if (custRepo.existsByPhone(req.getPhone())) {
			log.error("Filed to create account. Phone number already exist :"+req.getPhone());
			throw new DuplicateDataException("Filed to create account.Phone number already exist :"+req.getPhone());
		}
		log.info("Customer data validation has been done.");		
		String cId = utilService.createNewCustID();
		String accId = utilService.createNewAccID();
		
		log.info("customer id : {} and account num :{} generated",cId,accId);
		Customer customer = new Customer();
		customer.setCustId(cId);
		customer.setName(req.getName());
		customer.setEmail(req.getEmail());
		customer.setPhone(req.getPhone());
		customer.setAddress(req.getAddress());
		customer.setAccId(accId);
		custRepo.save(customer);		
		log.info("customer object saved ");
		
		Account acc = new Account();
		acc.setCustId(cId);
		acc.setAccNum(accId);		
		acc.setBal(10000.00);// default deposit amount 10k
		accRepo.save(acc);
		log.info("Account object saved with default deposit: {} ",acc.getBal());
         
		// Save Tx info for credit
		Transaction tx = new Transaction();
		tx.setTxId(utilService.createNewTxID(App.STMT));
		tx.setTxType(App.C_DEPOSIT);
		tx.setAccNum(accId);
		tx.setNarration(App.NARRATION_CREDITED_FROM_BANK + "HDFC1231");
		tx.setCredit("" + acc.getBal());
		tx.setDebit("");
		tx.setUpdatedBal(acc.getBal());
		txRepo.save(tx);
		log.info("Transaction recorded with default deposit {} ",acc.getBal());
		
		CreateAccResp resp = new CreateAccResp();
		resp.setAccNum(accId);
		resp.setBal(acc.getBal());
		resp.setCustId(cId);
		resp.setStatus(App.SUCCESS);
		resp.setMessage(App.ACC_CREATED_SUUCESS);
		log.info("resp object has been retured ");
		log.info("--------- create account end------");
		return resp;

	}

	/*---------basic transactions---------*/

	// Transfer Amount
	@Transactional(rollbackFor = Exception.class)
	public TransferResp transferAmount(TransferReq req) {
		log.info("Account validation checking...");
		// Account validation check
		utilService.isValidAccount(req.getFromAcc());
		utilService.isValidAccount(req.getToAcc());
		// Account withdraw validation check
		utilService.checkValidForWithDraw(req.getFromAcc(), req.getAmt());
		log.info("Account validation has been done.");
		
        // TransferAmount logic
		double senderNewBal = utilService.getBalByAcc(req.getFromAcc()) - req.getAmt();
		double receiverNewBal = utilService.getBalByAcc(req.getToAcc()) + req.getAmt();

		utilService.updateBalByAcc(req.getFromAcc(), senderNewBal);
		utilService.updateBalByAcc(req.getToAcc(), receiverNewBal);
		log.info("transfer amount {} updated both accounts",req.getAmt());
		
		// statement info for debit
		Transaction tx_debit = new Transaction();
		tx_debit.setTxId(utilService.createNewTxID(App.STMT));
		tx_debit.setTxType(App.D_TRANSFER_FROM);
		tx_debit.setAccNum(req.getFromAcc());
		tx_debit.setNarration(App.NARRATION_DEBITED_TO_ACC + req.getToAcc());
		tx_debit.setCredit("");
		tx_debit.setDebit("" + req.getAmt());
		tx_debit.setUpdatedBal(senderNewBal);
		txRepo.save(tx_debit);
		log.info("debited transaction record added ");
		
		//  if (true) throw new MyCustExp("Test Exception Message");
		 
		// statement info for credit
		Transaction tx_credit = new Transaction();
		tx_credit.setTxId(utilService.createNewTxID(App.STMT));
		tx_credit.setTxType(App.C_TRANSFER_TO);
		tx_credit.setAccNum(req.getToAcc());
		tx_credit.setNarration(App.NARRATION_CREDITED_FROM_ACC + req.getFromAcc());
		tx_credit.setCredit("" + req.getAmt());
		tx_credit.setDebit("");
		tx_credit.setUpdatedBal(receiverNewBal);
		txRepo.save(tx_credit);			
		log.info("credited transaction record added ");

		TransferResp resp = new TransferResp();
		resp.setFromAcc(req.getFromAcc());
		resp.setToAcc(req.getToAcc());
		resp.setAvailableAmount(senderNewBal);
		String message = req.getAmt() + " amount has been transfered";
		resp.setMessage(message);
		resp.setStatus(App.SUCCESS);
		log.info("resp object has been retured ");
		log.info("--------- transfer transaction end------");
		return resp;

	}

	// Deposit Amount
	public DepositResp depositAmount(DepositReq req) {
		log.info("Account validation checking...");
		// Account validation check
		utilService.isValidAccount(req.getAccNum());
		log.info("Account validation has been done.");
		
		double accNewBal = utilService.getBalByAcc(req.getAccNum()) + req.getAmt();
		utilService.updateBalByAcc(req.getAccNum(), accNewBal);
		log.info("depositAmount transaction completed");
		
		// Tx info for credit
		Transaction tx = new Transaction();
		tx.setTxId(utilService.createNewTxID(App.STMT));
		tx.setTxType(App.C_DEPOSIT);
		tx.setAccNum(req.getAccNum());
		tx.setNarration(App.NARRATION_CREDITED_FROM_BANK + "HDFC1231");
		tx.setCredit("" + req.getAmt());
		tx.setDebit("");
		tx.setUpdatedBal(utilService.getBalByAcc(req.getAccNum()));
		txRepo.save(tx);
		log.info("credited transaction record added");
		
		DepositResp resp = new DepositResp();
		resp.setAccNum(req.getAccNum());
		String message = req.getAmt() + " amount has been Credited ";
		resp.setMessage(message);
		resp.setStatus(App.SUCCESS);
		resp.setAvailableAmount(accNewBal);
		log.info("resp object has been retured ");
		log.info("--------- deposit transaction end------");
		return resp;

	}

	// Withdraw Amount
	public WithdrawResp withdrawAmount(WithdrawReq req) {
		log.info("Account validation checking...");
		// Account validation check
		utilService.isValidAccount(req.getAccNum());
		// Account withdraw validation check
		utilService.checkValidForWithDraw(req.getAccNum(), req.getWithdrawAmt());
		log.info("Account validation has been done.");
		
		double updatedBal = utilService.getBalByAcc(req.getAccNum()) - req.getWithdrawAmt();
		utilService.updateBalByAcc(req.getAccNum(), updatedBal);
		log.info("withdraw transaction completed");
		
		// statement info for credit
		Transaction tx_credit = new Transaction();
		tx_credit.setTxId(utilService.createNewTxID(App.STMT));
		tx_credit.setTxType(App.D_WITHDRAW);
		tx_credit.setAccNum(req.getAccNum());
		tx_credit.setNarration(App.NARRATION_DEBITED_FROM_ATM);
		tx_credit.setCredit("");
		tx_credit.setDebit("" + req.getWithdrawAmt());
		tx_credit.setUpdatedBal(updatedBal);
		txRepo.save(tx_credit);
		log.info("debited transaction record added");
		WithdrawResp resp = new WithdrawResp();
		resp.setAccNum(req.getAccNum());
		String message = req.getWithdrawAmt() + " amount has been debited ";
		resp.setMessage(message);
		resp.setStatus(App.SUCCESS);
		resp.setAvailableAmount(updatedBal);
		log.info("resp object has been retured ");
		log.info("--------- withdraw  transaction end------");
		return resp;

	}

	// Check Bal
	public String checkBal(String accNum) {
		return utilService.getBalByAcc(accNum) + "";
	}

	// Get last 5 tx By AccNum
	public List<Transaction> getLast5StatementByAcc(String accNum) {
		// Account validation check
		utilService.isValidAccount(accNum);
		return txRepo.findLast5ByAccNumOrderByCreatedDesc(accNum);

	}

	// Get all tx By AccNum
	public List<Transaction> getStatementByAcc(String accNum) {
		// Account validation check
	  	utilService.isValidAccount(accNum);
		return txRepo.findByAccNumOrderByCreatedDesc(accNum);

	}

	// Get all tx
	public List<Transaction> getAllStatement() {
		return txRepo.findAllByOrderByCreatedDesc();

	}
}
