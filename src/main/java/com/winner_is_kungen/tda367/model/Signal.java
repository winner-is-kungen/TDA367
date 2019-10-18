package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.Tuple;

import java.util.ArrayList;

class Signal extends ArrayList<Tuple<ComponentListener,Integer,Integer>> {

	void broadcastUpdate(String updateID, boolean[] newValues){
		for(Tuple<ComponentListener,Integer,Integer> connection : this){

			boolean outputValue = newValues[connection.third()];
			int inputChannel = connection.second();

			connection.first().update(updateID, outputValue, inputChannel);
		}
	}
}
