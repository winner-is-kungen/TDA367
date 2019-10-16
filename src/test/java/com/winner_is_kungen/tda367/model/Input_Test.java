package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class Input_Test {

	private Output o = new Output("Output", 4);

	@Test
	public void InputOnOffTest(){
		Component i = ComponentFactory.createComponent("INPUT");
		i.addListener(o, 0, 0);
		assertFalse(o.getChannel(0));
		i.clearInputFlags();
		o.clearInputFlags();
		i.update(true,0);
		assertTrue(o.getChannel(0));
	}

	@Test
	public void DoubleInputTest(){
		Component i1 = ComponentFactory.createComponent("INPUT");
		Component i2 = ComponentFactory.createComponent("INPUT");
		Component a = ComponentFactory.createComponent("AND");
		i1.addListener(a, 0, 0);
		i2.addListener(a, 1, 0);
		a.addListener(o, 2, 0);
		assertFalse(o.getChannel(2));

		i1.clearInputFlags();
		i2.clearInputFlags();
		a.clearInputFlags();
		o.clearInputFlags();
		i1.update(true, 0);
		assertFalse(o.getChannel(2));

		i1.clearInputFlags();
		i2.clearInputFlags();
		a.clearInputFlags();
		o.clearInputFlags();
		i2.update(true, 0);
		assertTrue(o.getChannel(2));
	}


}
