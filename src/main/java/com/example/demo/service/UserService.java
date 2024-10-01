package com.example.demo.service;

import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$"); // for validating phone numbers

    // Get all users
    public ResponseEntity<Response<List<User>>> getAllUsers() {
        Response<List<User>> response = new Response<>();
        List<User> usersResponse = userRepository.findAll();

        if (usersResponse.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(usersResponse);
            response.setMessage("Users data is empty");
        } else {
            response.setStatusCode(200);
            response.setPayload(usersResponse);
            response.setMessage("Users retrieved successfully.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Get user by ID
    public ResponseEntity<Response<User>> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        Response<User> response = new Response<>();

        if (user.isPresent()) {
            response.setStatusCode(200);
            response.setPayload(user.get());
            response.setMessage("User found successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("User not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Create a new user with validation
    public ResponseEntity<Response<User>> createUser(User user) {
        Response<User> response = new Response<>();

        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            response.setStatusCode(400);
            response.setPayload(user);
            response.setMessage("Invalid email. Email must contain '@'.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (user.getPhone() == null || !PHONE_PATTERN.matcher(user.getPhone()).matches()) {
            response.setStatusCode(400);
            response.setPayload(user);
            response.setMessage("Invalid phone number. It must be exactly 10 digits.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Set createdDatetime only during creation
        user.setCreatedDatetime(LocalDateTime.now());
        user.setCreatedBy(user.getCreatedBy());
        user.setUpdatedDatetime(null);

        User savedUser = userRepository.save(user);

        response.setStatusCode(201);
        response.setPayload(savedUser);
        response.setMessage("User created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update user by ID with validation
    public ResponseEntity<Response<User>> updateUser(Long id, User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        Response<User> response = new Response<>();

        if (existingUser.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(userDetails);
            response.setMessage("User not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (userDetails.getEmail() == null || !userDetails.getEmail().contains("@")) {
            response.setStatusCode(400);
            response.setPayload(userDetails);
            response.setMessage("Invalid email. Email must contain '@'.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (userDetails.getPhone() == null || !PHONE_PATTERN.matcher(userDetails.getPhone()).matches()) {
            response.setStatusCode(400);
            response.setPayload(userDetails);
            response.setMessage("Invalid phone number. It must be exactly 10 digits.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Update existing user
        User user = existingUser.get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setUpdatedBy(userDetails.getUpdatedBy());
        user.setUpdatedDatetime(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        response.setStatusCode(200);
        response.setPayload(updatedUser);
        response.setMessage("User updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Delete user by ID
    public ResponseEntity<Response<Void>> deleteUser(Long id) {
        Response<Void> response = new Response<>();

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            response.setStatusCode(404);
            response.setPayload(null);
            response.setMessage("User not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        userRepository.deleteById(id);
        response.setStatusCode(200);
        response.setPayload(null);
        response.setMessage("User deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
