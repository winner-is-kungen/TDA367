package com.winner_is_kungen.tda367.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A Signal is owned by a component as a output and can be given to a component as input.
 */
public class Signal {
	/** The id of this Signals owner. */
	private final String ownerId;
	/** The index of this Signal among its owners outputs. */
	private final int index;
	/** A set of listeners where one listener only can appear once. */
	private final Set<ISignalListener> listeners = new HashSet<ISignalListener>();
	/** A reference to the method for getting the value this Signal is supposed to send out. */
	private final ISignalValue valueGetter;

	/** Cached value from the latest update. */
	private boolean cachedValue;

	/**
	 * Creates a new Signal with a set method that returns the Signals value.
	 * @param ownerId     The id of this Signals owner.
	 * @param index       The index this Signal has in its owner.
	 * @param valueGetter The method that returns teh value of the Signal.
	 */
	public Signal(String ownerId, int index, ISignalValue valueGetter) {
		this.ownerId = ownerId;
		this.index = index;
		this.valueGetter = valueGetter;

		updateCache();
	}

	/**
	 * Gets this Signals owners id.
	 * @return The id of this Signals owner.
	 */
	public String getOwnerId() {
		return ownerId;
	}
	/**
	 * Gets this Signals index among the outputs of its owner.
	 * @return The index of this Signal among the outputs of its owners.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the value of this Signal.
	 * @return The value of this Signal.
	 */
	public boolean getValue() {
		return cachedValue;
	}

	/**
	 * Adds a new listener for updates from this Signal.
	 * @param listener The listener to be added.
	 */
	public void addListener(ISignalListener listener) {
		listeners.add(listener);
	}
	/**
	 * Removes a previously added listener to stop it from receiving updates from this Signal.
	 * @param listener The listener to be removed.
	 */
	public void removeListener(ISignalListener listener) {
		listeners.remove(listener);
	}
	/**
	 * Removes a previously added component listener to stop it from receiving updates from this Signal.
	 * @param listenerId The id of the listening component.
	 * @param index      The index at which the listening component listens.
	 */
	public void removeListener(String listenerId, int index) {
		listeners.removeIf(x -> {
			if (x instanceof ComponentListener) {
				ComponentListener cl = (ComponentListener)x;
				if (cl.getComponentId().equals(listenerId) && cl.getIndex() == index) {
					return true;
				}
			}

			return false;
		});
	}

	/**
	 * Gets a list of all ComponentListeners that listen to this Signal.
	 * @return A list of ComponentListeners.
	 */
	public List<ComponentListener> getComponentListeners() {
		List<ComponentListener> componentListeners = new ArrayList<ComponentListener>();

		for (ISignalListener listener : listeners) {
			if (listener instanceof ComponentListener) {
				componentListeners.add((ComponentListener)listener);
			}
		}

		return componentListeners;
	}

	/**
	 * Notifies all the Signals listeners of an update.
	 * @param id The id of the update.
	 */
	public void update(String id) {
		updateCache();

		for (ISignalListener listener : listeners) {
			listener.update(id);
		}
	}

	/**
	 * Gets the value from the valueGetter and saves it to the cache.
	 */
	private void updateCache() {
		cachedValue = valueGetter.getValue();
	}
}