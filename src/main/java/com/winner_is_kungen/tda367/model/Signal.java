package com.winner_is_kungen.tda367.model;

import com.winner_is_kungen.tda367.model.util.Tuple;

import java.util.ArrayList;

class Signal extends ArrayList<Tuple<ComponentListener,Integer,Integer>> {

	void broadcastUpdate(boolean[] newValues){
		for(Tuple<ComponentListener,Integer,Integer> connection : this){
			boolean outputValue = newValues[connection.third()];
			connection.first().update(outputValue,connection.second());
		}
	}
}
