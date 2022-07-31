package com.subhan.onlinebank.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subhan.onlinebank.OnlineBankAppApplication;
import com.subhan.onlinebank.dto.CreateAccReq;
import com.subhan.onlinebank.dto.CreateAccResp;
import com.subhan.onlinebank.dto.DepositReq;
import com.subhan.onlinebank.dto.DepositResp;
import com.subhan.onlinebank.dto.TransferReq;
import com.subhan.onlinebank.dto.TransferResp;
import com.subhan.onlinebank.dto.WithdrawReq;
import com.subhan.onlinebank.dto.WithdrawResp;
import com.subhan.onlinebank.entiry.Transaction;
import com.subhan.onlinebank.exception.MyCustExp;
import com.subhan.onlinebank.service.OnlineBankService;
import com.subhan.onlinebank.util.PDFGenerator;

@RestController
@RequestMapping("/bank")
@EnableTransactionManagement
public class OnlineBankController {
	Logger log = LoggerFactory.getLogger(OnlineBankAppApplication.class);
	@Autowired
	public OnlineBankService service;

	@PostMapping("/create")
	public CreateAccResp createAccount(@RequestBody @Valid CreateAccReq req) {
		log.info("--------- create account start------");
		return service.createAccount(req);
	}

	@PostMapping("/transfer")
	public TransferResp transfer(@RequestBody @Valid TransferReq req) {
		log.info("--------- transfer transaction start------");
		return service.transferAmount(req);
	}

	@PutMapping("/deposit")
	public DepositResp deposit(@RequestBody @Valid DepositReq req) {
		log.info("--------- deposit transaction start------");
		return service.depositAmount(req);
	}

	@PutMapping("/withdraw")
	public WithdrawResp withdraw(@RequestBody @Valid WithdrawReq req) {
		log.info("--------- withdraw transaction start------");
		return service.withdrawAmount(req);
	}

	@GetMapping("/checkBal/{accNum}")
	public String checkBal(@PathVariable String accNum) {
		log.info(" checkBal  called ->  acc :{}", accNum);
		return service.checkBal(accNum);
	}

	@GetMapping("/stmt")
	public List<Transaction> getAllStatement() {
		log.info(" getAllStatement  called ");
		return service.getAllStatement();
	}

	@GetMapping("/stmt/{accNum}")
	public List<Transaction> getStatementByAcc(@PathVariable String accNum) {
		log.info(" getStatementByAcc  called ");
		return service.getStatementByAcc(accNum);
	}

	@GetMapping("/stmt5/{accNum}")
	public List<Transaction> getLast5StatementByAcc(@PathVariable String accNum) {
		log.info(" getLast5StatementByAcc  called ");
		return service.getLast5StatementByAcc(accNum);
	}

	@GetMapping("/exp/{name}")
	public String exp(@PathVariable String name) {
		log.info(" test exception  called ");
		String errMgs = "my exp message";
		if (!"subhan".equalsIgnoreCase(name)) {
			throw new MyCustExp(errMgs);
		}

		return "Hello " + name.toUpperCase();

	}

	@GetMapping(value = "/pdf/{accNum}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> employeeReport(@PathVariable String accNum) throws IOException {
		List<Transaction> txs = (List<Transaction>)service.getStatementByAcc(accNum);
	  	
		ByteArrayInputStream bis = PDFGenerator.employeePDFReport(txs);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=satement.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
}
