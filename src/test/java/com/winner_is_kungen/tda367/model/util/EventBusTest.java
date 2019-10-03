package com.winner_is_kungen.tda367.model.util;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class EventBusTest {
	private static final String eventA = "eventA";
	private static final String eventB = "eventB";
	private static final String eventC = "eventC";
	/** Unused event type. */
	private static final String eventD = "eventD";

	private EventBus eventBus;

	@Before
	public void beforeEach() {
		eventBus = new EventBus(eventA, eventB, eventC);
	}

	/**
	 * Tests that valid listeners can be added.
	 * Both as lambda expressions and as implementations of the interface.
	 */
	@Test
	public void addValidListeners() {
		eventBus.addListener(eventA, event -> { });
		eventBus.addListener(
				eventB,
				new IEventBusListener() {
					@Override
					public void react(EventBusEvent event) { }
				}
		);

		// Shouldn't have thrown any errors
	}

	/**
	 * Test that an error is thrown an attempt to listen to an unavailable event type is made.
	 */
	@Test
	public void addInvalidListeners() {
		try {
			eventBus.addListener(eventD, event -> { });
			fail("Expects addListener to throw an error if the eventType provided isn't available in the EventBus.");
		}
		catch (IllegalArgumentException ignored) { }
	}

	/**
	 * Test that listeners are notified when an event is triggered.
	 */
	@Test
	public void triggerEvent() {
		AtomicBoolean listenerHasBeenCalled = new AtomicBoolean(false);

		eventBus.addListener(
				eventA,
				event -> {
					listenerHasBeenCalled.set(true);
				}
		);
		eventBus.triggerEvent(eventA);

		assertTrue("After an event with a registered listener has been triggered, the listener should have been called.", listenerHasBeenCalled.get());
	}

	/**
	 * Test that listeners get the message when an event is triggered.
	 */
	@Test
	public void triggerEventWithMessage() {
		AtomicBoolean listenerHasBeenCalled = new AtomicBoolean(false);
		Object message = "Hello World!";

		eventBus.addListener(
				eventA,
				event -> {
					assertEquals("Listeners should receive the object sent as message.", message, event.getMessage());
					listenerHasBeenCalled.set(true);
				}
		);
		eventBus.triggerEvent(eventA, message);

		assertTrue("After an event with a registered listener has been triggered, the listener should have been called.", listenerHasBeenCalled.get());
	}

	/**
	 * Tests that removed listeners aren't triggered by the event they previously listened to.
	 */
	@Test
	public void removeValidListener() {
		AtomicBoolean listenerHasBeenCalled = new AtomicBoolean(false);
		IEventBusListener listener = event -> {
			listenerHasBeenCalled.set(true);
		};

		eventBus.addListener(eventA, listener);
		eventBus.removeListener(eventA, listener);

		// Shouldn't have thrown any errors

		eventBus.triggerEvent(eventA);

		assertFalse("After an event with a registered and then unregistered listener has been triggered, the listener should not have been called.", listenerHasBeenCalled.get());
	}

	/**
	 * Test that an error is thrown an attempt to remove a listener from an unavailable event type is made.
	 */
	@Test
	public void removeInvalidListener() {
		try {
			// Weather or not the listener were added to a valid event type before should not matter
			eventBus.removeListener(eventD, event -> { });
			fail("Expects removeListener to throw an error if the eventType provided isn't available in the EventBus.");
		}
		catch (IllegalArgumentException ignored) { }
	}
}