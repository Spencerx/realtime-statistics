package com.statistics.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestStatisticsService {

	@Test
	public void emptyStatistics()
	{
		IStatisticsService statsService = new StatisticsService();
		
		assertEquals(0, statsService.getStatisticsCount());
	}
	
	@Test
	public void Statistics_Size_One()
	{
		IStatisticsService statsService = new StatisticsService();
		
		statsService.addTransaction(new Transaction());
		assertEquals(1, statsService.getStatisticsCount());
	}
	
	@Test
	public void Statistics_Size_Two()
	{
		IStatisticsService statsService = new StatisticsService();
		
		statsService.addTransaction(new Transaction());
		statsService.addTransaction(new Transaction());

		assertEquals(2, statsService.getStatisticsCount());
	}
}
