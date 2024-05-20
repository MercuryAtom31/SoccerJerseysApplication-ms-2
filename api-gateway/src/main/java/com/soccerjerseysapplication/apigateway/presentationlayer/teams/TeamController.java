package com.soccerjerseysapplication.apigateway.presentationlayer.teams;

//
//import com.soccerjerseysapplication.teams.businesslayer.TeamService;
import com.soccerjerseysapplication.apigateway.businesslayer.teams.TeamService;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamResponseModel;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(
            value = "",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<TeamResponseModel>> getAllTeams() {
        return ResponseEntity.ok().body(teamService.getAllTeams());
    }

    @GetMapping(
            value = "/{teamId}",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TeamResponseModel> getTeamByTeamId(@PathVariable String teamId) {
        return ResponseEntity.ok().body(teamService.getTeamByTeamId(teamId));
    }

    @PostMapping(
            value = "",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TeamResponseModel> createTeam(@RequestBody TeamRequestModel teamRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(teamRequestModel));
    }

    @PutMapping(
            value = "/{teamId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TeamResponseModel> updateTeam(@PathVariable String teamId, @RequestBody TeamRequestModel teamRequestModel) {
        return ResponseEntity.ok().body(teamService.updateTeam(teamId, teamRequestModel));
    }

    @DeleteMapping(
            value = "/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}
