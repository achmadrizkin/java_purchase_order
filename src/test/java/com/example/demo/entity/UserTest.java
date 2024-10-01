package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void testUserEntitySuccess() {
        // Arrange
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        // Act
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setCreatedBy("admin");
        user.setUpdatedBy("admin");
        user.setCreatedDatetime(now);
        user.setUpdatedDatetime(now);

        // Assert
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPhone()).isEqualTo("1234567890");
        assertThat(user.getCreatedBy()).isEqualTo("admin");
        assertThat(user.getUpdatedBy()).isEqualTo("admin");
        assertThat(user.getCreatedDatetime()).isEqualTo(now);
        assertThat(user.getUpdatedDatetime()).isEqualTo(now);
    }

    @Test
    public void testUserEntityFailed() {
        // Arrange
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        // Act
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setCreatedBy("admin");
        user.setUpdatedBy("admin");
        user.setCreatedDatetime(now);
        user.setUpdatedDatetime(now);

        // Assert
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isNotEqualTo("John22");
        assertThat(user.getLastName()).isNotEqualTo("Doess");
        assertThat(user.getEmail()).isNotEqualTo("john.adoe@example.com");
        assertThat(user.getPhone()).isNotEqualTo("12345X7890");
        assertThat(user.getCreatedBy()).isNotEqualTo("Xdmin");
        assertThat(user.getUpdatedBy()).isNotEqualTo("Xdmin");
        assertThat(user.getCreatedDatetime()).isEqualTo(now);
        assertThat(user.getUpdatedDatetime()).isEqualTo(now);
    }

    @Test
    public void testUserSettersAndGettersSuccess() {
        // Arrange
        User user = new User();

        // Act
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Smith");
        user.setEmail("alice.smith@example.com");
        user.setPhone("0987654321");
        user.setCreatedBy("user");
        user.setUpdatedBy("user");
        user.setCreatedDatetime(LocalDateTime.now());
        user.setUpdatedDatetime(LocalDateTime.now());

        // Assert
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("Alice");
        assertThat(user.getLastName()).isEqualTo("Smith");
        assertThat(user.getEmail()).isEqualTo("alice.smith@example.com");
        assertThat(user.getPhone()).isEqualTo("0987654321");
        assertThat(user.getCreatedBy()).isEqualTo("user");
        assertThat(user.getUpdatedBy()).isEqualTo("user");
    }

    @Test
    public void testUserSettersAndGettersFail() {
        // Arrange
        User user = new User();

        // Act
        user.setId(1L);
        user.setFirstName("Alice");
        user.setLastName("Smith");
        user.setEmail("alice.smith@example.com");
        user.setPhone("0987654321");
        user.setCreatedBy("user");
        user.setUpdatedBy("user");
        user.setCreatedDatetime(LocalDateTime.now());
        user.setUpdatedDatetime(LocalDateTime.now());

        // Assert
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isNotEqualTo("AlXice");
        assertThat(user.getLastName()).isNotEqualTo("SmiXth");
        assertThat(user.getEmail()).isNotEqualTo("alice.Xsmith@example.com");
        assertThat(user.getPhone()).isNotEqualTo("098765X321");
        assertThat(user.getCreatedBy()).isNotEqualTo("useXr");
        assertThat(user.getUpdatedBy()).isNotEqualTo("usXr");
    }
}
