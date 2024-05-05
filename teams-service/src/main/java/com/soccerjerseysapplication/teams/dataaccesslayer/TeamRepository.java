package com.soccerjerseysapplication.teams.dataaccesslayer;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TeamRepository extends MongoRepository<Team, String> {
    Team findTeamByTeamIdentifier_TeamId(String teamIdentifier);

    Team findTeamByName(String name);

    List<Team> findTeamByCountry(String country);

    Team findByTeamIdentifier_TeamId(String teamId);
}
