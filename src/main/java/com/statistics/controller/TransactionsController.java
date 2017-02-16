package com.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.statistics.domain.IStatisticsService;
import com.statistics.domain.Transaction;

@Controller
public class TransactionsController {

	@Autowired
	IStatisticsService statisticsService;

	@PostMapping("/transactions")
	public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction) {
		statisticsService.addTransaction(transaction);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
}
