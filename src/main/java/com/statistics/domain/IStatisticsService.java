package com.statistics.domain;

public interface IStatisticsService {

	void addTransaction(Transaction transaction);
	int getStatisticsCount();
	void reset();
	StatisticsResult getStatisticsResult();
	
}
