package com.winner_is_kungen.tda367.model.util;

public interface IEventBusListener<T> {
	void react(EventBusEvent<T> event);
}