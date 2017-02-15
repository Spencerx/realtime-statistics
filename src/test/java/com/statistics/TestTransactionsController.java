package com.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.statistics.controller.TransactionsController;
import com.statistics.domain.Transaction;

public class TestTransactionsController {

	@Test
	public void testSimpleAddTransaction() {
		TransactionsController controller = new TransactionsController();
		Transaction transaction = new Transaction();

		assertEquals(HttpStatus.NO_CONTENT, controller.addTransaction(transaction).getStatusCode());
	}
}
