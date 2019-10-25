package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.Tuple;

import java.util.ArrayList;
import java.util.List;

class Signal {

	private List<Tuple<ComponentListener,Integer,Integer>> listeners = new ArrayList<>();

	void broadcastUpdate(List<ComponentUpdateRecord> updateRecords, boolean[] newValues){
		for(Tuple<ComponentListener,Integer,Integer> connection : listeners){

			boolean outputValue = newValues[connection.third()];
			int inputChannel = connection.second();

			connection.first().update(updateRecords, outputValue, inputChannel);
		}
	}

	public void add(Tuple<ComponentListener, Integer, Integer> newListener) {
		listeners.add(newListener);
	}

	public void remove(Tuple<ComponentListener, Integer, Integer> listener) {
		listeners.remove(listener);
	}

	public int size() {
		return listeners.size();
	}

	public Tuple<ComponentListener, Integer, Integer> get(int index) {
		return listeners.get(index);
	}
}
