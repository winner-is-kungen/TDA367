package com.winner_is_kungen.tda367.controller;

import com.winner_is_kungen.tda367.model.Component;

public interface IComponentControllerFactoryMethod {
	ComponentController Create(Component component);
}