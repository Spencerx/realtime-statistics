package com.statistics.domain;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StatisticsService implements IStatisticsService {
	List<Transaction> transactions;
	
	public StatisticsService()
	{
		transactions = new LinkedList<Transaction>();
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	public int getStatisticsCount() {
		return transactions.size();
	}

	public void reset() {
		transactions.clear();
	}

}
