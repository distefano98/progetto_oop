package com.project.progetto_oop.service;

import com.project.progetto_oop.model.Survey;
import com.project.progetto_oop.model.SurveyCollection;
import com.project.progetto_oop.utils.FilterUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

public class FilterService {

    /**
     *Metodo che elabora l'arraylist iniziale filtrandolo tramite il campo field
     * @param filteredSurveys arraylist filtrato di Survey
     * @param initSurveys arraylist iniziale di Survey
     * @param filter Json (preso come string) che rappresenta le istruzioni per filtrare
     * @return filteredSurveys, arraylist di Survey filtrato, se non risulta vuoto, altrimenti initSurveys
     */
    public static ArrayList<Survey> filterArrayList(ArrayList<Survey> filteredSurveys, ArrayList<Survey> initSurveys, String filter) {
        if (filter != null) {
            try {
                JSONObject obj = new JSONObject(filter);
                String operator = (String) obj.keys().next();
                if (filter.indexOf("or") + filter.indexOf("$and") >= 2) {
                    JSONArray jsonArray = obj.getJSONArray(operator);
                    ArrayList<ArrayList<Survey>> arrayListArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ArrayList<Survey> tmp = new ArrayList<>();
                        tmp = filterCheck(initSurveys, jsonArray.getJSONObject(i));
                        arrayListArrayList.add(tmp);
                    }
                    if (operator.contains("$or")) {
                        filteredSurveys = SurveyCollection.or(arrayListArrayList);
                    } else {
                        filteredSurveys = SurveyCollection.and(arrayListArrayList);
                    }
                } else {
                    filteredSurveys = filterCheck(initSurveys, obj);
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong format");

            } catch (JSONException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON not found");
            }
        }
        if (filteredSurveys != null) {
            return filteredSurveys;
        } else {
            return initSurveys;
        }
    }

    /**
     *Metodo che controlla il tipo di filtro (AND, OR)
     * @param arrayList arraylist di Survey
     * @param requestBody oggetto JSON che rappresenta le istruzioni per filtrare
     * @return Restituisce l'insieme di oggetti filtrato ottenuto tramite unione(OR) o intersezione (AND)
     * @throws JSONException dovuta alla conversione in JSON
     */
    private static ArrayList<Survey> filterCheck(ArrayList<Survey> arrayList, JSONObject requestBody) throws JSONException {
        String field = (String) requestBody.keys().next();
        while (requestBody.keys().hasNext()) {
            if (field.contains("$or")) {
                JSONObject json = requestBody;
                JSONArray jsonArray = json.getJSONArray(field);
                ArrayList<ArrayList<Survey>> arrayListArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String newField = (String) jsonArray.getJSONObject(i).keys().next();
                    String operator = (String) jsonArray.getJSONObject(i).getJSONObject(newField).keys().next();
                    ArrayList<Survey> tmp = new ArrayList<>();
                    tmp = checkOperator( arrayList, jsonArray.getJSONObject(i).getJSONObject(newField), operator, newField);
                    arrayListArrayList.add(tmp);
                }
                return SurveyCollection.or(arrayListArrayList);
            } else if (field.contains("$and")) {
                JSONObject json = requestBody;
                JSONArray jsonArray = json.getJSONArray(field);
                ArrayList<ArrayList<Survey>> arrayListArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String newField = (String) jsonArray.getJSONObject(i).keys().next();
                    String operator = (String) jsonArray.getJSONObject(i).getJSONObject(newField).keys().next();
                    ArrayList<Survey> tmp = new ArrayList<>();
                    tmp = checkOperator( arrayList, jsonArray.getJSONObject(i).getJSONObject(newField), operator, newField);
                    arrayListArrayList.add(tmp);
                }
                return SurveyCollection.and(arrayListArrayList);
            } else {
                JSONObject newJson = requestBody.getJSONObject(field);
                String op = (String) newJson.keys().next();
                ArrayList<Survey> tmp = new ArrayList<>();
                tmp = checkOperator(arrayList, newJson, op, field);
                return tmp;
            }

        }
        return arrayList;
    }

    /**
     *Metodo che analizza il tipo di operatore
     * @param arrayList arraylist di Survey
     * @param json oggetto JSON
     * @param op operatore che viene analizzato tramite lo switch
     * @param field campo attraverso cui si effettua il filtro
     * @return arraylist di Survey filtrato in base al tipo di operatore
     * @throws JSONException dovuta alla conversione in JSON
     */
    private static ArrayList<Survey> checkOperator(ArrayList<Survey> arrayList, JSONObject json, String op, String field)
            throws JSONException {
        switch (op) {
            case "$bt":
                Object min = json.getJSONArray(op).get(0);
                Object max = json.getJSONArray(op).get(1);
                return (ArrayList<Survey>) FilterUtils.filter(arrayList, field, op, min, max);
            case "$in":
            case "$nin":
                Object[] values = new Object[json.getJSONArray(op).length()];
                for (int i = 0; i < json.getJSONArray(op).length(); i++) {
                    values[i] = json.getJSONArray(op).get(i);
                }
                return (ArrayList<Survey>) FilterUtils.filter(arrayList,field, op, values);
            case "$eq":
            case "$not":
            case "$lt":
            case "$lte":
            case "$gt":
            case "$gte":
                Object value = json.get(op);
                return (ArrayList<Survey>) FilterUtils.filter(arrayList,field, op, value);
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Operator not correct");
        }

    }

}
