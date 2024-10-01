package com.example.demo.service;

import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setCreatedBy("Admin");
        user.setCreatedDatetime(LocalDateTime.now());
    }

    @Test
    public void testGetAllUsers_Success() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        // Act
        ResponseEntity<Response<List<User>>> response = userService.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(1, response.getBody().getPayload().size());
        assertEquals("Users retrieved successfully.", response.getBody().getMessage());
    }

    @Test
    public void testGetAllUsers_EmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<Response<List<User>>> response = userService.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("Users data is empty", response.getBody().getMessage());
    }

    @Test
    public void testGetUserById_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<Response<User>> response = userService.getUserById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(user, response.getBody().getPayload());
        assertEquals("User found successfully.", response.getBody().getMessage());
    }

    @Test
    public void testGetUserById_NotFound() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Response<User>> response = userService.getUserById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("User not found with id 999", response.getBody().getMessage());
    }

    @Test
    public void testCreateUser_Success() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<Response<User>> response = userService.createUser(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(201, response.getBody().getStatusCode());
        assertEquals(user, response.getBody().getPayload());
        assertEquals("User created successfully.", response.getBody().getMessage());
    }

    @Test
    public void testCreateUser_InvalidEmail() {
        // Arrange
        user.setEmail(null); // Invalid email
        // Act
        ResponseEntity<Response<User>> response = userService.createUser(user);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().getStatusCode());
        assertEquals("Invalid email. Email must contain '@'.", response.getBody().getMessage());
    }

    @Test
    public void testUpdateUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setEmail("jane.doe@example.com");
        updatedUser.setPhone("0987654321");
        updatedUser.setUpdatedBy("Admin");

        // Act
        ResponseEntity<Response<User>> response = userService.updateUser(1L, updatedUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(updatedUser.getFirstName(), response.getBody().getPayload().getFirstName());
        assertEquals("User updated successfully.", response.getBody().getMessage());
    }

    @Test
    public void testUpdateUser_NotFound() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        User userDetails = new User(); // Dummy user details

        // Act
        ResponseEntity<Response<User>> response = userService.updateUser(999L, userDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("User not found with id 999", response.getBody().getMessage());
    }

    @Test
    public void testDeleteUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        // Act
        ResponseEntity<Response<Void>> response = userService.deleteUser(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals("User deleted successfully.", response.getBody().getMessage());
    }

    @Test
    public void testDeleteUser_NotFound() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Response<Void>> response = userService.deleteUser(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("User not found with id 999", response.getBody().getMessage());
    }
}
