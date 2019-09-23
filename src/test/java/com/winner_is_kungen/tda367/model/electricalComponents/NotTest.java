package com.winner_is_kungen.tda367.model.electricalComponents;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.ElectricalComponent;
import com.winner_is_kungen.tda367.model.Signal;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class NotTest {
	private Switch input;
	private ElectricalComponent not;

	@Before
	public void beforeEach() {
		Blueprint blueprint = new Blueprint();
		input = new Switch(UUID.randomUUID().toString());
		not = new Not(UUID.randomUUID().toString());

		blueprint.addComponent(input);
		blueprint.addComponent(not);

		blueprint.connect(input, 0, not, 0);
	}

	@Test
	public void trueToFalse() {
		input.setValue(true);
		assertFalse("A not with input true should output false", not.getOutput(0).getValue());
	}
	@Test
	public void falseToTrue() {
		input.setValue(false);
		assertTrue("A not with input false should output true", not.getOutput(0).getValue());
	}
}