//package com.soccerjerseysapplication.apigateway.domainclientlayer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.soccerjerseysapplication.apigateway.domainclientlayer.teams.TeamServiceClient;
//import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamResponseModel;
//import com.soccerjerseysapplication.apigateway.utils.exceptions.NotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.HttpClientErrorException;
//
//import static org.mockito.Mockito.*;
//import static org.mockito.BDDMockito.given;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//public class TeamServiceClientTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private TeamServiceClient teamServiceClient;
//
//    private final String baseUrl = "http://localhost:8080/api/v1/teams";
//
//    @BeforeEach
//    void setup() {
//        teamServiceClient = new TeamServiceClient(restTemplate, "localhost", "8080");
//    }
//
//    @Test
//    void getAllTeams_shouldReturnTeams() {
//        TeamResponseModel[] teamsArray = {
//                new TeamResponseModel("1", "Team One", "Country A"),
//                new TeamResponseModel("2", "Team Two", "Country B")
//        };
//        given(restTemplate.getForObject(baseUrl, TeamResponseModel[].class)).willReturn(teamsArray);
//
//        List<TeamResponseModel> teams = teamServiceClient.getAllTeams();
//
//        assertNotNull(teams);
//        assertEquals(2, teams.size());
//        assertEquals("1", teams.get(0).getTeamId());
//        verify(restTemplate, times(1)).getForObject(baseUrl, TeamResponseModel[].class);
//    }
//
//    @Test
//    void getTeamByTeamId_shouldReturnTeam() {
//        TeamResponseModel expectedTeam = new TeamResponseModel("1", "Team One", "Country A");
//        given(restTemplate.getForObject(baseUrl + "/1", TeamResponseModel.class)).willReturn(expectedTeam);
//
//        TeamResponseModel actualTeam = teamServiceClient.getTeamByTeamId("1");
//
//        assertNotNull(actualTeam);
//        assertEquals("1", actualTeam.getTeamId());
//        verify(restTemplate, times(1)).getForObject(baseUrl + "/1", TeamResponseModel.class);
//    }
//
//    @Test
//    void getTeamByTeamId_whenNotFound_shouldThrowException() {
//        given(restTemplate.getForObject(baseUrl + "/999", TeamResponseModel.class))
//                .willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
//
//        assertThrows(NotFoundException.class, () -> teamServiceClient.getTeamByTeamId("999"));
//    }
//}
//
