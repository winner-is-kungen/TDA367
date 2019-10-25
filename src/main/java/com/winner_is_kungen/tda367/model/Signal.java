package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.ConnectionRecord;

import java.util.ArrayList;
import java.util.List;

class Signal {

	private List<ConnectionRecord> listeners = new ArrayList<>();
	private boolean[] newValues = null;

	void broadcastUpdate(List<ComponentUpdateRecord> updateRecords, boolean[] newValues){

		this.newValues = newValues;

		for(ConnectionRecord connection : listeners){
      
			boolean outputValue = newValues[connection.getOutputChannel()];
			int inputChannel = connection.getInputChannel();
      
			connection.getListener().update(updateRecords, outputValue, inputChannel);
		}
	}

	public void add(ConnectionRecord newListener) {
		listeners.add(newListener);
	}

	public void remove(ConnectionRecord listener) {
		listeners.remove(listener);
	}

	public int size() {
		return listeners.size();
	}

	public boolean[] getNewValues() {
		return newValues;
	}

	public ConnectionRecord get(int index) {
		return listeners.get(index);
	}
}
