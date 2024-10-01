package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResponseTest {

    private Response<String> response;

    @BeforeEach
    void setUp() {
        response = new Response<>();
    }

    // Successful tests
    @Test
    void testDefaultConstructor() {
        assertEquals(0, response.getStatusCode());
        assertNull(response.getPayload());
        assertNull(response.getMessage());
    }

    @Test
    void testParameterizedConstructor() {
        Response<String> response = new Response<>(200, "Success", "Operation completed successfully.");
        assertEquals(200, response.getStatusCode());
        assertEquals("Success", response.getPayload());
        assertEquals("Operation completed successfully.", response.getMessage());
    }

    @Test
    void testSettersAndGetters() {
        response.setStatusCode(404);
        response.setPayload("Not Found");
        response.setMessage("The requested resource was not found.");

        assertEquals(404, response.getStatusCode());
        assertEquals("Not Found", response.getPayload());
        assertEquals("The requested resource was not found.", response.getMessage());
    }

    @Test
    void testEmptyPayload() {
        Response<String> response = new Response<>(200, null, "Payload is empty.");
        assertEquals(200, response.getStatusCode());
        assertNull(response.getPayload());
        assertEquals("Payload is empty.", response.getMessage());
    }

    // Failing tests
    @Test
    void testStatusCodeFail() {
        Response<String> response = new Response<>(200, "Success", "Operation completed successfully.");
        assertNotEquals(404, response.getStatusCode(), "Status code should not be 404");
    }

    @Test
    void testPayloadFail() {
        Response<String> response = new Response<>(200, "Success", "Operation completed successfully.");
        assertNotEquals("Failure", response.getPayload(), "Payload should not be 'Failure'");
    }

    @Test
    void testMessageFail() {
        Response<String> response = new Response<>(200, "Success", "Operation completed successfully.");
        assertNotEquals("Error occurred", response.getMessage(), "Message should not be 'Error occurred'");
    }

    @Test
    void testSetInvalidStatusCode() {
        response.setStatusCode(-1);
        assertTrue(response.getStatusCode() < 0, "Status code should be negative");
    }

    @Test
    void testSetInvalidMessage() {
        response.setMessage("");
        assertEquals("", response.getMessage(), "Message should be empty");
    }
}
