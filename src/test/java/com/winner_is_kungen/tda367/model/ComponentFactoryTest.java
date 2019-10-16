package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import com.winner_is_kungen.tda367.model.LogicGates.Output;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentFactoryTest {

	//Test for creating components using the ComponentFactory

	private Output output = new Output("-1");

	@Test
	//Test to see if possible to create one NOT-gate and that it functions correctly.
	public void testCreateNot(){
		Component ng = ComponentFactory.createComponent("NOT");
		ng.addListener(output, 0, 0);
		output.clearInputFlags();
		ng.update(true, 0);
		assertFalse(output.getInputValue());
		output.clearInputFlags();
		ng.clearInputFlags();
		ng.update(false, 0);
		assertTrue(output.getInputValue());
	}

	//Test to see if possible to create two of the same type of gates (AND-gates) and couple them together.
	@Test
	public void testCreateAndAnd(){
		Component A = ComponentFactory.createComponent("AND");
		Component B = ComponentFactory.createComponent("AND");
		A.addListener(B, 0, 0);
		B.addListener(output, 0, 0);
		A.clearInputFlags();
		B.clearInputFlags();
		A.update(true, 0);
		B.clearInputFlags();
		A.update(true, 1);
		B.clearInputFlags();
		output.clearInputFlags();
		B.update(false, 1);
		assertFalse(output.getInputValue());
		A.clearInputFlags();
		B.clearInputFlags();
		output.clearInputFlags();
		B.update(true,1);
		assertTrue(output.getInputValue());
	}

	//Test to see if possible to create one of each gate and connect them to eachother.
	@Test
	public void testCreateTriple(){
		Component A = ComponentFactory.createComponent("NOT");
		Component B = ComponentFactory.createComponent("AND");
		Component C = ComponentFactory.createComponent("OR");

		C.addListener(A, 0, 0);
		A.addListener(B, 0, 0);
		B.addListener(output, 0, 0);

		A.clearInputFlags();
		B.clearInputFlags();
		C.clearInputFlags();
		C.update(true, 0);
		A.clearInputFlags();
		B.clearInputFlags();
		C.clearInputFlags();
		C.update(false, 1);

		B.clearInputFlags();
		output.clearInputFlags();
		B.update(true, 1);

		assertFalse(output.getInputValue());

		A.clearInputFlags();
		B.clearInputFlags();
		C.clearInputFlags();
		output.clearInputFlags();
		C.update(false, 0);

		assertTrue(output.getInputValue());


	}
}
