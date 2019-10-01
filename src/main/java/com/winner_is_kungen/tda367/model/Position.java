package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.EventBus;

public class Position {
	/**
	 * The event type for events triggered by a change in Position.
	 * No message from this event.
	 */
	public static final String eventPosition = "position";

	/** The EventBus that handles events for the Position. */
	private final EventBus eventBus = new EventBus(eventPosition);

	/** This Positions x-coordinate. */
	private int x;
	/** This Positions y-coordinate. */
	private int y;

	/**
	 * Creates a new Position at the defined coordinates.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Creates a new Position at coordinate systems origin.
	 */
	public Position() {
		this(0, 0);
	}

	/**
	 * Gets the EventBus that handles events for this Position.
	 * @return An EventBus that handles events for this Position.
	 */
	public EventBus getEventBus() {
		return eventBus;
	}

	/**
	 * Gets the x-coordinate.
	 * @return The x-coordinate.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Sets the x-coordinate.
	 * @param value The new x-coordinate.
	 */
	public void setX(int value) {
		x = value;
		eventBus.triggerEvent(eventPosition);
	}

	/**
	 * Gets the y-coordinate.
	 * @return The y-coordinate.
	 */
	public int getY() {
		return y;
	}
	/**
	 * Sets the y-coordinate.
	 * @param value The new y-coordinate.
	 */
	public void setY(int value) {
		y = value;
		eventBus.triggerEvent(eventPosition);
	}
}