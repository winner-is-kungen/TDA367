package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class SimulationManagerTest {
	private Blueprint bp = new Blueprint();
	private Component A = new AndGate(1);
	private Component B = new AndGate(2);
	private Component C = new NotGate(3);
	private Component D = new NotGate(4);
	private Output out = new Output(-1,2);

	@Test
	public void testSimulationManagerID(){
		int simulationID = bp.getSimulationID();
		bp.incSimulationID();
		assertNotEquals(simulationID,bp.getSimulationID());
	}

	/**
	 * Create a SR flip and validate output to test if SimulationManager can allow self-calling Components
	 */
	@Test
	public void srFlip(){
		bp.addComponent(A);
		bp.addComponent(B);
		bp.addComponent(C);
		bp.addComponent(D);
		bp.addComponent(out);

		// Creating an SR Flip
		bp.connect(A,0,C,0);
		bp.connect(B,0,D,0);
		bp.connect(C,0,B,0);
		bp.connect(D,0,A,0);
		bp.connect(C,0,out,0);
		bp.connect(D,0,out,1);

		// Setting value of next state to 0
		A.update(true,1);
		B.update(false,1);

		bp.incSimulationID(); // Go to next step in simulation;

		// Getting value of state
		A.update(true,1);
		B.update(true,1);
		assertFalse(out.getChannel(0));

		bp.incSimulationID();

		// Setting value of next state to 1
		A.update(false,1);
		B.update(true,1);

		bp.incSimulationID();

		A.update(true,1);
		B.update(true,1);

		assertTrue(out.getChannel(0));
	}
}
