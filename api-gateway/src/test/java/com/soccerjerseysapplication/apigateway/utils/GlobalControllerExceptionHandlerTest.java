package com.soccerjerseysapplication.apigateway.utils;

import com.soccerjerseysapplication.apigateway.utils.exceptions.GlobalControllerExceptionHandler;
import com.soccerjerseysapplication.apigateway.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalControllerExceptionHandlerTest {

    private final GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();

    @Test
    public void testHandleNotFoundException() {
        // Mock the WebRequest and Exception objects
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("test/path");
        NotFoundException ex = new NotFoundException("Not found");

        // Call the method being tested
        HttpErrorInfo errorInfo = handler.handleNotFoundException(request, ex);

        // Check the result
        assertEquals(HttpStatus.NOT_FOUND, errorInfo.getHttpStatus());
        assertEquals("test/path", errorInfo.getPath());
        assertEquals("Not found", errorInfo.getMessage());
    }
}
