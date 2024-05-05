package com.soccerjerseysapplication.teams.dataaccesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@EnableMongoRepositories(basePackageClasses = TeamRepository.class)
@DataMongoTest
class TeamPersistenceTest {

    @Autowired
    private TeamRepository teamRepository;

    private Team preSavedTeam;

    @BeforeEach
    public void setup(){
        teamRepository.deleteAll();
        preSavedTeam = teamRepository.save(new Team(new TeamIdentifier(), "Liverpool", "England"));
    }

//    @Test
//    public void saveNewTeam_shouldSucceed(){
//        // Arrange
//        Team team = new Team(new TeamIdentifier(), "Manchester United", "England");
//
//        // Act
//        Team savedTeam = teamRepository.save(team);
//
//        // Assert
//        assertNotNull(savedTeam);
//        assertEquals("Manchester United", savedTeam.getName());
//        assertEquals("England", savedTeam.getCountry());
//    }
//
//    @Test
//    public void updateTeam_shouldSucceed(){
//        // Arrange
//        preSavedTeam.setName("Updated Name");
//
//        // Act
//        Team updatedTeam = teamRepository.save(preSavedTeam);
//
//        // Assert
//        assertEquals(updatedTeam, preSavedTeam);
//        assertEquals("Updated Name", updatedTeam.getName());
//    }
//
//    @Test
//    public void deleteTeam_shouldSucceed(){
//        // Act
//        teamRepository.delete(preSavedTeam);
//        Team found = teamRepository.findByTeamIdentifier_TeamId(preSavedTeam.getTeamIdentifier().getTeamId());
//
//        // Assert
//        assertNull(found);
//    }

    @Test
    public void findTeamByTeamIdentifier_TeamId_shouldSucceed(){
        // Act
        Team found = teamRepository.findByTeamIdentifier_TeamId(preSavedTeam.getTeamIdentifier().getTeamId());

        // Assert
        assertNotNull(found);
        assertEquals(preSavedTeam, found);
    }

    @Test
    public void findTeamByInvalidTeamIdentifier_TeamId_shouldReturnNull(){
        // Act
        Team found = teamRepository.findByTeamIdentifier_TeamId("invalidId");

        // Assert
        assertNull(found);
    }

    @Test
    public void findTeamByCountry_shouldReturnNotEmptyList(){
        // Arrange
        Team team = new Team(new TeamIdentifier(), "Manchester City", "England");
        teamRepository.save(team);

        // Act
        List<Team> foundTeams = teamRepository.findTeamByCountry("England");

        // Assert
        assertNotNull(foundTeams);
        assertFalse(foundTeams.isEmpty());
        assertTrue(foundTeams.stream().anyMatch(t -> t.getCountry().equals("England")));
    }

    @Test
    public void findTeamByName_shouldSucceed(){
        // Act
        Team found = teamRepository.findTeamByName(preSavedTeam.getName());

        // Assert
        assertNotNull(found);
        assertEquals(preSavedTeam.getName(), found.getName());
    }
}