package com.project.progetto_oop.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progetto_oop.ProgettoOopApplication;
import com.project.progetto_oop.model.Survey;
import com.project.progetto_oop.model.SurveyCollection;
import com.project.progetto_oop.utils.FilterUtils;
import com.project.progetto_oop.utils.StatisticNumber;
import com.project.progetto_oop.utils.StatisticString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class MainController {

    /**
     * alla richiesta /metadata mostra i metadati creati nella classe Survey
     * @return il mapper che rappresenta i metadati
     * @throws JsonProcessingException dovuta alla costruzione del jsonSchemaGenerator
     */
    @RequestMapping(value = "/metadata", method = RequestMethod.GET, produces="application/json")
    String showMetadata() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(Survey.class);
        return mapper.writeValueAsString(jsonSchema);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    ArrayList<Survey> showData(){
        return ProgettoOopApplication.startArrayList;
    }

    @RequestMapping(value = "/stats/number", method = RequestMethod.GET, produces = "application/json")
    HashMap<String, StatisticNumber> showStatsNumber(
            @RequestParam String field
    ){
        HashMap<String, StatisticNumber> hashMap = new HashMap<>();
        hashMap.put(field, new StatisticNumber(ProgettoOopApplication.startArrayList,field));
        if(hashMap.get(field).getSum() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Field not correct");
        }
        return hashMap;
    }

    @RequestMapping(value = "/stats/string", method = RequestMethod.GET, produces = "application/json")
    HashMap<String, StatisticString> showStatsString(
            @RequestParam String field,
            @RequestParam String value
    ){
        HashMap<String, StatisticString> hashMap = new HashMap<>();
        StatisticString statisticString = new StatisticString(ProgettoOopApplication.startArrayList, value, field);
        hashMap.put(field,statisticString);
        if(hashMap.get(field).getUnique() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Field or value not correct");
        }
        return hashMap;
    }


    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public ArrayList<Survey> showFilteredData(
            @RequestBody String filter
    ){
        ArrayList<Survey> filteredSurveys = null;
        ArrayList<Survey> initSurveys = ProgettoOopApplication.startArrayList;

        if (filter != null) {
            try {
                JSONObject obj = new JSONObject(filter);
                String operator = (String) obj.keys().next();
                if((filter.indexOf("$or") > 1) || (filter.indexOf("$and") > 1) || (filter.indexOf("or") + filter.indexOf("$and") >= 2)){
                    JSONArray jsonArray = obj.getJSONArray(operator);
                    ArrayList<ArrayList<Survey>> arrayListArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ArrayList<Survey> tmp = new ArrayList<>();
                        tmp = filterControl(initSurveys, jsonArray.getJSONObject(i));
                        arrayListArrayList.add(tmp);
                    }if(operator.contains("$or")){
                        filteredSurveys = SurveyCollection.or(arrayListArrayList);
                    }else{
                        filteredSurveys = SurveyCollection.and(arrayListArrayList);
                    }
                } else{
                    filteredSurveys = filterControl(initSurveys, obj);
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong format");

            } catch (JSONException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"JSON not found");
            }
        }
        if (filteredSurveys != null) {
            return filteredSurveys;
        } else {
            return initSurveys;
        }
    }

    private ArrayList<Survey> filterControl(ArrayList<Survey> arrayList, JSONObject requestBody) throws JSONException {
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

    private ArrayList<Survey> checkOperator(ArrayList<Survey> arrayList, JSONObject json, String op, String field)
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
