package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import com.winner_is_kungen.tda367.model.LogicGates.Output;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class OR_Test {

	// Create a couple of OR gates for testing purposes
	private Component A;
	private Component B;
	private Component C;

	// Create an output with 4 inputs for checking resulting values
	private Output output;

	@Before
	public void beforeEach() {
		A = new OrGate("1", 2);
		B = new OrGate("2", 2);
		C = new OrGate("3", 3);
		output = new Output("4");
	}

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
		assertTrue(output.getInputValue());

		// Check if trueOrFalse is true

		A.update(newUpdateID(), false, 1);
		assertTrue(output.getInputValue());

		// Check if falseOrFalse is false

		A.update(newUpdateID(), false, 0);
		assertFalse(output.getInputValue());

		// Check if falseOrTrue is true

		A.update(newUpdateID(), true, 1);
		assertTrue(output.getInputValue());

	}

	/**
	 * Test if chaining of two components is functional
	 */
	@Test
	public void chain2Logic() {
		A.addListener(B, 0, 0);
		B.addListener(output, 0, 0);

		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), false, 1);
		assertTrue(output.getInputValue());

		A.removeListener(B, 0, 0);

		B.update(newUpdateID(), false, 0);
		assertFalse(output.getInputValue());

		A.addListener(B, 0, 0);
		assertTrue(output.getInputValue());

		A.update(newUpdateID(), true, 0);
		assertTrue(output.getInputValue());

		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), false, 1);
		assertFalse(output.getInputValue());
	}

	//Comment: Update always needs to be called when re-coupling. Feature or fix?

	/**
	 * Test if chaining of three components is functional.
	 */
	@Test
	public void chain3Logic() {
		A.addListener(B, 0, 0);
		B.addListener(C, 0, 0);
		C.addListener(output, 0, 0);


		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), false, 1);
		B.update(newUpdateID(), false, 1);
		C.update(newUpdateID(), false, 1);
		assertTrue(output.getInputValue());


		A.update(newUpdateID(), false, 0);
		assertFalse(output.getInputValue());
	}

	/**
	 * Testing a 3 input OR-gate.
	 */
	@Test
	public void ChangingInputs() {
		A = new OrGate("1", 3);
		A.addListener(output, 0, 0);


		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), false, 2);
		assertFalse(output.getInputValue());

		A.update(newUpdateID(), true, 0);
		assertTrue(output.getInputValue());

		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), true, 1);
		assertTrue(output.getInputValue());

		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), true, 2);
		assertTrue(output.getInputValue());

		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		assertTrue(output.getInputValue());

		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), false, 2);
		assertFalse(output.getInputValue());
	}
}
