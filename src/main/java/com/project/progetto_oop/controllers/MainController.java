package com.project.progetto_oop.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progetto_oop.ProgettoOopApplication;
import com.project.progetto_oop.model.Survey;
import com.project.progetto_oop.service.FilterService;
import com.project.progetto_oop.utils.StatisticNumber;
import com.project.progetto_oop.utils.StatisticString;
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
        return FilterService.filterArrayList(filteredSurveys,initSurveys,filter);
    }

}
