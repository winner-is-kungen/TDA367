package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlueprintTest {
	private Blueprint blueprint;

	private Component notA;
	private Component notB;
	private Component notC;

	private Output listener;

	@Before
	public void beforeEach() {
		blueprint = new Blueprint();

		notA = new NotGate(1);
		notB = new NotGate(2);
		notC = new NotGate(3);

		listener = new Output(-1, 1);
	}

	/**
	 * Should ba abel to add all three components.
	 */
	@Test
	public void addingComponents() {
		blueprint.addComponent(notA);
		blueprint.addComponent(notB);
		blueprint.addComponent(notC);
	}

	/**
	 * Should not be able to add one component twice.
	 */
	@Test
	public void addingTwice() {
		blueprint.addComponent(notA);

		try {
			blueprint.addComponent(notA);
			fail("Expects addComponent to throw an error if the provided component is already included.");
		}
		catch (IllegalArgumentException ignored) { }
	}

	/**
	 * Should be able to remove included components.
	 */
	@Test
	public void removingComponents() {
		addingComponents();

		blueprint.removeComponent(notA);
		blueprint.removeComponent(notB);
		blueprint.removeComponent(notC);
	}

	/**
	 * Should not be able to remove components that aren't included.
	 */
	@Test
	public void removingTwice() {
		addingComponents();

		blueprint.removeComponent(notA);

		try {
			blueprint.removeComponent(notA);
			fail("Expects removeComponent to throw an error if the provided component isn't included.");
		}
		catch (IllegalArgumentException ignored) { }
	}

	public void connectComponents() {
		blueprint.connect(notA, 0, notB, 0);
		blueprint.connect(notB, 0, notC, 0);
	}

	public void addAndConnectListener() {
		blueprint.addComponent(listener);
		blueprint.connect(notC, 0, listener, 0);
	}

	/**
	 * Should be able to connect included components.
	 */
	@Test
	public void connectingComponents() {
		addingComponents();

		// Should not throw any errors
		connectComponents();

		addAndConnectListener();

		notA.update(true, 0);
		assertFalse("Should get an output all the way trough.", listener.getChannel(0));
	}

	/**
	 * Should be able to disconnect included components.
	 */
	@Test
	public void disconnectingComponents() {
		addingComponents();
		connectComponents();
		addAndConnectListener();

		notA.update(true, 0);
		assertFalse("Should get an output all the way trough.", listener.getChannel(0));

		blueprint.disconnect(notA, 0, notB, 0);

		notA.update(false, 0);
		assertFalse("Should not have been updated.", listener.getChannel(0));
	}

	/**
	 * Should not be able to connect components that aren't included.
	 */
	@Test
	public void connectingOutsideComponents() {
		blueprint.addComponent(notA);
		blueprint.addComponent(notC);

		try {
			blueprint.connect(notA, 0, notB, 0);
			fail("Expects connect to throw an error if the first provided component isn't included.");
		}
		catch (IllegalArgumentException ignored) { }

		try {
			blueprint.connect(notB, 0, notC, 0);
			fail("Expects connect to throw an error if the second provided components isn't included.");
		}
		catch (IllegalArgumentException ignored) { }
	}

	/**
	 * Should not be able to connect to the same channel on the same component twice.
	 */
	@Test
	public void connectingTwice() {
		addingComponents();
		connectComponents();

		try {
			blueprint.connect(notA, 0, notB, 0);
			fail("Expects connect to throw an error if the input is already occupied.");
		}
		catch (IllegalStateException ignored) { }
	}

	/**
	 * Should not be able to connect to too high in/out channels.
	 */
	@Test
	public void connectingOutOfRange() {
		addingComponents();

		try {
			blueprint.connect(notA, 1, notB, 0);
			fail("Expects connect to throw an error if the output channel is out of bounds.");
		}
		catch (IllegalArgumentException ignored) { }

		try {
			blueprint.connect(notA, 0, notB, 1);
			fail("Expects connect to throw an error if the input channel is out of bounds.");
		}
		catch (IllegalArgumentException ignored) { }
	}

	/**
	 * Should remove connections when removing components.
	 */
	@Test
	public void removeAComponentAndItsConnections() {
		addingComponents();
		connectComponents();
		addAndConnectListener();

		notA.update(true, 0);
		assertFalse("Should get an output all the way trough.", listener.getChannel(0));

		blueprint.removeComponent(notB);

		notA.update(false, 0);
		assertFalse("Should not have been updated.", listener.getChannel(0));
	}

	/**
	 * It should be possible to replace a component.
	 */
	@Test
	public void replaceComponent() {
		addingComponents();
		connectComponents();
		addAndConnectListener();

		notA.update(false, 0);
		assertTrue("Connections should work.", listener.getChannel(0));

		Component notReplacement = new NotGate(4);

		blueprint.replaceComponent(notB, notReplacement);

		blueprint.prepareNextSimulation();

		notA.update(true, 0);
		assertFalse("The connection should work after a replacement", listener.getChannel(0));
	}
}