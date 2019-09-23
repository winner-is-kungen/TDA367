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
	Blueprint blueprint;
	private Switch input;
	private ElectricalComponent not;

	@Before
	public void beforeEach() {
		blueprint = new Blueprint();
		input = new Switch(UUID.randomUUID().toString());
		not = new Not(UUID.randomUUID().toString());

		blueprint.addComponent(input);
		blueprint.addComponent(not);

		blueprint.connect(input, 0, not, 0);
	}

	@Test
	public void setNewInput() {
		input.setValue(false);

		Signal mockSwitch2 = new Signal(UUID.randomUUID().toString(), 0, () -> true);
		not.setInput(0, mockSwitch2);

		assertFalse("Should only read inputs from the latest set input", not.getOutput(0).getValue());
	}

	@Test
	public void notifyOutputs() {
		input.setValue(true);
		String updateID = UUID.randomUUID().toString();

		AtomicBoolean outputHasBeenCalled = new AtomicBoolean(false);
		not.getOutput(0).addListener(id -> {
			assertEquals("The update ID should be passed along unchanged", updateID, id);
			outputHasBeenCalled.set(true);
		});

		input.getOutput(0).update(updateID);
		assertTrue("The outputs should have been notified", outputHasBeenCalled.get());
	}

	@Test
	public void chaining() {
		ElectricalComponent not2 = new Not(UUID.randomUUID().toString());
		blueprint.addComponent(not2);
		blueprint.connect(not, 0, not2, 0);

		input.setValue(false);

		assertFalse("The second component should be updated immediately", not2.getOutput(0).getValue());

		input.setValue(true);

		assertTrue("The second component should be updated by a change further up the chain", not2.getOutput(0).getValue());
	}

	@Test
	public void simpleCircularFlipFlop() {
		ElectricalComponent or = new Or(UUID.randomUUID().toString(), 2);
		blueprint.addComponent(or);
		blueprint.connect(input, 0, or, 1);

		Switch input2 = new Switch(UUID.randomUUID().toString());
		input2.setValue(false);
		blueprint.addComponent(input2);

		ElectricalComponent and = new And(UUID.randomUUID().toString(), 2);
		blueprint.addComponent(and);
		blueprint.connect(or, 0, and, 0);
		blueprint.connect(input2, 0, and, 1);

		or.setInput(0, and.getOutput(0));

		// Reaching here without throwing errors is a test in itself. Proves
		// that the components do not update them self even if they are
		// connected to them self (directly or indirectly).

		assertFalse("The initial value should be false", and.getOutput(0).getValue());

		input2.setValue(true);

		assertFalse("false && true should still be false", and.getOutput(0).getValue());

		input.setValue(true);

		assertTrue("The value should now be true", and.getOutput(0).getValue());

		input.setValue(false);

		assertTrue("The value should still be true", and.getOutput(0).getValue());
	}
}