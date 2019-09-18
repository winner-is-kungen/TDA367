package com.winner_is_kungen.tda367.model;

public class ComponentFactory {

	public static Component create2OR(double X, double Y){
		return new OR(X, Y, 2);
	}
}
