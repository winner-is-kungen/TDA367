package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.LogicGates.NotGate;
import com.winner_is_kungen.tda367.model.util.IEventBusListener;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

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
	 * Should be abel to add all three components.
	 */
	@Test
	public void addingComponents() {
		blueprint.addComponent(notA);
		blueprint.addComponent(notB);
		blueprint.addComponent(notC);
	}

	/**
	 * Should send out an event when adding a component.
	 */
	@Test
	public void addingComponentEvent() {
		AtomicBoolean listenerHasBeenCalled = new AtomicBoolean(false);

		blueprint.getEventBus().addListener(
				Blueprint.eventComponent,
				(IEventBusListener<Blueprint.ComponentEvent>) event -> {
					assertEquals("Should list the added component.", notA, event.getMessage().getAffectedComponent());
					assertTrue("Should be an \"add\" event", event.getMessage().isAdded());
					listenerHasBeenCalled.set(true);
				}
		);

		blueprint.addComponent(notA);

		assertTrue("The event listener should have been called.", listenerHasBeenCalled.get());
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
	 * Should send out an event when removing a component.
	 */
	@Test
	public void removingComponentEvent() {
		addingComponents();

		AtomicBoolean listenerHasBeenCalled = new AtomicBoolean(false);

		blueprint.getEventBus().addListener(
				Blueprint.eventComponent,
				(IEventBusListener<Blueprint.ComponentEvent>) event -> {
					assertEquals("Should list the removed component.", notA, event.getMessage().getAffectedComponent());
					assertFalse("Should be a \"remove\" event", event.getMessage().isAdded());
					listenerHasBeenCalled.set(true);
				}
		);

		blueprint.removeComponent(notA);

		assertTrue("The event listener should have been called.", listenerHasBeenCalled.get());
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

	private void connectComponents() {
		blueprint.connect(notA, 0, notB, 0);
		blueprint.prepareNextSimulation();
		blueprint.connect(notB, 0, notC, 0);
		blueprint.prepareNextSimulation();
	}

	private void addAndConnectListener() {
		blueprint.addComponent(listener);
		blueprint.connect(notC, 0, listener, 0);
		blueprint.prepareNextSimulation();
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
	 * Should send out an event when connecting two components.
	 */
	@Test
	public void connectingComponentsEvent() {
		addingComponents();

		AtomicBoolean listenerHasBeenCalled = new AtomicBoolean(false);
		Component fromComponent = notA;
		int outChannel = 0;
		Component toComponent = notB;
		int inChannel = 0;

		blueprint.getEventBus().addListener(
				Blueprint.eventConnection,
				(IEventBusListener<Blueprint.ConnectionEvent>) event -> {
					assertEquals("Should list the \"from\" component.", fromComponent, event.getMessage().getFromComponent());
					assertEquals("Should list the \"out\" channel.", outChannel, event.getMessage().getOutChannel());
					assertEquals("Should list the \"to\" component.", toComponent, event.getMessage().getToComponent());
					assertEquals("Should list the \"in\" channel.", inChannel, event.getMessage().getInChannel());
					assertTrue("Should be a \"connect\" event", event.getMessage().isConnected());
					listenerHasBeenCalled.set(true);
				}
		);

		blueprint.connect(fromComponent, outChannel, toComponent, inChannel);

		assertTrue("The event listener should have been called.", listenerHasBeenCalled.get());
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
		blueprint.prepareNextSimulation();

		notA.update(false, 0);
		assertFalse("Should not have been updated.", listener.getChannel(0));
	}

	/**
	 * Should send out an event when disconnecting two components.
	 */
	@Test
	public void disconnectingComponentsEvent() {
		addingComponents();
		connectComponents();

		AtomicBoolean listenerHasBeenCalled = new AtomicBoolean(false);
		Component fromComponent = notA;
		int outChannel = 0;
		Component toComponent = notB;
		int inChannel = 0;

		blueprint.getEventBus().addListener(
				Blueprint.eventConnection,
				(IEventBusListener<Blueprint.ConnectionEvent>) event -> {
					assertEquals("Should list the \"from\" component.", fromComponent, event.getMessage().getFromComponent());
					assertEquals("Should list the \"out\" channel.", outChannel, event.getMessage().getOutChannel());
					assertEquals("Should list the \"to\" component.", toComponent, event.getMessage().getToComponent());
					assertEquals("Should list the \"in\" channel.", inChannel, event.getMessage().getInChannel());
					assertFalse("Should be a \"disconnect\" event", event.getMessage().isConnected());
					listenerHasBeenCalled.set(true);
				}
		);

		blueprint.disconnect(fromComponent, outChannel, notB, inChannel);

		assertTrue("The event listener should have been called.", listenerHasBeenCalled.get());
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

		AtomicBoolean hasComponentListenerBeenCalled = new AtomicBoolean(false);
		AtomicBoolean hasConnectionListenerBeenCalled = new AtomicBoolean(false);
		blueprint.getEventBus().addListener(Blueprint.eventComponent, event -> hasComponentListenerBeenCalled.set(true));
		blueprint.getEventBus().addListener(Blueprint.eventConnection, event -> hasConnectionListenerBeenCalled.set(true));

		blueprint.removeComponent(notB);
		blueprint.prepareNextSimulation();

		assertTrue("Component event listener should have been called.", hasComponentListenerBeenCalled.get());
		assertTrue("Connection event listener should have been called.", hasConnectionListenerBeenCalled.get());

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

		AtomicBoolean hasComponentListenerBeenCalled = new AtomicBoolean(false);
		AtomicBoolean hasConnectionListenerBeenCalled = new AtomicBoolean(false);
		blueprint.getEventBus().addListener(Blueprint.eventComponent, event -> hasComponentListenerBeenCalled.set(true));
		blueprint.getEventBus().addListener(Blueprint.eventConnection, event -> hasConnectionListenerBeenCalled.set(true));

		blueprint.replaceComponent(notB, notReplacement);

		// They should have been called multiple time but stuff like that is checked in other tests
		assertTrue("Component event listener should have been called.", hasComponentListenerBeenCalled.get());
		assertTrue("Connection event listener should have been called.", hasConnectionListenerBeenCalled.get());

		blueprint.prepareNextSimulation();

		notA.update(true, 0);
		assertFalse("The connection should work after a replacement", listener.getChannel(0));
	}
}