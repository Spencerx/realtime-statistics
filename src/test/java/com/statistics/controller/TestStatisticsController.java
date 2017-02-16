package com.statistics.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.statistics.domain.IStatisticsService;
import com.statistics.domain.StatisticsResult;
import com.statistics.domain.StatisticsService;
import com.statistics.domain.TimeProvider;
import com.statistics.domain.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StatisticsController.class, StatisticsService.class, TimeProvider.class })
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
		StatisticsResult result = statisticsController.getStatistics();
		assertEquals(0, result.count);
	}

	@Test
	public void testGetStatisticsSizeOne() {
		statisticService.addTransaction(new Transaction(5, System.currentTimeMillis()));

		StatisticsResult result = statisticsController.getStatistics();

		assertEquals(Double.valueOf(5), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(5), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(5), result.max);
		assertEquals(Double.valueOf(5), result.min);
		assertEquals(1, result.count);

	}
}
