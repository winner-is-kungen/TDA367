package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.Output;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class OUTPUT_Test {
	private Output output;

	@Before
	public void beforeEach() {
		output = new Output("0");
	}

	@Test
	public void initLow() {
		assertFalse("The Output component should start as false.", output.getInputValue());
	}

	@Test
	public void reactToInput() {
		output.update(true, 0);
		assertTrue("The Output component should change with its input.", output.getInputValue());
		output.update(false, 0);
		assertFalse("The Output component should change with its input.", output.getInputValue());
		output.update(true, 0);
		assertTrue("The Output component should change with its input.", output.getInputValue());
	}

	@Test
	public void shouldSendChangeEvents() {
		AtomicBoolean listenerHasBeenCalled = new AtomicBoolean(false);

		output.getEventBus().addListener(
			Output.changeEvent,
			event -> {
				listenerHasBeenCalled.set(true);
			}
		);

		output.update(true, 0);

		assertTrue("The Output component should update its listeners when it receives an update.", listenerHasBeenCalled.get());
	}
}