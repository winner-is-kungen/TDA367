package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.ConnectionRecord;

import java.util.ArrayList;
import java.util.List;

class Signal {
	private List<ConnectionRecord> listeners = new ArrayList<>();

	void broadcastUpdate(List<ComponentUpdateRecord> updateRecords, boolean[] newValues){
		for(ConnectionRecord connection : listeners){
      
			boolean outputValue = newValues[connection.getOutputChannel()];
			int inputChannel = connection.getInputChannel();
      
			connection.getListener().update(updateRecords, outputValue, inputChannel);
		}
	}

	void add(ConnectionRecord newListener) {
		listeners.add(newListener);
	}

	void remove(ConnectionRecord listener) {
		listeners.remove(listener);
	}

	int size() {
		return listeners.size();
	}

	ConnectionRecord get(int index) {
		return listeners.get(index);
	}
}
