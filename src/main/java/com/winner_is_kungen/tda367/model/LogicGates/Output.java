package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.util.EventBus;

public class Output extends Component {
	public static final String typeID = "OUTPUT";

	/**
	 * The event type for events triggered by a change in the input to this component.
	 * This event has now message.
	 */
	public static final String changeEvent = "change";

	private final EventBus eventBus = new EventBus(changeEvent);

	private boolean currentInput = false;

	public Output(String id) {
		super(id, typeID, 1, 0);
	}

	/**
	 * Gets this Output components Event Buss.
	 * @return The Event Bus of this Output component.
	 */
	public EventBus getEventBus() {
		return eventBus;
	}

	/**
	 * Gets the latest value this component was supplied by it's input(s).
	 * @return The current value of this component.
	 */
	public boolean getInputValue() {
		return currentInput;
	}

	@Override
	protected boolean[] logic(boolean... vars) {
		currentInput = vars[0];

		eventBus.triggerEvent(changeEvent);

		return new boolean[0];
	}
}