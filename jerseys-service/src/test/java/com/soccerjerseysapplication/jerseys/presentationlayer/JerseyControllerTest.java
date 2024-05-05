package com.soccerjerseysapplication.jerseys.presentationlayer;

import com.soccerjerseysapplication.jerseys.businesslayer.JerseyService;
import com.soccerjerseysapplication.jerseys.dataaccesslayer.Jersey;
import com.soccerjerseysapplication.jerseys.dataaccesslayer.JerseyIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JerseyController.class)
public class JerseyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JerseyService jerseyService;

    private Jersey jersey;

    @BeforeEach
    public void setup() {
        jersey = new Jersey(new JerseyIdentifier(),"M", "Red", "Short", 10, 59.99);
//        Mockito.when(jerseyService.getJerseyById(any())).thenReturn(jerseys);
//        Mockito.when(jerseyService.addJersey(any(Jersey.class))).thenReturn(jerseys);
    }

    @Test
    public void whenGetJerseyById_thenReturnJersey() throws Exception {
        mockMvc.perform(get("/api/v1/jerseys/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCreateJersey_thenReturnCreatedJersey() throws Exception {
        mockMvc.perform(post("/api/v1/jerseys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"jerseyId\":\"J001\",\"size\":\"M\",\"color\":\"Red\",\"styles\":\"Short\",\"stockAmount\":10,\"price\":59.99}"))
                .andExpect(status().isCreated());
    }
}
