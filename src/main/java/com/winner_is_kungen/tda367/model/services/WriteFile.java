package com.winner_is_kungen.tda367.model.services;

import com.winner_is_kungen.tda367.model.Blueprint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

	String home = System.getProperty("user.home");
	String path = home + File.separator + ".blueprintFiles" + File.separator + "test.txt";


	public void write(Blueprint bp){
		try(FileWriter fileWriter = new FileWriter(path)) {
			String fileContent = "test";
			fileWriter.write(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
