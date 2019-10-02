package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ComponentFactoryTest {

	private Output output = new Output(-1, 4);

	@Test
	public void testCreateNot(){
		Component ng = ComponentFactory.createComponent("NOT");
		ng.addListener(output, 0, 0);
		ng.update(true, 0);
		assertFalse(output.getChannel(0));
		ng.update(false, 0);
		assertTrue(output.getChannel(0));
	}

	@Test
	public void testCreateAndAnd(){
		Component and1 = ComponentFactory.createComponent("AND");
		Component and2 = ComponentFactory.createComponent("AND");
		and1.addListener(and2, 0, 0);
		and2.addListener(output, 1, 0);
		and1.update(true, 0);
		and1.update(true, 1);
		and2.update(false, 1);
		assertFalse(output.getChannel(1));
		and2.update(true,1);
		assertTrue(output.getChannel(1));
	}

}
