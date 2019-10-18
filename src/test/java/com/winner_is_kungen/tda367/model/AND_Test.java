package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class AND_Test {

	// Create a couple of AND gates for testing purposes
	private Component A = new AndGate("1", 2);
	private Component B = new AndGate("2", 2);
	private Component C = new AndGate("3", 3);

	// Create an output with 4 inputs for checking resulting values
	private Output output = new Output("4", 4);

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
		assertTrue(output.getChannel(0));

		// Check if True And False is false
		A.update(newUpdateID(), false, 1);
		assertFalse(output.getChannel(0));

		// Check if False And False is false
		A.update(newUpdateID(), false, 0);
		assertFalse(output.getChannel(0));

		// Check if False And True is false
		A.update(newUpdateID(), true, 1);
		assertFalse(output.getChannel(0));

	}

	/**
	 * Test if chaining of two components is functional.
	 */
	@Test
	public void chain2Logic() {
		A.addListener(B, 0, 0);
		B.addListener(output, 1, 0);


		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), true, 1);

		// Test (True And True) And True == True
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

	/**
	 * Test if chaining of three components is functional.
	 */
	@Test
	public void chain3Logic() {
		A.addListener(B, 0, 0);
		B.addListener(C, 0, 0);
		C.addListener(output, 2, 0);


		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), true, 1);
		C.update(newUpdateID(), true, 1);
		C.update(newUpdateID(), true, 2);
		assertTrue(output.getChannel(2));

		A.update(newUpdateID(), false, 0);
		assertFalse(output.getChannel(2));
	}

	/**
	 * Testing a 3 input AND-gate.
	 */
	@Test
	public void ChangingInputs() {
		A = new AndGate("5", 3);
		A.addListener(output, 3, 0);


		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), false, 2);
		assertFalse(output.getChannel(3));

		A.update(newUpdateID(), true, 0);
		assertFalse(output.getChannel(3));

		A.update(newUpdateID(), false, 0);
		A.update(newUpdateID(), true, 1);
		assertFalse(output.getChannel(3));

		A.update(newUpdateID(), false, 1);
		A.update(newUpdateID(), true, 2);
		assertFalse(output.getChannel(3));

		A.update(newUpdateID(), true, 0);
		A.update(newUpdateID(), true, 1);
		assertTrue(output.getChannel(3));
	}
}
