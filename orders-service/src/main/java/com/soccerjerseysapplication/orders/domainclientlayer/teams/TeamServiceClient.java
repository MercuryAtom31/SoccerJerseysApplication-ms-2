package com.soccerjerseysapplication.orders.domainclientlayer.teams;

import com.fasterxml.jackson.databind.ObjectMapper;



import com.soccerjerseysapplication.customers.utils.exceptions.NotFoundException;
import com.soccerjerseysapplication.orders.utils.HttpErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Component
public class TeamServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String  TEAM_SERVICE_BASE_URL;

    public TeamServiceClient(RestTemplate restTemplate,
                                 ObjectMapper objectMapper,
                                 @Value("${app.teams-service.host}") String teamServiceHost,
                                 @Value("${app.teams-service.port}") String teamServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.TEAM_SERVICE_BASE_URL = "http://" + teamServiceHost + ":" + teamServicePort + "/api/v1/teams";;
    }

    public TeamResponseModel getTeamById(String teamId){
        try{
            String url = TEAM_SERVICE_BASE_URL + "/"+ teamId;
            return restTemplate.getForObject(url, TeamResponseModel.class);
        } catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }

    }

    public String getErrorMessage(HttpClientErrorException ex) {
        try{
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex){
            return ioex.getMessage();
        }
    }
    private RuntimeException handleHttpClientException(HttpClientErrorException ex){
        //Adding all the exceptions that the service throws
        if (ex.getStatusCode() == NOT_FOUND)
            return new NotFoundException(getErrorMessage(ex));
        return ex;
    }
}


//package com.soccerjerseysapplication.orders.domainclientlayer.teams;
//
//import com.soccerjerseysapplication.orders.domainclientlayer.BaseServiceClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Slf4j
//@Component
//public class TeamServiceClient extends BaseServiceClient {
//
//    private final String teamServiceBaseUrl;
//
//    public TeamServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
//                             @Value("${app.team-service.host}") String teamServiceHost,
//                             @Value("${app.team-service.port}") String teamServicePort) {
//        super(restTemplate, objectMapper);
//        this.teamServiceBaseUrl = "http://" + teamServiceHost + ":" + teamServicePort + "/api/v1/teams";
//    }
//
//    public TeamResponseModel getTeamById(String teamId) {
//        String url = teamServiceBaseUrl + "/" + teamId;
//        return getForObject(url, TeamResponseModel.class);
//    }
//}
