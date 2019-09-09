package com.project.progetto_oop;

import com.project.progetto_oop.utils.Download;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class ProgettoOopApplication {
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
        SpringApplication.run(ProgettoOopApplication.class, args);
	}
}
