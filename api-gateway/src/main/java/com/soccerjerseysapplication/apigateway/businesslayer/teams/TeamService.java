package com.soccerjerseysapplication.apigateway.businesslayer.teams;



import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamResponseModel;

import java.util.List;

public interface TeamService {

    List<TeamResponseModel> getAllTeams();
    TeamResponseModel getTeamByTeamId(String teamId);
    TeamResponseModel createTeam(TeamRequestModel teamRequestModel);
    TeamResponseModel updateTeam(String teamId, TeamRequestModel teamRequestModel);
    void deleteTeam(String teamId);
}
