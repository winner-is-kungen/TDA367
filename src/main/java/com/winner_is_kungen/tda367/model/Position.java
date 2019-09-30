package com.winner_is_kungen.tda367.model;

public class Position {
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
	public void getX(int value) {
		x = value;
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
	public void getY(int value) {
		y = value;
	}
}