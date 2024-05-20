package com.soccerjerseysapplication.teams.businesslayer;


import com.soccerjerseysapplication.teams.businesslayer.TeamService;
import com.soccerjerseysapplication.teams.dataaccesslayer.TeamIdentifier;
import com.soccerjerseysapplication.teams.mapperlayer.TeamRequestMapper;
import com.soccerjerseysapplication.teams.dataaccesslayer.Team;
import com.soccerjerseysapplication.teams.dataaccesslayer.TeamRepository;
import com.soccerjerseysapplication.teams.mapperlayer.TeamResponseMapper;
import com.soccerjerseysapplication.teams.presentationlayer.TeamRequestModel;
import com.soccerjerseysapplication.teams.presentationlayer.TeamResponseModel;
import com.soccerjerseysapplication.teams.utils.exceptions.MethodNotAllowedException;
import com.soccerjerseysapplication.teams.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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
        try {
            TeamIdentifier teamIdentifier = new TeamIdentifier(); // Ensure it's properly initialized
            Team team = teamRequestMapper.requestModelToEntity(teamRequestModel, teamIdentifier);
            return teamResponseMapper.entityToResponseModel(teamRepository.save(team));
        } catch (DataAccessException ex) {
            if (ex.getMessage().contains("Method not allowed condition")) {
                throw new MethodNotAllowedException("POST method is not allowed for this operation.");
            }
            throw ex;
        }
    }

//    @Override
//    public TeamResponseModel addTeam(TeamRequestModel teamRequestModel) {
//        Team team = teamRequestMapper.requestModelToEntity(teamRequestModel, new TeamIdentifier());
//        //  try {
//        return teamResponseMapper.entityToResponseModel(teamRepository.save(team));
//        // } catch(DataAccessException ex){ //might throw diffrent exception
//        //if(ex.getMessage().contains(""))
//        //   throw new RuntimeException();
//        //}
//    }

    @Override
    public TeamResponseModel updateTeam(String teamId, TeamRequestModel teamRequestModel) {
        Team existingTeam = teamRepository.findByTeamIdentifier_TeamId(teamId);
        if (existingTeam != null) {
            try {
                TeamIdentifier teamIdentifier = existingTeam.getTeamIdentifier();
                if (teamIdentifier == null) {
                    teamIdentifier = new TeamIdentifier(teamId);
                }
                Team updatedTeam = teamRequestMapper.requestModelToEntity(teamRequestModel, teamIdentifier);
                updatedTeam.setId(existingTeam.getId());
                return teamResponseMapper.entityToResponseModel(teamRepository.save(updatedTeam));
            } catch (DataAccessException ex) {
                if (ex.getMessage().contains("Method not allowed condition")) {
                    throw new MethodNotAllowedException("PUT method is not allowed for this operation.");
                }
                throw ex;
            }
        } else {
            throw new NotFoundException("Unknown teamId: " + teamId);
        }
    }

//    @Override
//    public TeamResponseModel updateTeam(String teamId, TeamRequestModel teamRequestModel) {
//        Team existingTeam = teamRepository.findByTeamIdentifier_TeamId(teamId);
//        if (existingTeam != null) {
//            Team updatedTeam = teamRequestMapper.requestModelToEntity(
//                    teamRequestModel, existingTeam.getTeamIdentifier());
//            updatedTeam.setId(existingTeam.getId());
//            return teamResponseMapper.entityToResponseModel(teamRepository.save(updatedTeam));
//        } else {
//            // Handle the case where jerseys is not found
//            throw new NotFoundException("Unknown teamId: " + teamId);
//        }
//    }

    @Override
    public void deleteTeam(String teamId) {
        Team existingTeam = teamRepository.findByTeamIdentifier_TeamId(teamId);
        if (existingTeam == null) {
            throw new NotFoundException("Unknown teamId: " + teamId);
        }
        teamRepository.delete(existingTeam);
    }
}
