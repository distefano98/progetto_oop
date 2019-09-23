package com.project.progetto_oop.utils;

import com.project.progetto_oop.model.Survey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class FilterUtils<T> {

    public static Collection<Survey> filter(Collection<Survey> src, String fieldName, String operator, Object... value) {
        Collection<Survey> collection = new ArrayList<>();
        for(Survey item : src) {
            try {
                Method m = null;
                m = item.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
                Object tmp = null;
                tmp = m.invoke(item);
                if(FilterUtils.check(tmp, operator, value))
                    collection.add(item);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
        }
        return collection;
    }

    private static boolean check(Object value, String op, Object... objects) {

        if (objects.length == 1 && objects[0] instanceof Number && value instanceof Number) {

            float objValue = ((Number) objects[0]).floatValue();
            float valuef = ((Number) value).floatValue();
            switch (op) {
                case "$eq":
                    return valuef == objValue;
                case "$not":
                    return valuef != objValue;
                case "$lt":
                    return valuef < objValue;
                case "$lte":
                    return valuef <= objValue;
                case "$gt":
                    return valuef > objValue;
                case "$gte":
                    return valuef >= objValue;
            }
        }
        if(objects.length == 1 && objects[0] instanceof String && value instanceof String) {

            if(op.equals("$eq") || op.equals("$in"))
                return value.equals(objects[0]);
            else if(op.equals("$not") || op.equals("$nin"))
                return !value.equals(objects[0]);

        } else if(objects.length > 1) {

            if (op.equals("$bt")) {
                if(objects.length == 2 && objects[0] instanceof Number && objects[1] instanceof Number) {
                    Float min = ((Number)objects[0]).floatValue();
                    Float max = ((Number)objects[1]).floatValue();
                    Float valueF = ((Number)value).floatValue();
                    return valueF >= min && valueF <= max;
                }
            }
        }

        return false;
    }
}
