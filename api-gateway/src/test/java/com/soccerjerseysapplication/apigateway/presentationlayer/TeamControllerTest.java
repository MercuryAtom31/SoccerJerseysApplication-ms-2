package com.soccerjerseysapplication.apigateway.presentationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.apigateway.businesslayer.teams.TeamService;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamController;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(TeamController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @BeforeEach
    void setup() {
        // Setup for tests
    }

    @Test
    void getAllTeams_shouldReturnTeams() throws Exception {
        List<TeamResponseModel> teams = List.of(
                new TeamResponseModel("1", "Team One", "Country A")
        );
        given(teamService.getAllTeams()).willReturn(teams);

        mockMvc.perform(get("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teamId").value("1"));
    }

    @Test
    void getTeamById_shouldReturnTeam() throws Exception {
        TeamResponseModel team = new TeamResponseModel("1", "Team One", "Country A");
        given(teamService.getTeamByTeamId("1")).willReturn(team);

        mockMvc.perform(get("/api/v1/teams/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamId").value("1"));
    }

    @Test
    void createTeam_shouldReturnCreatedTeam() throws Exception {
        TeamRequestModel request = new TeamRequestModel("Team Three", "Country C");
        TeamResponseModel response = new TeamResponseModel("3", "Team Three", "Country C");

        given(teamService.createTeam(request)).willReturn(response);

        mockMvc.perform(post("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamId").value("3"));
    }
}

