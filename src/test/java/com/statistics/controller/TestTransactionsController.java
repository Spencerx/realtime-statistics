package com.statistics.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.statistics.domain.IStatisticsService;
import com.statistics.domain.StatisticsService;
import com.statistics.domain.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TransactionsController.class, StatisticsService.class })
public class TestTransactionsController {

	@Autowired
	TransactionsController transactionController;

	@Autowired
	IStatisticsService statisticService;

	@Before
	public void setup() {
		statisticService.reset();
	}

	@Test
	public void testSimpleAddTransaction() {
		Transaction transaction = new Transaction(12.3, 1478192204000L);

		assertEquals(0, statisticService.getStatisticsCount());

		assertEquals(HttpStatus.NO_CONTENT, transactionController.addTransaction(transaction).getStatusCode());

		assertEquals(1, statisticService.getStatisticsCount());
	}
}
