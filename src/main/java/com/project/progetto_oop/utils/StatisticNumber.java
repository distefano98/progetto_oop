package com.project.progetto_oop.utils;

import com.project.progetto_oop.model.Survey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class StatisticNumber {

    private double avg;
    private double min;
    private double max;
    private double dev;
    private double sum;
    private int count;

    public StatisticNumber(ArrayList<Survey> surveys, String field) {
        this.count = surveys.size();
        double[] values = new double[count];
        for ( int i = 0; i < count ; i++){
            Method m = null;
            try {
                m = Survey.class.getMethod("get"+field.substring(0, 1).toUpperCase()+field.substring(1),null);
                Object doubleValue = m.invoke(surveys.get(i));
                values[i] = (double) doubleValue;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            setSum(values);
            setAvg(values);
            setMin(values);
            setMax(values);
            setDev(values);
        }
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    private void setAvg(double[] values) {
        setAvg(sum/count);
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    private void setMin(double[] values) {
        double min = values[0];
        for ( int i = 1; i < values.length ; i++){
            if(values[i] < min){
                min = values[i];
            }
        }
        setMin(min);
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    private void setMax(double[] values) {
        double max = values[0];
        for ( int i = 1; i < values.length ; i++){
            if(values[i] > max){
                max = values[i];
            }
        }
        setMax(max);
    }

    public double getDev() {
        return dev;
    }

    public void setDev(double dev) {
        this.dev = dev;
    }

    private void setDev(double[] values) {
        double summ = 0;
        for (double v : values) {
            summ += Math.pow(v - avg, 2);
        }
        setDev((double) Math.pow(summ/count, 0.5));
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    private void setSum(double[] values) {
        for (double v : values){
            sum += v;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
