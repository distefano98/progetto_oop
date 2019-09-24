package com.project.progetto_oop.utils;

import com.project.progetto_oop.model.Survey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class StatisticString {

    private String value;
    private int count;
    private int unique;
    private float countPercent;

    /**
     * Metodo che consente di elabora le statistiche per i dati alfanumerici
     * @param surveys arraylist di Survey
     * @param value valore sul quale effettuare le statistiche
     * @param field nome del campo sul quale effettuare le statistiche
     */
    public StatisticString(ArrayList<Survey> surveys, String value, String field) {
        this.value = value;
        this.count = surveys.size();
        for ( Survey s : surveys){
            Method m = null;
            try {
                m = Survey.class.getMethod("get"+field.substring(0, 1).toUpperCase()+field.substring(1),null);
                Object string = m.invoke(s);
                if((value.equals((String) string))){
                    unique++;
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        this.countPercent = (float) unique/count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public float getCountPercent() {
        return countPercent;
    }

    public void setCountPercent(float countPercent) {
        this.countPercent = countPercent;
    }
}
