package com.statistics.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestStatisticsService {

	@Test
	public void emptyStatistics() {
		IStatisticsService statsService = new StatisticsService();
		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(0), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(0), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(0), Double.valueOf(result.count));
		assertEquals(null, result.max);
		assertEquals(null, result.min);
	}

	@Test
	public void Statistics_NullTransaction() {
		IStatisticsService statsService = new StatisticsService();

		statsService.addTransaction(null);
		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(0), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(0), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(0), Double.valueOf(result.count));
		assertEquals(null, result.max);
		assertEquals(null, result.min);
	}

	@Test
	public void Statistics_Size_Two() {
		IStatisticsService statsService = new StatisticsService();

		statsService.addTransaction(new Transaction(1, 500));
		statsService.addTransaction(new Transaction(2, 500));
		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(3), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(1.5), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(2), Double.valueOf(result.count));
		assertEquals(Double.valueOf(2), result.max);
		assertEquals(Double.valueOf(1), result.min);
	}
}
