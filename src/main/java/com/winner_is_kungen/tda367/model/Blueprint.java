package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.EventBus;
import com.winner_is_kungen.tda367.model.util.Tuple;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Blueprint {

	/**
	 * The event type for events triggered by a connection change.
	 * The message of the event is of type `ConnectionEvent`.
	 */
	public static final String eventConnection = "connection";
	/**
	 * The event type for events triggered by a change in the component list.
	 * The message of the event is of type `ComponentEvent`.
	 */
	public static final String eventComponent = "component";

	/**
	 * The list holding all components in this Blueprint.
	 */
	private final List<Component> componentList = new ArrayList<Component>();

	/**
	 * The EventBus that handles events for the Blueprint.
	 */
	private final EventBus eventBus = new EventBus(eventConnection, eventComponent);

	/**
	 * The name of the blueprint
	 */

	private String name;

	private String path = null;

	/**
	 * Gets the EventBus that handles events for this Blueprint.
	 *
	 * @return An EventBus that handles events for this Blueprint.
	 */
	public EventBus getEventBus() {
		return eventBus;
	}

	/**
	 * Gets all components in this blueprint
	 *
	 * @return Returns a list of all components in this blueprint
	 */

	public List<Component> getComponentList() {
		return componentList;
	}

	/**
	 * Adds a new component to the Blueprint.
	 *
	 * @param component The new component.
	 */
	public void addComponent(Component component) {
		if (componentList.contains(component)) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}
		componentList.add(component);

		eventBus.triggerEvent(eventComponent, new ComponentEvent(component, true));
	}

	/**
	 * Gets the Component at the specified index.
	 *
	 * @param index The index of the Component.
	 * @return A Component from the Blueprint.
	 */
	public Component getComponent(int index) {
		return componentList.get(index);
	}

	/**
	 * Gets the name of the Blueprint
	 *
	 * @return The name of this Blueprint
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the Blueprint
	 *
	 * @param name The name of this Blueprint
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the path of the Blueprint
	 *
	 * @return The path of this Blueprint
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Sets the path of the Blueprint
	 *
	 * @param path The path of this Blueprint
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the amount of Components in this Blueprint.
	 *
	 * @return The amount of Components in this Blueprint.
	 */
	public int getSize() {
		return componentList.size();
	}

	/**
	 * Removes a component and all of its connections from the Blueprint.
	 *
	 * @param component The component to be removed.
	 */
	public void removeComponent(Component component) {
		if (!componentList.contains(component)) {
			throw new IllegalArgumentException("Can't remove a component unless it's included in this Blueprint.");
		}

		removeAllConnections(component);

		componentList.remove(component);

		eventBus.triggerEvent(eventComponent, new ComponentEvent(component, false));
	}

	/**
	 * Connects two components.
	 *
	 * @param fromComponent The component the output should be taken from.
	 * @param outChannel    The channel of the output from the "fromComponent" that should be taken.
	 * @param toComponent   The component that should receive the new input.
	 * @param inChannel     The channel at which the input should be received.
	 */
	public void connect(Component fromComponent, int outChannel, Component toComponent, int inChannel) {
		if (!componentList.contains(fromComponent) || !componentList.contains(toComponent)) {
			throw new IllegalArgumentException("Can't make a connection between two components unless both are included in this Blueprint.");
		}

		if (fromComponent.getNrOutputs() <= outChannel) {
			throw new IllegalArgumentException("Out channel out of range.");
		}
		if (toComponent.getNrInputs() <= inChannel) {
			throw new IllegalArgumentException("In channel out of range.");
		}

		if (getIncomingConnections(toComponent).stream().anyMatch(x -> x.second() == inChannel)) {
			throw new IllegalStateException("One component can't have two inputs on the same channel.");
		}

		fromComponent.addListener(toComponent, inChannel, outChannel);

		eventBus.triggerEvent(eventConnection, new ConnectionEvent(fromComponent, outChannel, toComponent, inChannel, true));
	}

	/**
	 * Removes the connection between two components.
	 *
	 * @param fromComponent The component the connection went from.
	 * @param outChannel    The channel the connection went from.
	 * @param toComponent   The component that receives the connection.
	 * @param inChannel     The channel at which the component receives the connection.
	 */
	public void disconnect(Component fromComponent, int outChannel, Component toComponent, int inChannel) {
		if (!componentList.contains(toComponent)) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		fromComponent.removeListener(toComponent, inChannel, outChannel);

		eventBus.triggerEvent(eventConnection, new ConnectionEvent(fromComponent, outChannel, toComponent, inChannel, false));
	}

	/**
	 * Gets an unmodifiable list of connections going to the Component.
	 * The list are Tupples with the other component, the outgoing channel, and the incoming channel.
	 *
	 * @param component The component you wish to learn the incoming connections to.
	 * @return An unmodifiable list of connections.
	 */
	public List<Tuple<Component, Integer, Integer>> getIncomingConnections(Component component) {
		List<Tuple<Component, Integer, Integer>> connections = new ArrayList<Tuple<Component, Integer, Integer>>();

		for (Component other : componentList) {
			for (int i = 0; i < other.getListenerSize(); i++) {
				Tuple<ComponentListener, Integer, Integer> listener = other.getListener(i);
				if (listener != null && listener.first().equals(component)) {
					connections.add(new Tuple<Component, Integer, Integer>(other, listener.second(), listener.third()));
				}
			}
		}

		return Collections.unmodifiableList(connections);
	}

	/**
	 * Removes all to and from one component.
	 *
	 * @param component The component.
	 */
	public void removeAllConnections(Component component) {
		if (!componentList.contains(component)) {
			throw new IllegalArgumentException("Can't handle connections unless the component is included in this Blueprint.");
		}

		// Removes all connections to this component
		for (Tuple<Component, Integer, Integer> connection : getIncomingConnections(component)) {
			disconnect(connection.first(), connection.third(), component, connection.second());
		}

		// Removes all connections from this component
		for (int i = 0; i < component.getListenerSize(); i++) {
			Tuple<ComponentListener, Integer, Integer> listener = component.getListener(i);
			if (listener.first() instanceof Component) {
				// Disconnects other components
				disconnect(component, listener.third(), (Component) listener.first(), listener.second());
			} else {
				// Disconnects other kinds of listeners
				component.removeListener(listener.first(), listener.second(), listener.third());
			}
		}
	}

	/**
	 * Removes a component from this Blueprint and adds a new component in its place.
	 * Makes sure all possible connections are added back.
	 *
	 * @param oldComponent The component in the Blueprint to be removed.
	 * @param newComponent The new component to be added to the Blueprint.
	 */
	public void replaceComponent(Component oldComponent, Component newComponent) {
		if (!componentList.contains(oldComponent)) {
			throw new IllegalArgumentException("Can't remove a component unless it's included in this Blueprint.");
		}
		if (componentList.contains(newComponent)) {
			throw new IllegalArgumentException("Can't add a component that's already included in this component.");
		}

		// Gather all the connections to the old component
		List<Tuple<Component, Integer, Integer>> oldInputs = getIncomingConnections(oldComponent);

		// Gather all the connections from the old component
		List<Tuple<Component, Integer, Integer>> oldOutputs = new ArrayList<Tuple<Component, Integer, Integer>>();
		for (int i = 0; i < oldComponent.getListenerSize(); i++) {
			Tuple<ComponentListener, Integer, Integer> listener = oldComponent.getListener(i);
			if (listener.first() instanceof Component) {
				oldOutputs.add(new Tuple<Component, Integer, Integer>((Component) listener.first(), listener.second(), listener.third()));
			}
		}

		removeComponent(oldComponent);

		addComponent(newComponent);

		// Add all the connections that went to the old component
		for (int i = 0; i < oldInputs.size(); i++) {
			Tuple<Component, Integer, Integer> incomingConnection = oldInputs.get(i);
			if (newComponent.getNrInputs() > incomingConnection.second()) {
				connect(incomingConnection.first(), incomingConnection.third(), newComponent, incomingConnection.second());
			}
		}

		// Add all the connections that went from the old component
		for (int i = 0; i < oldOutputs.size(); i++) {
			Tuple<Component, Integer, Integer> outgoingConnection = oldOutputs.get(i);
			if (newComponent.getNrOutputs() > outgoingConnection.third()) {
				connect(newComponent, outgoingConnection.third(), outgoingConnection.first(), outgoingConnection.second());
			}
		}
	}

	public class ComponentEvent {
		private final Component affectedComponent;
		private final boolean added;

		public ComponentEvent(Component affectedComponent, boolean added) {
			this.affectedComponent = affectedComponent;
			this.added = added;
		}

		/**
		 * Gets the affected Component.
		 *
		 * @return The affected Component.
		 */
		public Component getAffectedComponent() {
			return affectedComponent;
		}

		/**
		 * If the affected Component was added or removed.
		 *
		 * @return If the affected Component was added or removed.
		 */
		public boolean isAdded() {
			return added;
		}
	}

	public class ConnectionEvent {
		private final Component fromComponent;
		private final int outChannel;
		private final Component toComponent;
		private final int inChannel;
		private final boolean connected;

		public ConnectionEvent(Component fromComponent, int outChannel, Component toComponent, int inChannel, boolean connected) {
			this.fromComponent = fromComponent;
			this.outChannel = outChannel;
			this.toComponent = toComponent;
			this.inChannel = inChannel;
			this.connected = connected;
		}

		/**
		 * Gets the Component the connection goes from.
		 *
		 * @return The Component the connection goes from.
		 */
		public Component getFromComponent() {
			return fromComponent;
		}

		/**
		 * Gets the channel the connections goes from.
		 *
		 * @return The channel the connections goes from.
		 */
		public int getOutChannel() {
			return outChannel;
		}

		/**
		 * Gets the Component the connection goes to.
		 *
		 * @return The Component the connection goes to.
		 */
		public Component getToComponent() {
			return toComponent;
		}

		/**
		 * Gets the channel the connections goes to.
		 *
		 * @return The channel the connections goes to.
		 */
		public int getInChannel() {
			return inChannel;
		}

		/**
		 * If the event is because the connections was made (`true`) or removed (`false`).
		 *
		 * @return The connection state.
		 */
		public boolean isConnected() {
			return connected;
		}

	}
}