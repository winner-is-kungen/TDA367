package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test for the functionality of the Not gate
 * This test also tests the functionality of the Component class although that might change in later iterations
 */
public class Not_Test {

		// Create a couple of not gates for testing purposes
		private Component A = new NotGate(1);
		private Component B = new NotGate(2);
		private Component C = new NotGate(3);

		// Create an output with 3 inputs for checking resulting values
		private Output output = new Output(-1, 3);

		@Test
		@DisplayName("Test if not(true) == false && not(false) == true")
		public void testLogic(){
			// Add listener to A's output and set A's input to true
			A.addListener(output,0,0);
			A.update(true,0);

			// Check if not(true) is false
			assertFalse(output.getChannel(0));

			// Check if not(false) is true
			A.update(false,0);
			assertTrue(output.getChannel(0));
		}

		@Test
		@DisplayName("Test if chaining of two components is functional")
		public void chain2Logic(){
			A.addListener(B,0,0);
			B.addListener(output,1,0);

			A.update(true,0);
			assertTrue(output.getChannel(1));

			A.update(false,0);
			assertFalse(output.getChannel(1));
		}

		@Test
		@DisplayName("Test if channing of three components is functional")
		public void chain3Logic(){
			A.addListener(B,0,0);
			B.addListener(C,0,0);
			C.addListener(output,2,0);

			A.update(true,0);
			assertFalse(output.getChannel(2));

			A.update(false,0);
			assertTrue(output.getChannel(2));
		}

		@Test
		@DisplayName("Test if swapping of inputs on a Component is functional")
		public void ChangingInputs(){
			A = new NotGate(1);
			B = new NotGate(2);
			C = new NotGate(3);
			Output output = new Output(-2, 1);

			A.addListener(C,0,0);
			C.addListener(output,0,0);

			A.update(true,0);
			assertTrue(output.getChannel(0));

			// Change from A -> C -> Output to B -> C -> Output
			A.removeListener(C,0,0);
			B.addListener(C,0,0);

			B.update(false,0);
			assertFalse(output.getChannel(0));
			A.update(true,0);
			assertFalse(output.getChannel(0));
		}
}
