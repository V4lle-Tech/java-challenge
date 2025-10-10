package com.example.demo.service;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        testUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        testUser = new User();
        testUser.setId(testUserId);
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");
    }

    @Test
    @DisplayName("Should find all users")
    void shouldFindAllUsers() {
        // Given
        User secondUser = new User();
        secondUser.setId(UUID.fromString("456e7890-e89b-12d3-a456-426614174001"));
        secondUser.setName("Jane Doe");
        secondUser.setEmail("jane@example.com");
        List<User> users = Arrays.asList(testUser, secondUser);
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = userService.findAll();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).contains(testUser);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find user by id")
    void shouldFindUserById() {
        // Given
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        // When
        Optional<User> result = userService.findById(testUserId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testUser);
        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUser() {
        // Given
        var request = new com.example.demo.dto.CreateUserRequest(testUser.getName(), testUser.getEmail());
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.create(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(testUser.getEmail());
        verify(userRepository, times(1)).existsByEmail(testUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when email exists")
    void shouldThrowExceptionWhenEmailExists() {
        // Given
        var request = new com.example.demo.dto.CreateUserRequest(testUser.getName(), testUser.getEmail());
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(true);

        // When/Then
        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("email");

        verify(userRepository, times(1)).existsByEmail(testUser.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUser() {
        // Given
        var request = new com.example.demo.dto.UpdateUserRequest(testUserId, "John Updated",
                "john.updated@example.com");

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.update(testUserId, request);

        // Then
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).findById(testUserId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existing user")
    void shouldThrowExceptionWhenUpdatingNonExistingUser() {
        // Given
        var request = new com.example.demo.dto.UpdateUserRequest(testUserId, "Updated", "updated@example.com");
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> userService.update(nonExistingId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, times(1)).findById(nonExistingId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUser() {
        // Given
        when(userRepository.existsById(testUserId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(testUserId);

        // When
        userService.delete(testUserId);

        // Then
        verify(userRepository, times(1)).existsById(testUserId);
        verify(userRepository, times(1)).deleteById(testUserId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing user")
    void shouldThrowExceptionWhenDeletingNonExistingUser() {
        // Given
        UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
        when(userRepository.existsById(nonExistingId)).thenReturn(false);

        // When/Then
        assertThatThrownBy(() -> userService.delete(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, times(1)).existsById(nonExistingId);
        verify(userRepository, never()).deleteById(any(UUID.class));
    }
}
