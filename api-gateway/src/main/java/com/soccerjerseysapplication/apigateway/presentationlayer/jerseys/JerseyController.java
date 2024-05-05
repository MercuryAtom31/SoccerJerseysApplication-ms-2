package com.soccerjerseysapplication.apigateway.presentationlayer.jerseys;

import com.soccerjerseysapplication.apigateway.businesslayer.jerseys.JerseyService;
import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyResponseModel;
import com.soccerjerseysapplication.apigateway.businesslayer.jerseys.JerseyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/jerseys")
public class JerseyController {

    private final JerseyService jerseyService;

    public JerseyController(JerseyService jerseyService) {
        this.jerseyService = jerseyService;
    }

    @GetMapping(
            value = "",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<JerseyResponseModel>> getAllJerseys() {
        return ResponseEntity.ok().body(jerseyService.getAllJerseys());
    }

    @GetMapping(
            value = "/{jerseyId}",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<JerseyResponseModel> getJerseyByJerseyId(@PathVariable String jerseyId) {
        return ResponseEntity.ok().body(jerseyService.getJerseyByJerseyId(jerseyId));
    }

    @PostMapping(
            value = "",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<JerseyResponseModel> createJersey(@RequestBody JerseyRequestModel jerseyRequestModel) {
        return ResponseEntity.ok().body(jerseyService.createJersey(jerseyRequestModel));
    }

    @PutMapping(
            value = "/{jerseyId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<JerseyResponseModel> updateJersey(@PathVariable String jerseyId, @RequestBody JerseyRequestModel jerseyRequestModel) {
        return ResponseEntity.ok().body(jerseyService.updateJersey(jerseyId, jerseyRequestModel));
    }

    @DeleteMapping(
            value = "/{jerseyId}")
    public ResponseEntity<Void> deleteJersey(@PathVariable String jerseyId) {
        jerseyService.deleteJersey(jerseyId);
        return ResponseEntity.noContent().build();
    }
}
