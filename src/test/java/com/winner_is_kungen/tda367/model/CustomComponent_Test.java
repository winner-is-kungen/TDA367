package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomComponent_Test {
	private CustomComponent customComponent;

	/**
	 * Tests the `CustomComponent`s functionality by building a simple and gate.
	 */
	@Test
	public void simpleAnd() {
		// Setup of the custom component
		{
			Blueprint bp = new Blueprint();

			Component i1 = new Input("B");
			bp.addComponent(i1);
			Component i2 = new Input("C");
			bp.addComponent(i2);
			Component a = new AndGate("A", 2);
			bp.addComponent(a);
			Component o = new Output("D");
			bp.addComponent(o);

			bp.connect(i1, 0, a, 0);
			bp.connect(i2, 0, a, 1);
			bp.connect(a, 0, o, 0);

			customComponent = new CustomComponent("1", "and", bp);
		}

		Output output = new Output("D");
		customComponent.addListener(output, 0, 0);

		customComponent.update(true, 0);
		assertFalse(output.getInputValue());

		customComponent.update(true, 1);
		assertTrue(output.getInputValue());

		customComponent.update(false, 1);
		assertFalse(output.getInputValue());
	}

	/**
	 * Tests that the `CustomComponent` functions with more complicated setups too.
	 *
	 * @implNote This is a copy and paste of the `SelfCallingTest.java` test.
	 */
	@Test
	public void complicatedSelfCalling() {
		// Setup of the custom component
		{
			Blueprint bp = new Blueprint();

			Component i1 = new Input("B");
			bp.addComponent(i1);
			Component i2 = new Input("C");
			bp.addComponent(i2);
			Component A = new AndGate("1", 2);
			bp.addComponent(A);
			Component B = new AndGate("2", 2);
			bp.addComponent(B);
			Component C = new NotGate("3");
			bp.addComponent(C);
			Component D = new NotGate("4");
			bp.addComponent(D);
			Component out = new Output("5");
			bp.addComponent(out);

			bp.connect(i1, 0, A, 1);
			bp.connect(i2, 0, B, 1);
			bp.connect(A, 0, C, 0);
			bp.connect(B, 0, D, 0);
			bp.connect(C, 0, B, 0);
			bp.connect(D, 0, A, 0);
			bp.connect(C, 0, out, 0);

			customComponent = new CustomComponent("1", "selfCalling", bp);
		}

		Output output = new Output("D");
		customComponent.addListener(output, 0, 0);

		// Setting value of next state to 0
		customComponent.update(true, 0);
		customComponent.update(false, 1);

		// Getting value of state
		customComponent.update(true, 0);
		customComponent.update(true, 1);
		assertFalse(output.getInputValue());

		// Setting value of next state to 1
		customComponent.update(false, 0);
		customComponent.update(true, 1);

		// Check values of state
		customComponent.update(true, 0);
		customComponent.update(true, 1);
		assertTrue(output.getInputValue());
	}
}