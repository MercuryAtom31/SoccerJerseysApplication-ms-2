package com.soccerjerseysapplication.teams.utils;

import com.soccerjerseysapplication.teams.dataaccesslayer.Team;
import com.soccerjerseysapplication.teams.dataaccesslayer.TeamIdentifier;
import com.soccerjerseysapplication.teams.dataaccesslayer.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataBaseLoaderService implements CommandLineRunner {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {

        Team team1 = new Team();

        team1.setName("Real Madrid");
        team1.setCountry("Spain");
        team1.setTeamIdentifier(new TeamIdentifier("24"));



        teamRepository.insert(team1);
        //teamRepository.insert(team2);

    }

}
