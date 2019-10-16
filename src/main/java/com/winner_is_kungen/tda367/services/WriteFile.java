package com.winner_is_kungen.tda367.services;

import com.winner_is_kungen.tda367.controller.BlueprintController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {

	String home = System.getProperty("user.home");
	String path = home + File.separator + ".blueprintFiles" + File.separator + "test.txt";
	File file = new File(path);



	public WriteFile(){}

	public void write(BlueprintController bp){
		file.getParentFile().mkdirs();
		try(FileWriter fileWriter = new FileWriter(path)) {
			ArrayList<String> data = bp.getCurrentComponentsForWrite();
			for(String str: data) {
				fileWriter.write(str + System.lineSeparator());
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
