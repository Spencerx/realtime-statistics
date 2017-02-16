package com.statistics.domain;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StatisticsService implements IStatisticsService {
	double sumValues;
	Double maxValue;
	Double minValue;
	List<Transaction> transactions;

	public StatisticsService() {
		transactions = new LinkedList<Transaction>();
	}

	synchronized public void addTransaction(Transaction transaction) {
		if (transaction == null) {
			return;
		}

		if (maxValue == null || transaction.getAmount() > maxValue) {
			maxValue = transaction.getAmount();
		}

		if (minValue == null || transaction.getAmount() < minValue) {
			minValue = transaction.getAmount();
		}

		sumValues += transaction.getAmount();
		transactions.add(transaction);
	}

	synchronized public int getStatisticsCount() {
		return transactions.size();
	}

	synchronized public void reset() {
		transactions.clear();
	}

	synchronized public StatisticsResult getStatisticsResult() {
		StatisticsResult result = new StatisticsResult();
		result.sum = sumValues;
		result.avg = transactions.size() > 0 ? sumValues / transactions.size() : 0;
		result.count = transactions.size();
		result.max = maxValue;
		result.min = minValue;
		return result;
	}

}
