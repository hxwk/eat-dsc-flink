package com.eat.dataplatform.analysis.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The event type used in the {@link}.
 * 模拟测试从kafka 到 kafka
 */
public class KafkaEvent {
	private static final Logger logger = LoggerFactory.getLogger(KafkaEvent.class);
	private String word;
	private int frequency;
	private long timestamp;

	public KafkaEvent() {}

	public KafkaEvent(String word, int frequency, long timestamp) {
		this.word = word;
		this.frequency = frequency;
		this.timestamp = timestamp;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public static KafkaEvent fromString(String eventStr) {
		logger.info("eventStr: {}", eventStr);
		String[] split = eventStr.split(",");
		return new KafkaEvent(split[0], Integer.valueOf(split[1]), Long.valueOf(split[2]));
	}

	@Override
	public String toString() {
		return word + "," + frequency + "," + timestamp;
	}
}
