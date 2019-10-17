package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import com.winner_is_kungen.tda367.model.LogicGates.Input;
import org.junit.Test;

import static org.junit.Assert.*;

public class Input_Test {

	private Output o = new Output("Output", 4);

	@Test
	public void InputOnOffTest(){
		Input i = new Input("INPUT");
		i.addListener(o, 0, 0);
		assertFalse(o.getChannel(0));
		i.clearInputFlags();
		o.clearInputFlags();
		i.switchState();
		assertTrue(o.getChannel(0));
	}

	@Test
	public void DoubleInputTest(){
		Input i1 = new Input("INPUT1");
		Input i2 = new Input("INPUT2");
		Component a = ComponentFactory.createComponent("AND");
		i1.addListener(a, 0, 0);
		i2.addListener(a, 1, 0);
		a.addListener(o, 2, 0);
		assertFalse(o.getChannel(2));

		i1.clearInputFlags();
		i2.clearInputFlags();
		a.clearInputFlags();
		o.clearInputFlags();
		i1.switchState();
		assertFalse(o.getChannel(2));

		i1.clearInputFlags();
		i2.clearInputFlags();
		a.clearInputFlags();
		o.clearInputFlags();
		i2.switchState();
		assertTrue(o.getChannel(2));
	}

	@Test
	public void TestState(){
		Input i = new Input("INPUT");
		assertFalse(i.getState());
		i.switchState();
		assertTrue(i.getState());
	}

}
