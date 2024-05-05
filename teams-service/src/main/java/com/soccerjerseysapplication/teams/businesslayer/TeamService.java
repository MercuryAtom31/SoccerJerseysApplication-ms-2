package com.soccerjerseysapplication.teams.businesslayer;

import com.soccerjerseysapplication.teams.presentationlayer.TeamRequestModel;
import com.soccerjerseysapplication.teams.presentationlayer.TeamResponseModel;

import java.util.List;

public interface TeamService {


    List<TeamResponseModel> getAllTeams();


    TeamResponseModel addTeam(TeamRequestModel teamRequestModel);


    TeamResponseModel getTeamByTeamId(String teamId);


    TeamResponseModel updateTeam(String teamId, TeamRequestModel requestModel);


    void deleteTeam(String teamId);


}
