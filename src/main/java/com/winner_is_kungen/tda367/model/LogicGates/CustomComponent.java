package com.winner_is_kungen.tda367.model.LogicGates;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntFunction;

public class CustomComponent extends Component {
	private final Blueprint blueprint;

	/**
	 * Constructor for the Component.
	 *
	 * @param id          A String specifying the given id for the component.
	 * @param blueprintID A String specifying the given id for the provided blueprint.
	 * @param blueprint   A Blueprint that calculates the logic.
	 */
	public CustomComponent(String id, String blueprintID, Blueprint blueprint) {
		super(id, blueprintID, getBlueprintInputs(blueprint).size(), getBlueprintOutputs(blueprint).size());

		this.blueprint = blueprint;
	}

	private static List<Input> getBlueprintInputs(Blueprint blueprint) {
		return getBlueprintComponents(blueprint, Input.getTypeID());
	}
	private static List<Output> getBlueprintOutputs(Blueprint blueprint) {
		return getBlueprintComponents(blueprint, Output.getTypeID());
	}
	private static <T> List<T> getBlueprintComponents(Blueprint blueprint, String typeID) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < blueprint.getComponentCount(); i++) {
			Component component = blueprint.getComponent(i);
			if (component.getTypeId().equals(typeID)) {
				list.add((T) component);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	protected boolean[] logic(boolean... vars) {
		List<Input> inputs = getBlueprintInputs(blueprint);
		for (int i = 0; i < inputs.size(); i++) {
			Input component = inputs.get(i);
			if (component.getState() != vars[i]) {
				component.switchState();
			}
		}

		List<Output> outputs = getBlueprintOutputs(blueprint);
		boolean[] outVars = new boolean[outputs.size()];
		for (int i = 0; i < outputs.size(); i++) {
			outVars[i] = outputs.get(i).getInputValue();
		}

		return outVars;
	}
}