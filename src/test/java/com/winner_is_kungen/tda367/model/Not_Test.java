package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.NotGate;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test for the functionality of the Not gate
 * This test also tests the functionality of the Component class although that might change in later iterations
 */
public class Not_Test {

		private Component A;
		private Component B;
		private Component C;

		private Output output;

		// Helper function to clear all input flags on components between updates
		// Handled by the Blueprint class otherwise
		private void clearFlags(){
			A.clearInputFlags();
			B.clearInputFlags();
			C.clearInputFlags();
			output.clearInputFlags();
		}

		@Before
		public void prepare(){
			// Create a couple of not gates for testing purposes
			A = new NotGate(0);
			B = new NotGate(1);
			C = new NotGate(2);

			// Create an output for checking resulting values
			output = new Output(-1,1);
		}

		@Test
		@DisplayName("Test if not(true) == false && not(false) == true")
		public void testLogic(){
			// Add listener to A's output and set A's input to true
			A.addListener(output,0,0);
			A.update(true,0);

			// Check if not(true) is false
			assertFalse(output.getChannel(0));

			// Allows inputs to take in new values
			clearFlags();

			// Check if not(false) is true
			A.update(false,0);
			assertTrue(output.getChannel(0));
		}

		@Test
		@DisplayName("Test if chaining of two components is functional")
		public void chain2Logic(){
			A.addListener(B,0,0);
			B.addListener(output,0,0);

			A.update(true,0);
			assertTrue(output.getChannel(0));

			clearFlags();

			A.update(false,0);
			assertFalse(output.getChannel(0));
		}

		@Test
		@DisplayName("Test if channing of three components is functional")
		public void chain3Logic(){
			A.addListener(B,0,0);
			B.addListener(C,0,0);
			C.addListener(output,0,0);

			A.update(true,0);
			assertFalse(output.getChannel(0));

			clearFlags();

			A.update(false,0);
			assertTrue(output.getChannel(0));
		}

		@Test
		@DisplayName("Test if swapping of inputs on a Component is functional")
		public void ChangingInputs(){

			A.addListener(C,0,0);
			C.addListener(output,0,0);

			A.update(true,0);
			assertTrue(output.getChannel(0));

			clearFlags();

			// Change from A -> C -> Output to B -> C -> Output
			A.removeListener(C,0,0);
			B.addListener(C,0,0);

			B.update(false,0);

			clearFlags();

			assertFalse(output.getChannel(0));
			A.update(true,0);

			clearFlags();

			assertFalse(output.getChannel(0));
		}
}
