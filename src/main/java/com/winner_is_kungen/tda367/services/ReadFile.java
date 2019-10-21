package com.winner_is_kungen.tda367.services;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReadFile {

	private static final ReadFile instance = new ReadFile();

	public static ReadFile getReadFileInstance() {
		return instance;
	}

	private ReadFile() {
	}

	public Blueprint read(String path) {

		Blueprint bp = new Blueprint();

		Map<String, Component> createdComponents = new HashMap<>();

		BufferedReader reader;
		try {

			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			while (line != null) {
				String[] content = line.split(";");

				if (!createdComponents.containsKey(content[0])) {
					Component newComponent = ComponentFactory.createComponent(content[1]);
					newComponent.getPosition().setX(Integer.valueOf(content[2]));
					newComponent.getPosition().setY(Integer.valueOf(content[3]));

					createdComponents.put(content[0], newComponent);
				}

				Component newComponent = createdComponents.get(content[0]);

				bp.addComponent(newComponent);
				bp.setName(path.substring(path.lastIndexOf(File.separator), path.length()).replace(File.separator, ""));

				for (int i = 4; i < content.length; i++) {
					String[] connections = content[i].split(":");

					if (createdComponents.containsKey(connections[3])) {
						if (connections[0].equals("Input")) {
							bp.connect(createdComponents.get(connections[3]), Integer.valueOf(connections[2]), newComponent, Integer.valueOf(connections[1]));
						} else {
							bp.connect(newComponent, Integer.valueOf(connections[1]), createdComponents.get(connections[3]), Integer.valueOf(connections[2]));
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
