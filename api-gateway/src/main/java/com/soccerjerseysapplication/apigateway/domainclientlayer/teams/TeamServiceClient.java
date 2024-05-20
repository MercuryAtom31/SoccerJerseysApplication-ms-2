package com.soccerjerseysapplication.apigateway.domainclientlayer.teams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamResponseModel;
import com.soccerjerseysapplication.apigateway.utils.HttpErrorInfo;
import com.soccerjerseysapplication.apigateway.utils.exceptions.InvalidInputException;
import com.soccerjerseysapplication.apigateway.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class TeamServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String TEAM_SERVICE_BASE_URL;

    public TeamServiceClient(RestTemplate restTemplate,
                             ObjectMapper mapper,
                             @Value("${app.teams-service.host}") String teamServiceHost,
                             @Value("${app.teams-service.port}") String teamServicePort) {
        this.restTemplate = restTemplate;
       this.objectMapper = mapper;

        TEAM_SERVICE_BASE_URL  = "http://" + teamServiceHost + ":" + teamServicePort + "/api/v1/teams";
    }

    public List<TeamResponseModel> getAllTeams() {
        try {
            String url = TEAM_SERVICE_BASE_URL;

            TeamResponseModel [] teamResponseModel = restTemplate.getForObject(url, TeamResponseModel[].class);

            return Arrays.asList(teamResponseModel);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public TeamResponseModel getTeamByTeamId(String teamId) {
        try {
            String url = TEAM_SERVICE_BASE_URL + "/" + teamId;
            TeamResponseModel teamResponseModel = restTemplate.getForObject(url, TeamResponseModel.class);

            return teamResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public TeamResponseModel createTeam(TeamRequestModel teamRequestModel) {
        try {
            String url = TEAM_SERVICE_BASE_URL;

            TeamResponseModel teamResponseModel = restTemplate.postForObject(url, teamRequestModel, TeamResponseModel.class);

            return teamResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public TeamResponseModel updateTeam(String teamId, TeamRequestModel teamRequestModel) {
        try {
            String url = TEAM_SERVICE_BASE_URL + "/" + teamId;

            restTemplate.put(url, teamRequestModel);

            return getTeamByTeamId(teamId);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteTeam(String teamId) {
        try {
            String url = TEAM_SERVICE_BASE_URL + "/" + teamId;

            restTemplate.delete(url);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public RuntimeException handleHttpClientException(HttpClientErrorException ex) {

        //include all possible responses from the client
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }

    public String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }
}
