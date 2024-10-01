package com.example.demo.controller;

import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for getting all users - success
    @Test
    void testGetAllUsers_Success() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPhone("1234567890");
        user1.setCreatedBy("admin");
        user1.setCreatedDatetime(LocalDateTime.now());

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPhone("0987654321");
        user2.setCreatedBy("admin");
        user2.setCreatedDatetime(LocalDateTime.now());

        Response<List<User>> response = new Response<>(HttpStatus.OK.value(), Arrays.asList(user1, user2), "Success");
        when(userService.getAllUsers()).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        // Act
        ResponseEntity<Response<List<User>>> result = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().getPayload().size());
        assertEquals("Success", result.getBody().getMessage());
        verify(userService, times(1)).getAllUsers();
    }

    // Test for getting all users - failure
    @Test
    void testGetAllUsers_Failure() {
        // Arrange
        Response<List<User>> response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.emptyList(), "Failure");
        when(userService.getAllUsers()).thenReturn(new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR));

        // Act
        ResponseEntity<Response<List<User>>> result = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Failure", result.getBody().getMessage());
        verify(userService, times(1)).getAllUsers();
    }

    // Test for getting user by ID - success
    @Test
    void testGetUserById_Success() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setCreatedBy("admin");
        user.setCreatedDatetime(LocalDateTime.now());

        Response<User> response = new Response<>(HttpStatus.OK.value(), user, "Success");
        when(userService.getUserById(anyLong())).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        // Act
        ResponseEntity<Response<User>> result = userController.getUserById(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("John", result.getBody().getPayload().getFirstName());
        assertEquals("Success", result.getBody().getMessage());
        verify(userService, times(1)).getUserById(1L);
    }

    // Test for getting user by ID - failure
    @Test
    void testGetUserById_Failure() {
        // Arrange
        when(userService.getUserById(anyLong())).thenReturn(new ResponseEntity<>(new Response<>(HttpStatus.NOT_FOUND.value(), null, "User not found"), HttpStatus.NOT_FOUND));

        // Act
        ResponseEntity<Response<User>> result = userController.getUserById(999L); // Assuming this ID doesn't exist

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found", result.getBody().getMessage());
        verify(userService, times(1)).getUserById(999L);
    }

    // Test for creating a user - success
    @Test
    void testCreateUser_Success() {
        // Arrange
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setCreatedBy("admin");
        user.setCreatedDatetime(LocalDateTime.now());

        Response<User> response = new Response<>(HttpStatus.CREATED.value(), user, "User created");
        when(userService.createUser(any(User.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));

        // Act
        ResponseEntity<Response<User>> result = userController.createUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("User created", result.getBody().getMessage());
        verify(userService, times(1)).createUser(user);
    }

    // Test for creating a user - failure
    @Test
    void testCreateUser_Failure() {
        // Arrange
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");

        when(userService.createUser(any(User.class))).thenReturn(new ResponseEntity<>(new Response<>(HttpStatus.BAD_REQUEST.value(), null, "Invalid user data"), HttpStatus.BAD_REQUEST));

        // Act
        ResponseEntity<Response<User>> result = userController.createUser(user);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid user data", result.getBody().getMessage());
        verify(userService, times(1)).createUser(user);
    }

    // Test for updating a user - success
    @Test
    void testUpdateUser_Success() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setCreatedBy("admin");
        user.setCreatedDatetime(LocalDateTime.now());

        Response<User> response = new Response<>(HttpStatus.OK.value(), user, "User updated");
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        // Act
        ResponseEntity<Response<User>> result = userController.updateUser(1L, user);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User updated", result.getBody().getMessage());
        verify(userService, times(1)).updateUser(1L, user);
    }

    // Test for updating a user - failure
    @Test
    void testUpdateUser_Failure() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");

        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(new ResponseEntity<>(new Response<>(HttpStatus.NOT_FOUND.value(), null, "User not found"), HttpStatus.NOT_FOUND));

        // Act
        ResponseEntity<Response<User>> result = userController.updateUser(999L, user); // Assuming this ID doesn't exist

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found", result.getBody().getMessage());
        verify(userService, times(1)).updateUser(999L, user);
    }

    // Test for deleting a user - success
    @Test
    void testDeleteUser_Success() {
        // Arrange
        Response<Void> response = new Response<>(HttpStatus.NO_CONTENT.value(), null, "User deleted");
        when(userService.deleteUser(anyLong())).thenReturn(new ResponseEntity<>(response, HttpStatus.NO_CONTENT));

        // Act
        ResponseEntity<Response<Void>> result = userController.deleteUser(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals("User deleted", result.getBody().getMessage());
        verify(userService, times(1)).deleteUser(1L);
    }

    // Test for deleting a user - failure
    @Test
    void testDeleteUser_Failure() {
        // Arrange
        when(userService.deleteUser(anyLong())).thenReturn(new ResponseEntity<>(new Response<>(HttpStatus.NOT_FOUND.value(), null, "User not found"), HttpStatus.NOT_FOUND));

        // Act
        ResponseEntity<Response<Void>> result = userController.deleteUser(999L); // Assuming this ID doesn't exist

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found", result.getBody().getMessage());
        verify(userService, times(1)).deleteUser(999L);
    }
}
