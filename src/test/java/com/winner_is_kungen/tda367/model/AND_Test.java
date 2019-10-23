package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import com.winner_is_kungen.tda367.model.LogicGates.Output;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class AND_Test {

	// Create a couple of AND gates for testing purposes
	private Component A;
	private Component B;
	private Component C;

	// Create an output with 4 inputs for checking resulting values
	private Output output;

	@Before
	public void beforeEach() {
		A = new AndGate("1", 2);
		B = new AndGate("2", 2);
		C = new AndGate("3", 3);
		output = new Output("4");
	}

	private String newUpdateID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Test if (true && true == true) && (true && false == false) && (false && false == false).
	 */
	@Test
	public void testLogic() {
		// Add listener to A's output and set A's inputs to true
		A.addListener(output, 0, 0);

		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);

		// Check if True And True is true
		assertTrue(output.getInputValue());

		// Check if True And False is false
		A.update(newUpdateID(), false, 1);
		assertFalse(output.getInputValue());

		// Check if False And False is false
		A.update(newUpdateID(), false, 0);
		assertFalse(output.getInputValue());

		// Check if False And True is false
		A.update(newUpdateID(), true, 1);
		assertFalse(output.getInputValue());

	}

	/**
	 * Test if chaining of two components is functional.
	 */
	@Test
	public void chain2Logic() {
		A.addListener(B, 0, 0);
		B.addListener(output, 0, 0);


		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), true, 1);

		// Test (True And True) And True == True
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

	/**
	 * Test if chaining of three components is functional.
	 */
	@Test
	public void chain3Logic() {
		A.addListener(B, 0, 0);
		B.addListener(C, 0, 0);
		C.addListener(output, 0, 0);


		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), true, 1);
		C.update(newUpdateID(), true, 1);
		C.update(newUpdateID(), true, 2);
		assertTrue(output.getInputValue());

		A.update(newUpdateID(), false, 0);
		assertFalse(output.getInputValue());
	}

	/**
	 * Testing a 3 input AND-gate.
	 */
	@Test
	public void ChangingInputs() {
		A = new AndGate("5", 3);
		A.addListener(output, 0, 0);


		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), false, 2);
		assertFalse(output.getInputValue());

		A.update(newUpdateID(), true, 0);
		assertFalse(output.getInputValue());

		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), true, 1);
		assertFalse(output.getInputValue());

		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), true, 2);
		assertFalse(output.getInputValue());

		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		assertTrue(output.getInputValue());
	}
}
