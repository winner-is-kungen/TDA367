package com.winner_is_kungen.tda367.model;

public class Output implements ComponentListener {

	private boolean channels[];

	public Output(int inputs){
		channels = new boolean[inputs];
	}

	public void update(boolean val, int channel){
		channels[channel] = val;
		//System.out.println("Output on channel"+channel+"is "+val);
	}

	public boolean getChannel(int channel){
		return channels[channel];
	}
}
