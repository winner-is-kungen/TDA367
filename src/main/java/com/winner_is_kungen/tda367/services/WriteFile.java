package com.winner_is_kungen.tda367.services;

import com.winner_is_kungen.tda367.model.Blueprint;
import com.winner_is_kungen.tda367.model.Component;
import com.winner_is_kungen.tda367.model.ComponentListener;
import com.winner_is_kungen.tda367.model.util.Tuple;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {

	public WriteFile() {
	}

	public ArrayList<String> getComponentsForWrite(Blueprint bp) {
		ArrayList<String> data = new ArrayList<String>();
		String line;
		List<Component> componentList = bp.getComponentList();

		for (int i = 0; i < componentList.size(); i++) {
			Component comp = componentList.get(i);
			String inputs = "";
			String outputs = "";

			List<Tuple<Component, Integer, Integer>> oldInputs = bp.getIncomingConnections(comp);
			for (int j = 0; j < oldInputs.size(); j++) {
				Tuple<Component, Integer, Integer> incomingConnection = oldInputs.get(j);
				inputs = inputs + ";Input:" + incomingConnection.third() + ":" + incomingConnection.second() + ":" + incomingConnection.first().getId();
			}

			for (int k = 0; k < comp.getListenerSize(); k++) {
				Tuple<ComponentListener, Integer, Integer> listener = comp.getListener(k);
				if (listener.first() instanceof Component) {
					outputs = outputs + ";+Output:" + listener.third() + ":" + listener.second() + ":" + ((Component) listener.first()).getId();
				}
			}

			line = comp.getId() + ";" + comp.getTypeId() + ";" + comp.getPosition().getX() + ";" + comp.getPosition().getY() + inputs + outputs;
			data.add(line);
		}
		return data;
	}

	public void write(Blueprint bp, String path) {
		File file = new File(path);
		file.getParentFile().mkdirs();
		try (FileWriter fileWriter = new FileWriter(path + File.separator + "test.txt")) { //TODO ADD NAME OF BLUEPRINT AS FILE NAME
			ArrayList<String> data = this.getComponentsForWrite(bp);
			for (String str : data) {
				fileWriter.write(str + System.lineSeparator());
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
