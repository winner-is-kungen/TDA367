package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.electricalComponents.*;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Tests general functionality of ElectricalComponents using the Not component.
 */
public class ElectricalComponentTest {
	private boolean mockSwitchValue;
	private Signal mockSwitch;
	private ElectricalComponent not;

	@Before
	public void beforeEach() {
		mockSwitchValue = false;
		mockSwitch = new Signal(UUID.randomUUID().toString(), 0, this::mockSwitchOutput);
		not = new Not(UUID.randomUUID().toString());
		not.setInput(0, mockSwitch);
	}

	public boolean mockSwitchOutput() {
		return mockSwitchValue;
	}

	@Test
	public void setNewInput() {
		mockSwitchValue = false;
		mockSwitch.update(UUID.randomUUID().toString());

		Signal mockSwitch2 = new Signal(UUID.randomUUID().toString(), 0, () -> true);
		not.setInput(0, mockSwitch2);

		assertFalse("Should only read inputs from the latest set input", not.getOutput(0).getValue());
	}

	@Test
	public void notifyOutputs() {
		String updateID = UUID.randomUUID().toString();
		mockSwitchValue = true;

		AtomicBoolean outputHasBeenCalled = new AtomicBoolean(false);
		not.getOutput(0).addListener(id -> {
			assertEquals("The update ID should be passed along unchanged", updateID, id);
			outputHasBeenCalled.set(true);
		});

		mockSwitch.update(updateID);
		assertTrue("The outputs should have been notified", outputHasBeenCalled.get());
	}

	@Test
	public void chaining() {
		ElectricalComponent not2 = new Not(UUID.randomUUID().toString());
		not2.setInput(0, not.getOutput(0));

		assertFalse("The second component should be updated immediately", not2.getOutput(0).getValue());

		mockSwitchValue = true;
		mockSwitch.update(UUID.randomUUID().toString());

		assertTrue("The second component should be updated by a change further up the chain", not2.getOutput(0).getValue());
	}

	@Test
	public void simpleCircularFlipFlop() {
		ElectricalComponent or = new Or(UUID.randomUUID().toString(), 2);
		or.setInput(1, mockSwitch);

		// Hack for making an internal mockSwitch. Does not affect testing.
		var mockSwitch2Value = new Object() {
			boolean value = false;
		};
		Signal mockSwitch2 = new Signal(UUID.randomUUID().toString(), 0, () -> mockSwitch2Value.value);

		ElectricalComponent and = new And(UUID.randomUUID().toString(), 2);
		and.setInput(0, or.getOutput(0));
		and.setInput(1, mockSwitch2);

		or.setInput(0, and.getOutput(0));

		// Reaching here without throwing errors is a test in itself. Proves
		// that the components do not update them self even if they are
		// connected to them self (directly or indirectly).

		assertFalse("The initial value should be false", and.getOutput(0).getValue());

		mockSwitch2Value.value = true;
		mockSwitch2.update(UUID.randomUUID().toString());

		assertFalse("false && true should still be false", and.getOutput(0).getValue());

		mockSwitchValue = true;
		mockSwitch.update(UUID.randomUUID().toString());

		assertTrue("The value should now be true", and.getOutput(0).getValue());

		mockSwitchValue = false;
		mockSwitch.update(UUID.randomUUID().toString());

		assertTrue("The value should still be true", and.getOutput(0).getValue());
	}
}