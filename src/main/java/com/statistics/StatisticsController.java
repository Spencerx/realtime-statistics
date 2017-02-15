package com.statistics;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

	@RequestMapping("/statistics")
	public String getStatistics() {
		return "1";
	}

	
}
