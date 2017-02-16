package com.statistics.domain;

public interface IStatisticsService {

	void addTransaction(Transaction transaction);
	long getStatisticsCount();
	void reset();
	StatisticsResult getStatisticsResult();
	
}
