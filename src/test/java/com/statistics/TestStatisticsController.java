package com.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestStatisticsController {

	@Test 
	public void testSimpleGetStatistics(){
		StatisticsController controller = new StatisticsController();
		assertEquals("1", controller.getStatistics());
	}
}
