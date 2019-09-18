package com.winner_is_kungen.tda367.model;

import java.util.ArrayList;

//public?
public abstract class Component {

	//X & Y values for positioning;
	private double X;
	private double Y;

	//Boolean-arrays to hold in- & output signals.
	private boolean[] input;
	private boolean[] output;

	//Constructor
	Component(double X, double Y, int numberOfInputs, int numberOfOutputs) {
		this.X = X;
		this.Y = Y;
		input = new boolean[numberOfInputs];
		output = new boolean[numberOfOutputs];
	}

	double getX() {
		return X;
	}

	double getY() {
		return Y;
	}

	void setX(double x) {
		X = x;
	}

	void setY(double y) {
		Y = y;
	}

	boolean getInPort(int portIndex) {
		return input[portIndex];
	}

	//Public?
	public boolean getOutPort(int portIndex) {
		return output[portIndex];
	}

	void setOutPort(int portIndex, boolean bool) {
		output[portIndex] = bool;
	}

	//Public?
	public void setInPort(int portIndex, boolean bool) {
		input[portIndex] = bool;
	}

	public abstract void calculate();
}
