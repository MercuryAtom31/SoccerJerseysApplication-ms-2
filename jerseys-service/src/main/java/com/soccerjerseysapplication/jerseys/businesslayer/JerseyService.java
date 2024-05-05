package com.soccerjerseysapplication.jerseys.businesslayer;

import com.soccerjerseysapplication.jerseys.dataaccesslayer.Jersey;
import com.soccerjerseysapplication.jerseys.presentationlayer.JerseyRequestModel;
import com.soccerjerseysapplication.jerseys.presentationlayer.JerseyResponseModel;

import java.util.List;

public interface JerseyService {

    List<JerseyResponseModel> getAllJerseys();
    //JerseyResponseModel getJerseyByJerseyId(String jerseyId);
    JerseyResponseModel updateJersey(String jerseyId, JerseyRequestModel jerseyRequestModel);
    JerseyResponseModel addJersey(JerseyRequestModel jerseyRequestModel);
    void deleteJersey(String jerseyId);


    JerseyResponseModel getJerseyById(String jerseyId);
}
