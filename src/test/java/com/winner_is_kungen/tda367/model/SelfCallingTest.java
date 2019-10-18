package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.AndGate;
import com.winner_is_kungen.tda367.model.LogicGates.NotGate;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class SelfCallingTest {
	private Blueprint bp = new Blueprint();
	private Component A = new AndGate("1", 2);
	private Component B = new AndGate("2", 2);
	private Component C = new NotGate("3");
	private Component D = new NotGate("4");
	private Output out = new Output("5",2);

	private String newUpdateID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Create a SR flip and validate output to test self connected connected
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
		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), false, 1);


		// Getting value of state
		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), true, 1);
		assertFalse(out.getChannel(0));


		// Setting value of next state to 1
		A.update(newUpdateID(), false, 1);
		B.update(newUpdateID(), true, 1);


		A.update(newUpdateID(), true, 1);
		B.update(newUpdateID(), true, 1);

		assertTrue(out.getChannel(0));
	}
}
