package com.soccerjerseysapplication.apigateway.businesslayer.teams;
//
//
//
import com.soccerjerseysapplication.apigateway.businesslayer.teams.TeamService;

import com.soccerjerseysapplication.apigateway.datamapperlayer.teams.TeamResponseMapper;
import com.soccerjerseysapplication.apigateway.domainclientlayer.teams.TeamServiceClient;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamResponseModel;
import com.soccerjerseysapplication.apigateway.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {


    private final TeamServiceClient teamServiceClient;
    private final TeamResponseMapper teamResponseMapper;

    public TeamServiceImpl(TeamServiceClient teamServiceClient, TeamResponseMapper teamResponseMapper) {
        this.teamServiceClient = teamServiceClient;
        this.teamResponseMapper = teamResponseMapper;
    }

    @Override
    public List<TeamResponseModel> getAllTeams() {
        return teamResponseMapper.responseModelListToResponseModelList(teamServiceClient.getAllTeams());
    }

    @Override
    public TeamResponseModel getTeamByTeamId(String teamId) {
        return teamResponseMapper.responseModelToResponseModel(teamServiceClient.getTeamByTeamId(teamId));
    }

    @Override
    public TeamResponseModel createTeam(TeamRequestModel teamRequestModel) {
        return teamResponseMapper.responseModelToResponseModel(teamServiceClient.createTeam(teamRequestModel));
    }

    @Override
    public TeamResponseModel updateTeam(String teamId, TeamRequestModel teamRequestModel) {
        return teamResponseMapper.responseModelToResponseModel(teamServiceClient.updateTeam(teamId, teamRequestModel));
    }

    @Override
    public void deleteTeam(String teamId) {
        teamServiceClient.deleteTeam(teamId);
    }
}
