package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;
import com.winner_is_kungen.tda367.model.LogicGates.Input;
import com.winner_is_kungen.tda367.model.LogicGates.Output;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Input_Test {

	private Output o;

	@Before
	public void beforeEach(){
		o = new Output("Output");
	}

	@Test
	public void InputOnOffTest(){
		Input i = new Input("INPUT");
		i.addListener(o, 0, 0);
		assertFalse(o.getInputValue());
		i.switchState();
		assertTrue(o.getInputValue());
	}

	@Test
	public void DoubleInputTest(){
		Input i1 = new Input("INPUT1");
		Input i2 = new Input("INPUT2");
		Component a = ComponentFactory.createComponent("AND");
		i1.addListener(a, 0, 0);
		i2.addListener(a, 1, 0);
		a.addListener(o, 0, 0);
		assertFalse(o.getInputValue());

		i1.switchState();
		assertFalse(o.getInputValue());

		i2.switchState();
		assertTrue(o.getInputValue());
	}

	@Test
	public void TestState(){
		Input i = new Input("INPUT");
		assertFalse(i.getState());
		i.switchState();
		assertTrue(i.getState());
	}

}
