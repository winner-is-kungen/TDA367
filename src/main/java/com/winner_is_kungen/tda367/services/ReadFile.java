package com.winner_is_kungen.tda367.services;

import com.winner_is_kungen.tda367.controller.BlueprintController;
import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.LogicGates.ComponentFactory;

import java.io.*;
import java.util.ArrayList;

public class ReadFile {

	String home = System.getProperty("user.home");
	String path = home + File.separator + ".blueprintFiles" + File.separator + "test.txt";

	public ReadFile() {
	}

	public void read(BlueprintController bpController) {

		Blueprint bp = new Blueprint();
		bpController.setBlueprint(bp);

		ArrayList<String> createdComponents = new ArrayList<>();

		BufferedReader reader;
		try {

			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				String[] content = line.split(";");

				if (!createdComponents.contains(content[0])){
					createdComponents.add(content[0]);

					Component newComponent = ComponentFactory.createComponent(content[1]);
					newComponent.getPosition().setX(Integer.valueOf(content[2]));
					newComponent.getPosition().setY(Integer.valueOf(content[3]));

					bpController.addComponent(newComponent);
				}



				for (int i = 4; i < content.length; i++){

				}

				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
