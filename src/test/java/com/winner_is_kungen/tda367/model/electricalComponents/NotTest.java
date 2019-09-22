package com.winner_is_kungen.tda367.model.electricalComponents;

import com.winner_is_kungen.tda367.model.ElectricalComponent;
import com.winner_is_kungen.tda367.model.Signal;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class NotTest {
	private boolean mockSwitchValue;
	private Signal mockSwitch;
	private ElectricalComponent not;

	@Before
	public void beforeEach() {
		mockSwitch = new Signal(UUID.randomUUID().toString(), 0, this::mockSwitchOutput);
		not = new Not(UUID.randomUUID().toString());
		not.setInput(0, mockSwitch);
	}

	public boolean mockSwitchOutput() {
		return mockSwitchValue;
	}

	@Test
	public void trueToFalse() {
		mockSwitchValue = true;
		mockSwitch.update(UUID.randomUUID().toString());
		assertFalse("A not with input true should output false", not.getOutput(0).getValue());
	}
	@Test
	public void falseToTrue() {
		mockSwitchValue = false;
		mockSwitch.update(UUID.randomUUID().toString());
		assertTrue("A not with input false should output true", not.getOutput(0).getValue());
	}
}