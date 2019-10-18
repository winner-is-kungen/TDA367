package com.winner_is_kungen.tda367.services;

import com.winner_is_kungen.tda367.controller.BlueprintController;
import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;

import java.io.*;
import java.util.Map;

public class ReadFile {

	public ReadFile() {
	}

	public Blueprint read(BlueprintController bpController, String path) {

		Blueprint bp = new Blueprint();
		bpController.setBlueprint(bp);

		Map<String, Component> createdComponents = Map.ofEntries();

		BufferedReader reader;
		try {

			reader = new BufferedReader(new FileReader(path + File.separator + "test.txt")); //TODO ADD BLUEPRINT NAME TO PATH
			String line = reader.readLine();
			while (line != null) {
				String[] content = line.split(";");

				if (!createdComponents.containsKey(content[0])) {
					Component newComponent = ComponentFactory.createComponent(content[1]);
					newComponent.getPosition().setX(Integer.valueOf(content[2]));
					newComponent.getPosition().setY(Integer.valueOf(content[3]));

					createdComponents.put(content[0], newComponent);
				} else {
					Component newComponent = createdComponents.get(content[0]);

					bpController.addComponent(newComponent);

					for (int i = 4; i < content.length; i++) {
						String[] connections = content[i].split(":");

						if (createdComponents.containsKey(connections[3])) {
							if (connections[0].equals("Input")) {
								bp.connect(createdComponents.get(connections[3]), Integer.valueOf(connections[1]), newComponent, Integer.valueOf(connections[2]));
							} else {
								bp.connect(newComponent, Integer.valueOf(connections[2]), createdComponents.get(connections[3]), Integer.valueOf(connections[1]));
							}
						}
					}
				}


				for (int i = 4; i < content.length; i++) {

				}

				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (bp);
	}


}
