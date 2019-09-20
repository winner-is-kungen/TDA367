package com.winner_is_kungen.tda367.model;

class Pair <F,S> {
	private F first;
	private S second;
	Pair(F first,S second){
		this.first = first;
		this.second = second;
	}

	F first(){
		return  first;
	}

	S second(){
		return second;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(null == obj) return false;
		if(this.getClass() != obj.getClass()) return false;
		Pair<F,S> p = (Pair<F,S>) obj;
		return this.first == p.first() && this.second == p.second();
	}
}
