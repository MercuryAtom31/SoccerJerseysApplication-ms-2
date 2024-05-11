package com.soccerjerseysapplication.jerseys.presentationlayer;

import com.soccerjerseysapplication.jerseys.businesslayer.JerseyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/jerseys")
public class JerseyController {


    private final JerseyService jerseyService;

    public JerseyController(JerseyService jerseyService) {
        this.jerseyService = jerseyService;
    }

    @GetMapping
    public ResponseEntity<List<JerseyResponseModel>> getAllJerseys() {
        List<JerseyResponseModel> jerseys = jerseyService.getAllJerseys();
        return ResponseEntity.ok(jerseys);
    }

    @GetMapping("/{jerseyId}")
    public ResponseEntity<JerseyResponseModel> getJerseyById(@PathVariable String jerseyId) {
        JerseyResponseModel jersey = jerseyService.getJerseyById(jerseyId);
        return ResponseEntity.ok(jersey);
    }

    @PostMapping
    public ResponseEntity<JerseyResponseModel> addJersey(@RequestBody JerseyRequestModel jerseyRequestModel) {
        JerseyResponseModel createdJersey = jerseyService.addJersey(jerseyRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJersey);
    }

    @PutMapping("/{jerseyId}")
    public ResponseEntity<JerseyResponseModel> updateJersey(@PathVariable String jerseyId,
                                                            @Valid @RequestBody JerseyRequestModel jerseyRequestModel) {
        JerseyResponseModel updatedJersey = jerseyService.updateJersey(jerseyId, jerseyRequestModel);
        return ResponseEntity.ok(updatedJersey);
    }

    @DeleteMapping("/{jerseyId}")
    public ResponseEntity<Void> deleteJersey(@PathVariable String jerseyId) {
        jerseyService.deleteJersey(jerseyId);
        return ResponseEntity.noContent().build();
    }

}
