package com.statistics.domain;

import org.springframework.stereotype.Component;

@Component
public class TimeProvider {

	public long getCurrentTime()
	{
		return System.currentTimeMillis();
	}
}
