package com.winner_is_kungen.tda367.model.util;

/**
 * A generic container for three objects
 * @param <F> Object
 * @param <S> Object
 * @param <T> Object
 */

public class Tuple<F,S,T> {
	private final F first;
	private final S second;
	private final T third;

	/**
	 * A Constructor for a Pair
	 * @param first a object of type F
	 * @param second a object of type S
	 * @param third a object of type T
	 */
	public Tuple(F first, S second, T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	/**
	 * A getter for the first object
	 * @return returns a Value of type F
	 */
	public F first() {
		return  first;
	}

	/**
	 * A getter for the second object
	 * @return returns a value of type S
	 */
	public S second() {
		return second;
	}

	/**
	 * A getter for the third object
	 * @return returns a value of type T
	 */
	public T third() {
		return third;
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
		Tuple<F,S,T> p = (Tuple<F,S,T>) obj;                // True if both components of the two Pairs are equal
		return this.first() == p.first() && this.second() == p.second() && this.third() == p.third();
	}
}
