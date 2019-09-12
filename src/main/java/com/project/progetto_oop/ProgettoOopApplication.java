package com.project.progetto_oop;

import com.project.progetto_oop.model.Survey;
import com.project.progetto_oop.utils.Download;
import com.project.progetto_oop.utils.ParseDataset;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class ProgettoOopApplication {

	public static ArrayList<Survey> startArrayList;

	public static void main(String[] args){
		File file = new File("dataset.csv");
        if(!file.exists()){
			Download download = new Download();
			try {
				download.downloadCsv(file.getName());
			} catch (IOException | JSONException e) {
				e.printStackTrace();
			}
		}
		ParseDataset parseDataset = new ParseDataset();
        startArrayList = parseDataset.parserCsv(file.getName());
        SpringApplication.run(ProgettoOopApplication.class, args);
	}
}
