package com.soccerjerseysapplication.teams.presentationlayer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.teams.businesslayer.TeamService;
import com.soccerjerseysapplication.teams.presentationlayer.TeamController;
import com.soccerjerseysapplication.teams.presentationlayer.TeamResponseModel;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerIntegrationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TeamService teamService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenGetAllTeams_thenReturnsTeamsAndStatus200() throws Exception {
        List<TeamResponseModel> teams = Arrays.asList(new TeamResponseModel("1", "Team A", "Country A"));
        when(teamService.getAllTeams()).thenReturn(teams);

        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Team A")));
    }

    @Test
    public void givenTeamId_whenGetTeamById_thenReturnsTeamAndStatus200() throws Exception {
        TeamResponseModel team = new TeamResponseModel("1", "Team A", "Country A");
        when(teamService.getTeamByTeamId("1")).thenReturn(team);

        mockMvc.perform(get("/api/v1/teams/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.teamId", is("1")))
                .andExpect(jsonPath("$.name", is("Team A")));
    }

    @Test
    public void givenTeamDetails_whenAddTeam_thenCreatesTeamAndReturnsStatus201() throws Exception {
        TeamResponseModel newTeam = new TeamResponseModel("1", "Team A", "Country A");
        when(teamService.addTeam(any())).thenReturn(newTeam);

        mockMvc.perform(post("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTeam)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.teamId", is("1")))
                .andExpect(jsonPath("$.name", is("Team A")));
    }

    @Test
    public void givenTeamIdAndDetails_whenUpdateTeam_thenUpdatesAndReturnsTeamAndStatus200() throws Exception {
        TeamResponseModel updatedTeam = new TeamResponseModel("1", "Team A Updated", "Country A");
        when(teamService.updateTeam(eq("1"), any())).thenReturn(updatedTeam);

        mockMvc.perform(put("/api/v1/teams/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTeam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Team A Updated")));
    }

    @Test
    public void givenTeamId_whenDeleteTeam_thenDeletesTeamAndReturnsStatus204() throws Exception {
        doNothing().when(teamService).deleteTeam("1");

        mockMvc.perform(delete("/api/v1/teams/1"))
                .andExpect(status().isNoContent());
    }
}
