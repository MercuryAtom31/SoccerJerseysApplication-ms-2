package com.soccerjerseysapplication.teams.dataaccesslayer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@DataMongoTest
class TeamRepositoryTests {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Team.class);  // Ensure each test starts with a clean state
    }

    private Team createNewTeam(String teamId, String name, String country) {
        return new Team(new TeamIdentifier(teamId), name, country);
    }

    @Test
    void saveTeamAndFindByTeamIdentifier_ShouldSucceed() {
        // Arrange
        Team team = createNewTeam("team1", "FC Barcelona", "Spain");
        teamRepository.save(team);

        // Act
        Team found = teamRepository.findByTeamIdentifier_TeamId("team1");

        // Assert
        assertNotNull(found);
        assertEquals("FC Barcelona", found.getName());
        assertEquals("Spain", found.getCountry());
    }

    @Test
    void findByNonexistentTeamIdentifier_ShouldFail() {
        // Act
        Team found = teamRepository.findByTeamIdentifier_TeamId("nonexistent-id");

        // Assert
        assertNull(found);
    }

    @Test
    void saveTeamAndFindByName_ShouldSucceed() {
        // Arrange
        Team team = createNewTeam("team2", "Real Madrid", "Spain");
        teamRepository.save(team);

        // Act
        Team found = teamRepository.findTeamByName("Real Madrid");

        // Assert
        assertNotNull(found);
        assertEquals("team2", found.getTeamIdentifier().getTeamId());
    }

    @Test
    void findByNonexistentName_ShouldFail() {
        // Act
        Team found = teamRepository.findTeamByName("Nonexistent Team");

        // Assert
        assertNull(found);
    }

    @Test
    void saveMultipleTeamsAndFindByCountry_ShouldSucceed() {
        // Arrange
        Team team1 = createNewTeam("team1", "FC Barcelona", "Spain");
        Team team2 = createNewTeam("team2", "Real Madrid", "Spain");
        teamRepository.saveAll(List.of(team1, team2));

        // Act
        List<Team> foundTeams = teamRepository.findTeamByCountry("Spain");

        // Assert
        assertEquals(2, foundTeams.size());
        assertTrue(foundTeams.stream().anyMatch(t -> t.getName().equals("FC Barcelona")));
        assertTrue(foundTeams.stream().anyMatch(t -> t.getName().equals("Real Madrid")));
    }

    @Test
    void deleteTeam_ShouldSucceed() {
        // Arrange
        Team team = createNewTeam("team3", "Liverpool", "England");
        teamRepository.save(team);

        // Act
        teamRepository.delete(team);
        Team found = teamRepository.findByTeamIdentifier_TeamId("team3");

        // Assert
        assertNull(found);
    }

    @Test
    void updateTeamName_ShouldSucceed() {
        // Arrange
        Team team = createNewTeam("team4", "Manchester United", "England");
        teamRepository.save(team);

        // Act
        team.setName("Manchester City");
        teamRepository.save(team);
        Team updatedTeam = teamRepository.findTeamByName("Manchester City");

        // Assert
        assertNotNull(updatedTeam);
        assertEquals("Manchester City", updatedTeam.getName());
    }

    @Test
    void findByCountryWithNoTeams_ShouldFail() {
        // Act
        List<Team> foundTeams = teamRepository.findTeamByCountry("Nonexistent Country");

        // Assert
        assertTrue(foundTeams.isEmpty());
    }
}
