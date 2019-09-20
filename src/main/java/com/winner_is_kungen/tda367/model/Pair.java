package com.winner_is_kungen.tda367.model;

/**
 * A generic container for two objects
 * @param <F> Object
 * @param <S> Object
 */

class Pair <F,S> {
	private F first;
	private S second;

	/**
	 * A Constructor for a Pair
	 * @param first a object of type F
	 * @param second a object of type S
	 */
	Pair(F first,S second){
		this.first = first;
		this.second = second;
	}

	/**
	 * A getter for the first object
	 * @return returns a Value of type F
	 */
	F first(){
		return  first;
	}

	/**
	 * A getter for the second object
	 * @return returns a value of type S
	 */
	S second(){
		return second;
	}

	/**
	 * A function for comparing two Pairs
	 * @param obj a object to compare with self
	 * @return returns true if obj is a Pair and the first and second obj are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;                        // True if compared with self
		if(null == obj) return false;                       // False if compared with nothing
		if(this.getClass() != obj.getClass()) return false; // False if obj is not a Pair
		Pair<F,S> p = (Pair<F,S>) obj;                      // True if both components of the two Pairs are equal
		return this.first == p.first() && this.second == p.second();
	}
}
