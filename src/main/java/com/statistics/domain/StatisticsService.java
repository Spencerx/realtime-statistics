package com.statistics.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService implements IStatisticsService {
	double sumValues;
	long countValue;
	TreeMap<Double, Long> maxValuesMap;
	TreeMap<Long, List<Double>> valuesForTimestamp;

	private TimeProvider timeProvider;

	private static final int STATISTICS_NUMBER_OF_MILISECONDS = 60 * 1000;

	@Autowired
	public StatisticsService(TimeProvider timeProvider) {
		valuesForTimestamp = new TreeMap<Long, List<Double>>();
		maxValuesMap = new TreeMap<Double, Long>();
		this.timeProvider = timeProvider;
	}

	@Scheduled(fixedRate = 1000)
	synchronized public void removeOldEntries() {
		long oldestValidTimestamp = oldestValidStatistic();
		// Remove old values:
		Entry<Long, List<Double>> valueEntry = null;
		while ((valueEntry = valuesForTimestamp.floorEntry(oldestValidTimestamp)) != null) {
			if (valueEntry.getKey() < oldestValidTimestamp) {
				countValue -= valueEntry.getValue().size();
				removeSumAndMax(valueEntry.getValue());
				valuesForTimestamp.remove(valueEntry.getKey());
			}
		}
	}

	private void removeSumAndMax(List<Double> list) {
		for (Double value : list) {
			sumValues -= value;

			Long maxCountForNumber = maxValuesMap.get(value);
			if (maxCountForNumber == 1) {
				maxValuesMap.remove(value);
			} else {
				maxValuesMap.put(value, maxCountForNumber - 1);
			}
		}
	}

	private boolean isOldStatistics(long timestamp) {
		return timestamp < oldestValidStatistic();
	}

	private long oldestValidStatistic() {
		return timeProvider.getCurrentTime() - STATISTICS_NUMBER_OF_MILISECONDS;
	}

	synchronized public void addTransaction(Transaction transaction) {
		if (transaction == null || isOldStatistics(transaction.getTimestamp())) {
			return;
		}

		sumValues += transaction.getAmount();
		countValue++;

		// Always insert in order:
		insertInMaps(transaction);
	}

	private void insertInMaps(Transaction transaction) {
		// Insert in the Sum of values map:
		List<Double> values = valuesForTimestamp.get(transaction.getTimestamp());
		if (values == null) {
			values = new ArrayList<Double>();
			values.add(transaction.getAmount());
			valuesForTimestamp.put(transaction.getTimestamp(), values);
		} else {
			values.add(transaction.getAmount());
		}

		// Insert in the max map:
		Long maxNumber = maxValuesMap.get(transaction.getAmount());
		if (maxNumber == null) {
			maxValuesMap.put(transaction.getAmount(), 1L);
		} else {
			maxValuesMap.put(transaction.getAmount(), maxNumber + 1);
		}

	}

	synchronized public long getStatisticsCount() {
		return countValue;
	}

	synchronized public void reset() {
		valuesForTimestamp.clear();
		maxValuesMap.clear();

		countValue = 0;
		sumValues = 0;
	}

	synchronized public StatisticsResult getStatisticsResult() {
		StatisticsResult result = new StatisticsResult();
		result.sum = sumValues;
		result.avg = countValue > 0 ? sumValues / countValue : 0;
		result.count = countValue;
		result.max = maxValuesMap.size() > 0 ? maxValuesMap.lastKey() : null;
		result.min = maxValuesMap.size() > 0 ? maxValuesMap.firstKey() : null;
		return result;
	}

}
