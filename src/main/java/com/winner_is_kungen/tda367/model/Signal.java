package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains connections between the outputs of the parent
 * and the inputs of the listeners
 */
class Signal {
	// Connections are saved as Tuple<ComponentListener,inputChannel,outputChannel>
	// Where outputChannel is the output of the owner of Signal and inputChannel is
	// the input of the listener
	private List<Tuple<ComponentListener,Integer,Integer>> listeners = new ArrayList<>();

	void broadcastUpdate(String updateID, boolean[] newValues){
		for(Tuple<ComponentListener,Integer,Integer> connection : listeners){

			boolean outputValue = newValues[connection.third()];
			int inputChannel = connection.second();

			connection.first().update(updateID, outputValue, inputChannel);
		}
	}

	void add(Tuple<ComponentListener, Integer, Integer> newListener) {
		listeners.add(newListener);
	}

	void remove(Tuple<ComponentListener, Integer, Integer> listener) {
		listeners.remove(listener);
	}

	int size() {
		return listeners.size();
	}

	Tuple<ComponentListener, Integer, Integer> get(int index) {
		return listeners.get(index);
	}
}
