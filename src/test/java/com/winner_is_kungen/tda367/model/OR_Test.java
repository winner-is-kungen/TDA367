package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import com.winner_is_kungen.tda367.model.Output;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class OR_Test {

	// Create a couple of OR gates for testing purposes
	private Component A = new OrGate("1", 2);
	private Component B = new OrGate("2", 2);
	private Component C = new OrGate("3", 3);

	// Create an output with 4 inputs for checking resulting values
	private Output output = new Output("4", 4);

	private String newUpdateID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Test if (true || true == true) && (true || false == true) && (false || false == false).
	 */
	@Test
	public void testLogic() {
		// Add listener to A's output and set A's inputs to true
		A.addListener(output, 0, 0);

		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		// Check if trueOrTrue is true
		assertTrue(output.getChannel(0));

		// Check if trueOrFalse is true

		A.update(newUpdateID(), false, 1);
		assertTrue(output.getChannel(0));

		// Check if falseOrFalse is false

		A.update(newUpdateID(), false, 0);
		assertFalse(output.getChannel(0));

		// Check if falseOrTrue is true

		A.update(newUpdateID(), true, 1);
		assertTrue(output.getChannel(0));

	}

	/**
	 * Test if chaining of two components is functional
	 */
	@Test
	public void chain2Logic() {
		A.addListener(B, 0, 0);
		B.addListener(output, 1, 0);

		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), false, 1);
		assertTrue(output.getChannel(1));

		A.removeListener(B, 0, 0);

		B.update(newUpdateID(), false, 0);
		assertFalse(output.getChannel(1));

		A.addListener(B, 0, 0);
		assertTrue(output.getChannel(1));

		A.update(newUpdateID(), true, 0);
		assertTrue(output.getChannel(1));

		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), false, 1);
		assertFalse(output.getChannel(1));
	}

	//Comment: Update always needs to be called when re-coupling. Feature or fix?

	/**
	 * Test if chaining of three components is functional.
	 */
	@Test
	public void chain3Logic() {
		A.addListener(B, 0, 0);
		B.addListener(C, 0, 0);
		C.addListener(output, 2, 0);


		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), false, 1);
		B.update(newUpdateID(), false, 1);
		C.update(newUpdateID(), false, 1);
		assertTrue(output.getChannel(2));


		A.update(newUpdateID(), false, 0);
		assertFalse(output.getChannel(2));
	}

	/**
	 * Testing a 3 input OR-gate.
	 */
	@Test
	public void ChangingInputs() {
		A = new OrGate("1", 3);
		A.addListener(output, 3, 0);


		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), false, 2);
		assertFalse(output.getChannel(3));

		A.update(newUpdateID(), true, 0);
		assertTrue(output.getChannel(3));

		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), true, 1);
		assertTrue(output.getChannel(3));

		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), true, 2);
		assertTrue(output.getChannel(3));

		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		assertTrue(output.getChannel(3));

		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), false, 2);
		assertFalse(output.getChannel(3));
	}
}
