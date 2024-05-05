package com.soccerjerseysapplication.apigateway.businesslayer.jerseys;


import com.soccerjerseysapplication.apigateway.datamapperlayer.jerseys.JerseyResponseMapper;
import com.soccerjerseysapplication.apigateway.domainclientlayer.jerseys.JerseyServiceClient;
import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyResponseModel;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JerseyServiceImpl implements JerseyService {

    private final JerseyServiceClient jerseyServiceClient;
    private final JerseyResponseMapper jerseyResponseMapper;

    public JerseyServiceImpl(JerseyServiceClient jerseyServiceClient, JerseyResponseMapper jerseyResponseMapper) {
        this.jerseyServiceClient = jerseyServiceClient;
        this.jerseyResponseMapper = jerseyResponseMapper;
    }

    @Override
    public List<JerseyResponseModel> getAllJerseys() {
        return jerseyResponseMapper.responseModelListToResponseModelList(jerseyServiceClient.getAllJerseys());
    }

    @Override
    public JerseyResponseModel getJerseyByJerseyId(String jerseyId) {
        return jerseyResponseMapper.responseModelToResponseModel(jerseyServiceClient.getJerseyByJerseyId(jerseyId));
    }

    @Override
    public JerseyResponseModel createJersey(JerseyRequestModel jerseyRequestModel) {
        return jerseyResponseMapper.responseModelToResponseModel(jerseyServiceClient.createJersey(jerseyRequestModel));
    }

    @Override
    public JerseyResponseModel updateJersey(String jerseyId, JerseyRequestModel jerseyRequestModel) {
        return jerseyResponseMapper.responseModelToResponseModel(jerseyServiceClient.updateJersey(jerseyId, jerseyRequestModel));
    }

    @Override
    public void deleteJersey(String jerseyId) {
        jerseyServiceClient.deleteJersey(jerseyId);
    }
    
}
