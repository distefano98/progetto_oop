package com.project.progetto_oop.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progetto_oop.ProgettoOopApplication;
import com.project.progetto_oop.model.Survey;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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

}
