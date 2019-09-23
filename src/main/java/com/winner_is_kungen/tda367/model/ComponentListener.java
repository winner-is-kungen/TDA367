package com.winner_is_kungen.tda367.model;

public class ComponentListener implements ISignalListener {
	/** The id of the component that uses this as listener. */
	private final String componentId;
	/** The index at which the component listens for this listener. */
	private final int index;
	/** The "real" listener method from the component. */
	private final ISignalListener listener;

	public ComponentListener(String componentId, int index, ISignalListener listener) {
		this.componentId = componentId;
		this.index = index;
		this.listener = listener;
	}

	/**
	 * Gets the id of the component that uses this listener.
	 * @return The id of the component that uses this listener.
	 */
	public String getComponentId() {
		return componentId;
	}

	/**
	 * Gets the index at which the component listens for this listener.
	 * @return The index at which the component listens for this listener.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Relays the update to the "real" listener.
	 * @param id The id of the received update.
	 */
	@Override
	public void update(String id) {
		listener.update(id);
	}

	/**
	 * Returns the same code for two ComponentListeners that has teh sam componentId and index.
	 * This is so that only one of these can appear in a HasSet.
	 * @return The hash code.
	 */
	@Override
	public int hashCode() {
		return getComponentId().hashCode() + getIndex();
	}
}