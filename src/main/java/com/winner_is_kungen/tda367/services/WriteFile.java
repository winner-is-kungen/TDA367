package com.winner_is_kungen.tda367.services;

import com.winner_is_kungen.tda367.controller.BlueprintController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {

	public WriteFile() {
	}

	public void write(BlueprintController bp, String path) {
		File file = new File(path);
		file.getParentFile().mkdirs();
		try (FileWriter fileWriter = new FileWriter(path + File.separator + "test.txt")) { //TODO ADD NAME OF BLUEPRINT AS FILE NAME
			ArrayList<String> data = bp.getCurrentComponentsForWrite();
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
