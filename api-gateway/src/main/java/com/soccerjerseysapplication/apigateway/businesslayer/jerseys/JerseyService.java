package com.soccerjerseysapplication.apigateway.businesslayer.jerseys;

import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyResponseModel;


import java.util.List;

public interface JerseyService {

    List<JerseyResponseModel> getAllJerseys();
    JerseyResponseModel getJerseyByJerseyId(String jerseyId);
    JerseyResponseModel createJersey(JerseyRequestModel jerseyRequestModel);
    JerseyResponseModel updateJersey(String jerseyId, JerseyRequestModel jerseyRequestModel);
    void deleteJersey(String jerseyId);

}
