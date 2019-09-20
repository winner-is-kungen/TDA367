package com.winner_is_kungen.tda367.model;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public abstract class Component implements ComponentListener {
	private int pos_x;
	private int pos_y;

	private int nr_inputs;
	private boolean[] input_channels;

	private int id;

	public Component(int id,int inputs){
		this.nr_inputs = inputs;
		this.id = id;

		this.input_channels = new boolean[nr_inputs];
	}

	private List<Pair<ComponentListener,Integer>> listeners = new ArrayList<>();

	void addListener(ComponentListener l,int channel){
		listeners.add(new Pair<>(l,channel));
	}
	void removeListener(ComponentListener l,int channel){
		Pair<ComponentListener,Integer> p = new Pair<>(l,channel);
		listeners.remove(p);
	}

	protected abstract boolean logic(boolean... vars);

	public void update(boolean val,int channel){
		input_channels[channel] = val;
		boolean current = logic(input_channels);
		for (Pair p :listeners) {
			((ComponentListener)(p.first())).update(current,(int) p.second());
		}
	}
}
