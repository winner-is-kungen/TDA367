package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ComponentFactoryTest {

	//Test for creating components using the ComponentFactory

	private Output output = new Output("-1", 4);

	private String newUpdateID() {
		return UUID.randomUUID().toString();
	}

	@Test
	//Test to see if possible to create one NOT-gate and that it functions correctly.
	public void testCreateNot(){
		Component ng = ComponentFactory.createComponent("NOT");
		ng.addListener(output, 0, 0);

		ng.update(newUpdateID(), true, 0);
		assertFalse(output.getChannel(0));


		ng.update(newUpdateID(), false, 0);
		assertTrue(output.getChannel(0));
	}

	//Test to see if possible to create two of the same type of gates (AND-gates) and couple them together.
	@Test
	public void testCreateAndAnd(){
		Component A = ComponentFactory.createComponent("AND");
		Component B = ComponentFactory.createComponent("AND");
		A.addListener(B, 0, 0);
		B.addListener(output, 1, 0);


		A.update(newUpdateID(), true, 0);

		A.update(newUpdateID(), true, 1);


		B.update(newUpdateID(), false, 1);
		assertFalse(output.getChannel(1));


		B.update(newUpdateID(), true, 1);
		assertTrue(output.getChannel(1));
	}

	//Test to see if possible to create one of each gate and connect them to eachother.
	@Test
	public void testCreateTriple(){
		Component A = ComponentFactory.createComponent("NOT");
		Component B = ComponentFactory.createComponent("AND");
		Component C = ComponentFactory.createComponent("OR");

		C.addListener(A, 0, 0);
		A.addListener(B, 0, 0);
		B.addListener(output, 2, 0);


		C.update(newUpdateID(), true, 0);


		C.update(newUpdateID(), false, 1);


		B.update(newUpdateID(), true, 1);

		assertFalse(output.getChannel(2));


		C.update(newUpdateID(), false, 0);

		assertTrue(output.getChannel(2));


	}
}
