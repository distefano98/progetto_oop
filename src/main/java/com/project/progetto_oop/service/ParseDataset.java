package com.project.progetto_oop.service;

import com.project.progetto_oop.model.Survey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ParseDataset {

    private final String COMMA_DELIMITER = "\",";
    private final String COMMA_DELIMITER2 = ",";

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
                String s0 = data[0].replace("\"","");
                String s1 = data[1].replace("\"","");
                String s2 = data[2].replace("\"","");
                String s3 = data[3].replace("\"","");
                String s4 = data[4].replace("\"","");
                String s5 = data[5].replace("\"","");
                String[] data2 = data[6].split(COMMA_DELIMITER2);
                int i = Integer.parseInt(s0);
                double d6 = Double.parseDouble(data2[0]);
                double d7 = Double.parseDouble(data2[1]);
                double d8 = Double.parseDouble(data2[2]);
                arrayList.add(new Survey(i,s1,s2,s3,s4,s5,d6,d7,d8,data2[3]));
            }
        } finally {
            return arrayList;
        }
    }
}
