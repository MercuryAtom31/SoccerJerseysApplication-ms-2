package com.soccerjerseysapplication.jerseys.presentationlayer;

import com.soccerjerseysapplication.jerseys.dataaccesslayer.Jersey;
import com.soccerjerseysapplication.jerseys.dataaccesslayer.JerseyIdentifier;
import com.soccerjerseysapplication.jerseys.dataaccesslayer.JerseyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
//@DataJpaTest
public class JerseyControllerIntegrationTest {

    private final String BASE_URI_JERSEYS = "/api/v1/jerseys";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JerseyRepository jerseyRepository;

    @AfterEach
    public void tearDown() {
        jerseyRepository.deleteAll();
    }

    private Jersey createJersey() {
        return new Jersey(new JerseyIdentifier(), "M", "Red", "Short", 10, 59.99);
    }

    @Sql({"/data-h2.sql"})
    @Test
    public void whenGetJerseyById_thenReturnJerseyDetails() {
        // Arrange
        Jersey jersey = createJersey();
        jersey = jerseyRepository.save(jersey);
        Long jerseyId = jersey.getId();

        // Act & Assert
        webTestClient.get()
                .uri(BASE_URI_JERSEYS + "/{id}", jerseyId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size").isEqualTo("M")
                .jsonPath("$.color").isEqualTo("Red")
                .jsonPath("$.styles").isEqualTo("Short")
                .jsonPath("$.stockAmount").isEqualTo(10)
                .jsonPath("$.price").isEqualTo(59.99);
    }

    @Sql({"/data-h2.sql"})
    @Test
    public void whenCreateJersey_thenReturnCreatedJersey() {
        // Arrange
        Jersey jersey = createJersey();

        // Act & Assert
        webTestClient.post()
                .uri(BASE_URI_JERSEYS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jersey)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.size").isEqualTo("M")
                .jsonPath("$.color").isEqualTo("Red")
                .jsonPath("$.styles").isEqualTo("Short")
                .jsonPath("$.stockAmount").isEqualTo(10)
                .jsonPath("$.price").isEqualTo(59.99);
    }

    // For updating a Jersey
    @Test
    public void whenUpdateJersey_thenReturnUpdatedJersey() {
        // Arrange
        Jersey jersey = createJersey();
        jersey = jerseyRepository.save(jersey);
        jersey.setColor("Blue");

        // Act & Assert
        webTestClient.put()
                .uri(BASE_URI_JERSEYS + "/{id}", jersey.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jersey)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.color").isEqualTo("Blue");
    }

    // For deleting a Jersey
    @Test
    public void whenDeleteJersey_thenStatusNoContent() {
        // Arrange
        Jersey jersey = createJersey();
        jersey = jerseyRepository.save(jersey);

        // Act & Assert
        webTestClient.delete()
                .uri(BASE_URI_JERSEYS + "/{id}", jersey.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
}
