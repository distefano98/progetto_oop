package com.project.progetto_oop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SurveyCollection {


    /**
     * Metodo che implementa la logica OR su di un gruppo di insiemi di oggetti
     * @param collection Gruppo di insiemi di oggetti Survey
     * @return Restituisce l'insieme di oggetti ottenuto tramite unione
     */
    public static ArrayList<Survey> or (ArrayList<ArrayList<Survey>> collection) {
        Set<Survey> set = new HashSet<>();
        for (ArrayList<Survey> item : collection)
            set.addAll(item);
        return new ArrayList<>(set);
    }

    /**
     * Metodo che implementa la logica AND su di un gruppo di insiemi di oggetti
     * @param collection Gruppo di insiemi di oggetti Survey
     * @return Restituisce l'insieme di oggetti ottenuto tramite intersezione
     */
    public static ArrayList<Survey> and (ArrayList<ArrayList<Survey>> collection) {
        ArrayList<Survey> total = new ArrayList<>();
        for(int i = 0; i < collection.size(); i++) {
            for(Survey t : collection.get(i)){
                boolean included = true;
                for(ArrayList<Survey> itemToCompare : collection) {
                    if(!itemToCompare.contains(t)) {
                        included = false;
                        break;
                    }
                }
                if(included && !total.contains(t))
                    total.add(t);
            }
        }
        return total;
    }
}
