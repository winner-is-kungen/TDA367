package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.LogicGates.Output;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A test for the functionality of the Not gate
 * This test also tests the functionality of the Component class although that might change in later iterations
 */
public class Not_Test {
	// Create a couple of not gates for testing purposes
	private Component A;
	private Component B;
	private Component C;

	private Output output;

	private String newUpdateID() {
		return UUID.randomUUID().toString();
	}

	@Before
	public void beforeEach() {
		A = ComponentFactory.createComponent("NOT");
		B = new NotGate("2");
		C = new NotGate("3");

		output = new Output("-1");
	}

	/**
	 * Test if not(true) == false && not(false) == true.
	 */
	@Test
	public void testLogic() {
		// Add listener to A's output and set A's input to true
		A.addListener(output, 0, 0);


		A.update(newUpdateID(), true, 0);

		// Check if not(true) is false
		assertFalse(output.getInputValue());


		// Check if not(false) is true
		A.update(newUpdateID(), false, 0);
		assertTrue(output.getInputValue());
	}

	/**
	 * Test if chaining of two components is functional.
	 */
	@Test
	public void chain2Logic() {
		A.addListener(B, 0, 0);
		B.addListener(output, 0, 0);


		A.update(newUpdateID(), true, 0);
		assertTrue(output.getInputValue());


		A.update(newUpdateID(), false, 0);
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
		assertFalse(output.getInputValue());


		A.update(newUpdateID(), false, 0);
		assertTrue(output.getInputValue());
	}

	/**
	 * Test if swapping of inputs on a Component is functional.
	 */
	@Test
	public void ChangingInputs() {
		A.addListener(C, 0, 0);
		C.addListener(output, 0, 0);


		A.update(newUpdateID(), true, 0);


		B.update(newUpdateID(), false, 0);
		assertTrue(output.getInputValue());

		// Change from A -> C -> Output to B -> C -> Output
		A.removeListener(C, 0, 0);
		B.addListener(C, 0, 0);
		assertFalse(output.getInputValue());


		A.update(newUpdateID(), true, 0);
		assertFalse(output.getInputValue());
	}
}
