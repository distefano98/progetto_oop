package com.project.progetto_oop.utils;

import com.project.progetto_oop.model.Survey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ParseDataset {

    private final String COMMA_DELIMITER = ",";

    /**
     * Metodo per parsare il csv in classi Survey, collezionati come Arraylist
     *
     * @param fileName nome del csv da parsare
     * @return ritorna l'arraylist
     */
    public ArrayList<Survey> parserCsv (String fileName) {

        ArrayList<Survey> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(COMMA_DELIMITER);
                int i = Integer.parseInt(data[0].replace('"',' '));
                double d6 = Double.parseDouble(data[6]);
                double d7 = Double.parseDouble(data[7]);
                double d8 = Double.parseDouble(data[8]);
                arrayList.add(new Survey(i,data[1],data[2],data[3],data[4],data[5],d6,d7,d8,data[9]));
            }
        } finally {
            return arrayList;
        }
    }
}
