package com.soccerjerseysapplication.teams.businesslayer;


import com.soccerjerseysapplication.teams.businesslayer.TeamService;
import com.soccerjerseysapplication.teams.dataaccesslayer.TeamIdentifier;
import com.soccerjerseysapplication.teams.mapperlayer.TeamRequestMapper;
import com.soccerjerseysapplication.teams.dataaccesslayer.Team;
import com.soccerjerseysapplication.teams.dataaccesslayer.TeamRepository;
import com.soccerjerseysapplication.teams.mapperlayer.TeamResponseMapper;
import com.soccerjerseysapplication.teams.presentationlayer.TeamRequestModel;
import com.soccerjerseysapplication.teams.presentationlayer.TeamResponseModel;
import com.soccerjerseysapplication.teams.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamResponseMapper teamResponseMapper;
    private final TeamRequestMapper teamRequestMapper;

    public TeamServiceImpl(TeamRepository teamRepository, TeamResponseMapper teamResponseMapper, TeamRequestMapper teamRequestMapper) {
        this.teamRepository = teamRepository;
        this.teamResponseMapper = teamResponseMapper;
        this.teamRequestMapper = teamRequestMapper;
    }

    @Override
    public List<TeamResponseModel> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teamResponseMapper.entityListToResponseModelList(teams);
    }

    @Override
    public TeamResponseModel getTeamByTeamId(String teamId) {
        Team team = teamRepository.findTeamByTeamIdentifier_TeamId(teamId);
        if (team != null) {
            return teamResponseMapper.entityToResponseModel(team);
        } else {
            // Handle the case where team is not found
            throw new NotFoundException("Unknown teamId provided " + teamId);
        }
    }

    @Override
    public TeamResponseModel addTeam(TeamRequestModel teamRequestModel) {
        Team team = teamRequestMapper.requestModelToEntity(teamRequestModel, new TeamIdentifier());
        return teamResponseMapper.entityToResponseModel(teamRepository.save(team));
    }

    @Override
    public TeamResponseModel updateTeam(String teamId, TeamRequestModel teamRequestModel) {
        Team existingTeam = teamRepository.findByTeamIdentifier_TeamId(teamId);
        if (existingTeam != null) {
            Team updatedTeam = teamRequestMapper.requestModelToEntity(
                    teamRequestModel, existingTeam.getTeamIdentifier());
            updatedTeam.setId(existingTeam.getId());
            return teamResponseMapper.entityToResponseModel(teamRepository.save(updatedTeam));
        } else {
            // Handle the case where jerseys is not found
            throw new NotFoundException("Unknown teamId: " + teamId);
        }
    }

    @Override
    public void deleteTeam(String teamId) {
        Team existingTeam = teamRepository.findByTeamIdentifier_TeamId(teamId);
        if (existingTeam == null) {
            throw new NotFoundException("Unknown teamId: " + teamId);
        }
        teamRepository.delete(existingTeam);
    }
}
