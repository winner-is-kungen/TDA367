package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.electricalComponents.Not;
import com.winner_is_kungen.tda367.model.electricalComponents.Switch;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class BlueprintTest {
	private Blueprint blueprint;

	private boolean mockSwitchValue;
	private Switch input;
	private ElectricalComponent notA;
	private ElectricalComponent notB;
	private ElectricalComponent notC;

	@Before
	public void beforeEach() {
		blueprint = new Blueprint();

		input = new Switch(UUID.randomUUID().toString());
		notA = new Not(UUID.randomUUID().toString());
		notB = new Not(UUID.randomUUID().toString());
		notC = new Not(UUID.randomUUID().toString());

		blueprint.addComponent(input);
		blueprint.addComponent(notA);
		blueprint.addComponent(notB);
		blueprint.addComponent(notC);

		blueprint.connect(input, 0, notA, 0);
		blueprint.connect(notA, 0, notB, 0);
		blueprint.connect(notB, 0, notC, 0);
	}

	public boolean mockSwitchOutput() {
		return mockSwitchValue;
	}

	@Test
	public void replaceComponent() {
		input.setValue(false);

		assertTrue("Connections should work.", notC.getOutput(0).getValue());

		AtomicBoolean outputHasBeenCalled = new AtomicBoolean(false);
		ElectricalComponent notReplacement = new Not(UUID.randomUUID().toString());
		notReplacement.getOutput(0).addListener(id -> {
			outputHasBeenCalled.set(true);
		});

		blueprint.replaceComponent(notB, notReplacement);

		assertTrue("The replacements output should have been called", outputHasBeenCalled.get());
		assertTrue("The connection should work after a replacement", notC.getOutput(0).getValue());
	}
}