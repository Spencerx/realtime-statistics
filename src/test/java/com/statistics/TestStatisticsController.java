package com.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.statistics.controller.StatisticsController;


public class TestStatisticsController {

	@Test 
	public void testSimpleGetStatistics(){
		StatisticsController controller = new StatisticsController();
		assertEquals("1", controller.getStatistics());
	}
}
