//package com.soccerjerseysapplication.apigateway.domainclientlayer;
//
//import com.soccerjerseysapplication.apigateway.domainclientlayer.jerseys.JerseyServiceClient;
//import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyResponseModel;
//import com.soccerjerseysapplication.apigateway.utils.exceptions.NotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.HttpClientErrorException;
//
//import static org.mockito.Mockito.*;
//import static org.mockito.BDDMockito.given;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//public class JerseyServiceClientTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private JerseyServiceClient jerseyServiceClient;
//
//    private final String baseUrl = "http://localhost:8080/api/v1/jerseys";
//
//    @BeforeEach
//    void setup() {
//        jerseyServiceClient = new JerseyServiceClient(restTemplate, "localhost", "8080");
//    }
//
//    @Test
//    void getAllJerseys_shouldReturnJerseys() {
//        JerseyResponseModel[] jerseysArray = {
//                new JerseyResponseModel("1", "M", "Red", "Striped", 10, 59.99, null, null),
//                new JerseyResponseModel("2", "L", "Blue", "Solid", 15, 49.99, null, null)
//        };
//        given(restTemplate.getForObject(baseUrl, JerseyResponseModel[].class)).willReturn(jerseysArray);
//
//        List<JerseyResponseModel> jerseys = jerseyServiceClient.getAllJerseys();
//
//        assertNotNull(jerseys);
//        assertEquals(2, jerseys.size());
//        assertEquals("M", jerseys.get(0).getSize());
//        verify(restTemplate, times(1)).getForObject(baseUrl, JerseyResponseModel[].class);
//    }
//
//    @Test
//    void getJerseyByJerseyId_shouldReturnJersey() {
//        JerseyResponseModel expectedJersey = new JerseyResponseModel("1", "M", "Red", "Striped", 10, 59.99, null, null);
//        given(restTemplate.getForObject(baseUrl + "/1", JerseyResponseModel.class)).willReturn(expectedJersey);
//
//        JerseyResponseModel actualJersey = jerseyServiceClient.getJerseyByJerseyId("1");
//
//        assertNotNull(actualJersey);
//        assertEquals("Red", actualJersey.getColor());
//        verify(restTemplate, times(1)).getForObject(baseUrl + "/1", JerseyResponseModel.class);
//    }
//
//    @Test
//    void getJerseyByJerseyId_whenNotFound_shouldThrowException() {
//        given(restTemplate.getForObject(baseUrl + "/999", JerseyResponseModel.class))
//                .willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
//
//        assertThrows(NotFoundException.class, () -> jerseyServiceClient.getJerseyByJerseyId("999"));
//    }
//}
//
