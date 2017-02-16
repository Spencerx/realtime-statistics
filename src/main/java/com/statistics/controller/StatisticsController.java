package com.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.statistics.domain.IStatisticsService;

@Controller
public class StatisticsController {

	@Autowired
	IStatisticsService statisticsService;
	
	@GetMapping("/statistics")
	public String getStatistics() {
		return Integer.toString(statisticsService.getStatisticsCount());
	}

	
}
