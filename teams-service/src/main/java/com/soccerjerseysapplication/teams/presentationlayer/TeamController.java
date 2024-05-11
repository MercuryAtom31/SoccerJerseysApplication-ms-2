package com.soccerjerseysapplication.teams.presentationlayer;


import com.soccerjerseysapplication.teams.businesslayer.TeamService;
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

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping()
    public ResponseEntity<List<TeamResponseModel>> getAllTeams(){
        List<TeamResponseModel> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseModel> getTeamById(@PathVariable String teamId) {
        TeamResponseModel teamResponseModel = teamService.getTeamByTeamId(teamId);
        if (teamResponseModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teamResponseModel, HttpStatus.OK);
    }

    @PostMapping("/teams")
    public ResponseEntity<TeamResponseModel> addTeam(@PathVariable String teamId, @RequestBody TeamRequestModel teamRequestModel) {
        TeamResponseModel createdTeam = teamService.addTeam(teamRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponseModel> updateTeam(@PathVariable String teamId,
                                                            @Valid @RequestBody TeamRequestModel teamRequestModel) {
        TeamResponseModel updatedTeam = teamService.updateTeam(teamId, teamRequestModel);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}
