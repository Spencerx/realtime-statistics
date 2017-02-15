package com.statistics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.statistics.domain.Transaction;

@RestController
public class TransactionsController {

	@PostMapping("/transactions")
	public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction) {
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
}
