package com.project.progetto_oop.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progetto_oop.ProgettoOopApplication;
import com.project.progetto_oop.model.Survey;
import com.project.progetto_oop.utils.StatisticNumber;
import com.project.progetto_oop.utils.StatisticString;
import org.springframework.web.bind.annotation.*;

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
    HashMap<String, StatisticNumber> showStatsNumber(){
        HashMap<String, StatisticNumber> hashMap = new HashMap<>();
        hashMap.put("answers", new StatisticNumber(ProgettoOopApplication.startArrayList,"answers"));
        hashMap.put("subsetAnswers", new StatisticNumber(ProgettoOopApplication.startArrayList,"subsetAnswers"));
        hashMap.put("percentage", new StatisticNumber(ProgettoOopApplication.startArrayList,"percentage"));
        return hashMap;
    }

    @RequestMapping(value = "/stats/string", method = RequestMethod.GET, produces = "application/json")
    HashMap<String, StatisticString> showStatsString(
            @RequestParam String field,
            @RequestParam String value
    ){
        HashMap<String, StatisticString> hashMap = new HashMap<>();
        StatisticString statisticString = new StatisticString(ProgettoOopApplication.startArrayList, value, field);
        //if (statisticString.getUnique() == 0){
            //Throwable new ResponseStatus()

        hashMap.put(field,statisticString);
        return hashMap;
    }

}
