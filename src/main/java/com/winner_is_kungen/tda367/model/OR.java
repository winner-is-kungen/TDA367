package com.winner_is_kungen.tda367.model;

public class OR extends Component{

	//OR component for testing.

	private int numberOfInputs;

	OR(double X, double Y, int numberOfInputs){
		super(X, Y, numberOfInputs, 1);
		this.numberOfInputs = numberOfInputs;
	}

	//Public?
	public void calculate() {
		boolean tmp = false;
		for(int i = 0; i < numberOfInputs; i++) {
			tmp = tmp || getInPort(i);
		}
		setOutPort(0, tmp);
	}
}
