package com.statistics.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.statistics.domain.IStatisticsService;
import com.statistics.domain.StatisticsService;
import com.statistics.domain.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StatisticsController.class, StatisticsService.class})
public class TestStatisticsController {

	@Autowired
	StatisticsController statisticsController;

	@Autowired
	IStatisticsService statisticService;

	@Before
	public void setup() {
		statisticService.reset();
	}

	@Test
	public void testGetStatisticsEmpty() {
		assertEquals("0", statisticsController.getStatistics());
	}

	@Test
	public void testGetStatisticsSizeOne() {
		statisticService.addTransaction(new Transaction());

		assertEquals("1", statisticsController.getStatistics());
	}
}
