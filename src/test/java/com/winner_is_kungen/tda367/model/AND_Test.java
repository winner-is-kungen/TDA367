package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.OrGate;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AND_Test {

	// Create a couple of AND gates for testing purposes
	private Component A = new AndGate(1, 2);
	private Component B = new AndGate(2, 2);
	private Component C = new AndGate(3, 3);

	// Create an output with 4 inputs for checking resulting values
	private Output output = new Output(-1, 4);

	@Test
	@DisplayName("Test if (true && true == true) && (true && false == false) && (false && false == false)")
	public void testLogic(){
		// Add listener to A's output and set A's inputs to true
		A.addListener(output,0, 0);
		A.update(true,0);
		A.update(true, 1);

		// Check if trueOrTrue is true
		assertTrue(output.getChannel(0));

		// Check if trueOrFalse is false
		A.update(false,1);
		assertFalse(output.getChannel(0));

		// Check if falseOrFalse is false
		A.update(false,0);
		assertFalse(output.getChannel(0));

		// Check if falseOrTrue is false
		A.update(true,1);
		assertFalse(output.getChannel(0));

	}

	@Test
	@DisplayName("Test if chaining of two components is functional")
	public void chain2Logic(){
		A.addListener(B,0, 0);
		B.addListener(output,1, 0);

		A.update(true,0);
		A.update(true, 1);
		B.update(true, 1);
		assertTrue(output.getChannel(1));

		A.removeListener(B, 0, 0);
		B.update(false,0);
		assertFalse(output.getChannel(1));

		A.addListener(B, 0, 0);
		assertFalse(output.getChannel(1));

		A.update(true, 0);
		assertTrue(output.getChannel(1));

		A.update(false, 0);
		A.update(false, 1);
		assertFalse(output.getChannel(1));
	}

	//Comment: Update always needs to be called when re-coupling. Feature or fix?

	@Test
	@DisplayName("Test if channing of three components is functional")
	public void chain3Logic(){
		A.addListener(B,0, 0);
		B.addListener(C,0, 0);
		C.addListener(output,2, 0);

		A.update(true,0);
		A.update(true,1);
		B.update(true,1);
		C.update(true,1);
		C.update(true, 2);
		assertTrue(output.getChannel(2));

		A.update(false,0);
		assertFalse(output.getChannel(2));
	}

	@Test
	@DisplayName("Testing a 3 input AND-gate")
	public void ChangingInputs(){
		A = new AndGate(1, 3);
		A.addListener(output, 3, 0);

		A.update(false, 0);
		A.update(false, 1);
		A.update(false, 2);
		assertFalse(output.getChannel(3));

		A.update(true, 0);
		assertFalse(output.getChannel(3));

		A.update(false, 0);
		A.update(true, 1);
		assertFalse(output.getChannel(3));

		A.update(false, 1);
		A.update(true, 2);
		assertFalse(output.getChannel(3));

		A.update(true, 0);
		A.update(true, 1);
		assertTrue(output.getChannel(3));
	}
}
