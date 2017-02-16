package com.statistics.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestStatisticsService {

	StatisticsService statsService;

	@Before
	public void setup() {
		statsService = new StatisticsService(new TimeProvider());
	}

	@Test
	public void emptyStatistics() {
		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(0), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(0), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(0), Double.valueOf(result.count));
		assertEquals(null, result.max);
		assertEquals(null, result.min);
	}

	@Test
	public void nullTransaction() {

		statsService.addTransaction(null);
		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(0), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(0), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(0), Double.valueOf(result.count));
		assertEquals(null, result.max);
		assertEquals(null, result.min);
	}

	@Test
	public void transaction_Value_Zero() {

		statsService.addTransaction(new Transaction(0, System.currentTimeMillis()));
		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(0), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(0), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(1), Double.valueOf(result.count));
		assertEquals(Double.valueOf(0), result.max);
		assertEquals(Double.valueOf(0), result.min);
	}

	@Test
	public void remove_Value_Zero() {

		TimeProvider tp = Mockito.mock(TimeProvider.class);

		Mockito.when(tp.getCurrentTime()).thenReturn(250 * 1000L);

		statsService = new StatisticsService(tp);

		statsService.addTransaction(new Transaction(0, 250 * 1000L));

		StatisticsResult result = statsService.getStatisticsResult();
		assertEquals(Double.valueOf(1), Double.valueOf(result.count));
		assertEquals(Double.valueOf(0), result.max);
		assertEquals(Double.valueOf(0), result.min);

		// Delete old results:
		Mockito.when(tp.getCurrentTime()).thenReturn(400 * 1000L);
		statsService.removeOldEntries();

		StatisticsResult result2 = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(0), Double.valueOf(result2.sum));
		assertEquals(Double.valueOf(0), Double.valueOf(result2.avg));
		assertEquals(Double.valueOf(0), Double.valueOf(result2.count));
		assertEquals(null, result2.max);
		assertEquals(null, result2.min);
	}

	@Test
	public void two_Transactions() {

		statsService.addTransaction(new Transaction(1, System.currentTimeMillis()));
		statsService.addTransaction(new Transaction(2, System.currentTimeMillis()));
		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(3), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(1.5), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(2), Double.valueOf(result.count));
		assertEquals(Double.valueOf(2), result.max);
		assertEquals(Double.valueOf(1), result.min);
	}

	@Test
	public void addOldTransaction() {
		statsService.addTransaction(new Transaction(1, System.currentTimeMillis()));
		// Add an old value:
		statsService.addTransaction(new Transaction(2, System.currentTimeMillis() - 300 * 1000));
		
		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(1), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(1), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(1), Double.valueOf(result.count));
		assertEquals(Double.valueOf(1), result.max);
		assertEquals(Double.valueOf(1), result.min);
	}

	@Test
	public void three_Transacations() {

		statsService.addTransaction(new Transaction(-1, System.currentTimeMillis()));
		statsService.addTransaction(new Transaction(2, System.currentTimeMillis()));
		statsService.addTransaction(new Transaction(11, System.currentTimeMillis()));

		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(12), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(4), Double.valueOf(result.avg));
		assertEquals(Double.valueOf(3), Double.valueOf(result.count));
		assertEquals(Double.valueOf(11), result.max);
		assertEquals(Double.valueOf(-1), result.min);
	}

	@Test
	public void tree_transactions_one_expires() {
		TimeProvider tp = Mockito.mock(TimeProvider.class);

		Mockito.when(tp.getCurrentTime()).thenReturn(250 * 1000L);

		statsService = new StatisticsService(tp);

		statsService.addTransaction(new Transaction(17, 200 * 1000));
		statsService.addTransaction(new Transaction(2, 299 * 1000));
		statsService.addTransaction(new Transaction(11, 300 * 1000));

		StatisticsResult firstResult = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(30), Double.valueOf(firstResult.sum));
		assertEquals(Double.valueOf(3), Double.valueOf(firstResult.count));
		assertEquals(Double.valueOf(17), firstResult.max);
		assertEquals(Double.valueOf(2), firstResult.min);

		// Delete old results:
		Mockito.when(tp.getCurrentTime()).thenReturn(290 * 1000L);
		statsService.removeOldEntries();

		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(13), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(2), Double.valueOf(result.count));
		assertEquals(Double.valueOf(11), result.max);
		assertEquals(Double.valueOf(2), result.min);
	}
	
	@Test
	public void transactions_same_max_one_expires() {
		TimeProvider tp = Mockito.mock(TimeProvider.class);

		Mockito.when(tp.getCurrentTime()).thenReturn(250 * 1000L);

		statsService = new StatisticsService(tp);

		statsService.addTransaction(new Transaction(10, 200 * 1000));
		statsService.addTransaction(new Transaction(2, 299 * 1000));
		statsService.addTransaction(new Transaction(10, 300 * 1000));

		StatisticsResult firstResult = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(22), Double.valueOf(firstResult.sum));
		assertEquals(Double.valueOf(10), firstResult.max);
		assertEquals(Double.valueOf(2), firstResult.min);

		// Delete old results:
		Mockito.when(tp.getCurrentTime()).thenReturn(290 * 1000L);
		statsService.removeOldEntries();

		StatisticsResult result = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(12), Double.valueOf(result.sum));
		assertEquals(Double.valueOf(2), Double.valueOf(result.count));
		assertEquals(Double.valueOf(10), result.max);
		assertEquals(Double.valueOf(2), result.min);
	}

	@Test
	public void multhiThread() {

		AddTransactions addTransactions1 = new AddTransactions(statsService, 100);
		AddTransactions addTransactions2 = new AddTransactions(statsService, 200);
		Thread newThread1 = new Thread(addTransactions1);
		Thread newThread2 = new Thread(addTransactions2);

		newThread1.start();
		newThread2.start();

		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		StatisticsResult result = statsService.getStatisticsResult();

		// Expect that sum == count, since we are always adding 1.
		assertEquals(Double.valueOf(result.count), Double.valueOf(result.sum));

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		StatisticsResult result2 = statsService.getStatisticsResult();

		// Expect that sum == count, since we are always adding 1.
		assertEquals(Double.valueOf(result2.count), Double.valueOf(result2.sum));

		try {
			newThread1.join();
			newThread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// After threads are done we should have 300 values:
		StatisticsResult result3 = statsService.getStatisticsResult();

		assertEquals(Double.valueOf(300), Double.valueOf(result3.sum));
		assertEquals(Double.valueOf(300), Double.valueOf(result3.count));
		assertEquals(Double.valueOf(1), Double.valueOf(result3.avg));
		assertEquals(Double.valueOf(1), result3.max);
		assertEquals(Double.valueOf(1), result3.min);
	}

	private class AddTransactions implements Runnable {
		IStatisticsService statsService;
		int numberOfAdds;

		public AddTransactions(IStatisticsService statsService, int numberOfAdds) {
			this.statsService = statsService;
			this.numberOfAdds = numberOfAdds;
		}

		public void run() {

			for (int i = 0; i < numberOfAdds; i++) {
				statsService.addTransaction(new Transaction(1, System.currentTimeMillis()));
			}
		}
	}
}
