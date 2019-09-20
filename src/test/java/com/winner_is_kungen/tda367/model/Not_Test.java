package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Not_Test {
		private Component A = new NotGate(1);
		private Component B = new NotGate(2);
		private Component C = new NotGate(3);
		private Output output = new Output(3);

		@Test
		public void testLogic(){
			A.addListener(output,0);

			A.update(true,0);
			assertFalse(output.getChannel(0));

			A.update(false,0);
			assertTrue(output.getChannel(0));
		}

		@Test
		public void chain2Logic(){
			A.addListener(B,0);
			B.addListener(output,1);

			A.update(true,0);
			assertTrue(output.getChannel(1));

			A.update(false,0);
			assertFalse(output.getChannel(1));
		}

		@Test
		public void chain3Logic(){
			A.addListener(B,0);
			B.addListener(C,0);
			C.addListener(output,2);

			A.update(true,0);
			assertFalse(output.getChannel(2));

			A.update(false,0);
			assertTrue(output.getChannel(2));
		}

		@Test
		public void ChangingInputs(){
			A = new NotGate(1);
			B = new NotGate(2);
			C = new NotGate(3);
			Output output = new Output(1);

			A.addListener(C,0);
			C.addListener(output,0);

			A.update(true,0);
			assertTrue(output.getChannel(0));

			// Change from A -> C -> Output to B -> C -> Output
			A.removeListener(C,0);
			B.addListener(C,0);
			B.update(false,0);
			assertFalse(output.getChannel(0));
			A.update(true,0);
			assertFalse(output.getChannel(0));


		}
}
