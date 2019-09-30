package com.winner_is_kungen.tda367.model.util;

public class EventBusEvent<T> {
	private final String eventType;
	private final T message;

	/**
	 * Creates a new event with set a event type and message.
	 * @param eventType The event type of this event.
	 * @param message   The message of this event.
	 */
	public EventBusEvent(String eventType, T message) {
		this.eventType = eventType;
		this.message = message;
	}

	/**
	 * Gets the event type of this event.
	 * @return The event type of this event.
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * Gets the message of this event.
	 * @return The message of this event.
	 */
	public T getMessage() {
		return message;
	}
}