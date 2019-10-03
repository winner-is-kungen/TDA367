package com.winner_is_kungen.tda367.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventBus {
	/** A map of event types to lists of event listeners. */
	private final Map<String, List<IEventBusListener>> listeners;

	/**
	 * Creates an Event Bus with a fixed set of event types.
	 * @param eventTypes The event types this Event Bus handles.
	 */
	public EventBus(String... eventTypes) {
		// Creates a Map from strings (eventTypes) to empty lists of event listeners
		listeners = Stream.of(eventTypes).collect(Collectors.toUnmodifiableMap(x -> x, x -> new ArrayList<IEventBusListener>()));
	}

	/**
	 * Adds a listener to the specified type of event.
	 * @param eventType The type of events to listen for.
	 * @param listener  The event listener.
	 */
	public void addListener(String eventType, IEventBusListener listener) {
		if (listeners.containsKey(eventType)) {
			listeners.get(eventType).add(listener);
		}
		else {
			throw new IllegalArgumentException("Can't add listener to unknown event type.");
		}
	}

	/**
	 * Removes an event listener from the specified event type.
	 * @param eventType The type of events the listener listened to.
	 * @param listener  The event listener.
	 */
	public void removeListener(String eventType, IEventBusListener listener) {
		if (listeners.containsKey(eventType)) {
			listeners.get(eventType).remove(listener);
		}
		else {
			throw new IllegalArgumentException("Can't remove listener from unknown event type.");
		}
	}

	/**
	 * Triggers an event without any message.
	 * @param eventType The event type to trigger.
	 */
	public void triggerEvent(String eventType) {
		triggerEvent(eventType, null);
	}
	/**
	 * Triggers an event with the specified event message.
	 * @param eventType The event type to trigger.
	 * @param message   The message to send out.
	 */
	public <T> void triggerEvent(String eventType, T message) {
		if (listeners.containsKey(eventType)) {
			EventBusEvent<T> event = new EventBusEvent<T>(eventType, message);

			listeners.get(eventType).forEach(x -> x.react(event));
		}
		else {
			throw new IllegalArgumentException("Can't trigger event with unknown event type.");
		}
	}
}