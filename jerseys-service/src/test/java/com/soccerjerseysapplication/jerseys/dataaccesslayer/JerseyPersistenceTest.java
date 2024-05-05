package com.soccerjerseysapplication.jerseys.dataaccesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class JerseyPersistenceTest {

    @Autowired
    private JerseyRepository jerseyRepository;

    @BeforeEach
    public void setup() {
        jerseyRepository.deleteAll();
        jerseyRepository.save(new Jersey(new JerseyIdentifier("J001"), "M", "Red", "Short", 10, 59.99));
        jerseyRepository.save(new Jersey(new JerseyIdentifier("J002"), "L", "Blue", "Long", 5, 64.99));
    }

    @Test
    public void findJerseyByJerseyIdentifier_ShouldReturnJersey() {
        Jersey found = jerseyRepository.findByJerseyIdentifier_JerseyId("J001");
        assertNotNull(found);
        assertEquals("Red", found.getColor());
    }

    @Test
    public void findJerseysByColor_ShouldReturnJerseys() {
        List<Jersey> jerseys = jerseyRepository.findJerseysByColor("Red");
        assertFalse(jerseys.isEmpty());
        assertEquals(1, jerseys.size());
    }

    @Test
    public void findJerseysBySizeAndStyles_ShouldReturnJerseys() {
        List<Jersey> jerseys = jerseyRepository.findJerseysBySizeAndStyles("M", "Short");
        assertFalse(jerseys.isEmpty());
        assertEquals(1, jerseys.size());
    }

    @Test
    public void findJerseysByStockAmountGreaterThan_ShouldReturnJerseys() {
        List<Jersey> jerseys = jerseyRepository.findJerseysByQuantityGreaterThan(4);
        assertFalse(jerseys.isEmpty());
        assertTrue(jerseys.size() >= 1);
    }

    @Test
    public void saveJersey_ShouldPersist() {
        Jersey jersey = new Jersey(new JerseyIdentifier("J003"), "S", "Green", "Short", 15, 49.99);
        Jersey savedJersey = jerseyRepository.save(jersey);
        assertNotNull(savedJersey.getId());
        assertEquals("J003", savedJersey.getJerseyIdentifier().getJerseyId());
    }

    @Test
    public void updateJersey_ShouldChangeData() {
        Jersey found = jerseyRepository.findByJerseyIdentifier_JerseyId("J001");
        assertNotNull(found);
        found.setColor("Black");
        jerseyRepository.save(found);

        Jersey updated = jerseyRepository.findByJerseyIdentifier_JerseyId("J001");
        assertNotNull(updated);
        assertEquals("Black", updated.getColor());
    }

    @Test
    public void deleteJersey_ShouldRemoveData() {
        Jersey found = jerseyRepository.findByJerseyIdentifier_JerseyId("J002");
        assertNotNull(found);
        jerseyRepository.delete(found);

        Jersey deleted = jerseyRepository.findByJerseyIdentifier_JerseyId("J002");
        assertNull(deleted);
    }
}
