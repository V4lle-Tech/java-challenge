package com.example.demo.controller;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("UserController WebMvcTest Integration Tests")
class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private UserService userService;

        private User testUser;
        private List<User> userList;
        private UUID testUserId;
        private UUID user2Id;

        @BeforeEach
        void setUp() {
                testUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
                user2Id = UUID.fromString("456e7890-e89b-12d3-a456-426614174001");

                testUser = new User();
                testUser.setId(testUserId);
                testUser.setName("John Doe");
                testUser.setEmail("john@example.com");

                User user2 = new User();
                user2.setId(user2Id);
                user2.setName("Jane Doe");
                user2.setEmail("jane@example.com");

                userList = Arrays.asList(testUser, user2);
        }

        @Test
        @DisplayName("GET /api/users should return all users")
        void shouldGetAllUsers() throws Exception {
                // Given
                when(userService.findAll()).thenReturn(userList);

                // When/Then
                mockMvc.perform(get("/api/users")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].id", is(testUserId.toString())))
                                .andExpect(jsonPath("$[0].name", is("John Doe")))
                                .andExpect(jsonPath("$[0].email", is("john@example.com")))
                                .andExpect(jsonPath("$[1].id", is(user2Id.toString())))
                                .andExpect(jsonPath("$[1].name", is("Jane Doe")));

                verify(userService, times(1)).findAll();
        }

        @Test
        @DisplayName("GET /api/users/{id} should return user when found")
        void shouldGetUserById() throws Exception {
                // Given
                when(userService.findById(testUserId)).thenReturn(Optional.of(testUser));

                // When/Then
                mockMvc.perform(get("/api/users/{id}", testUserId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id", is(testUserId.toString())))
                                .andExpect(jsonPath("$.name", is("John Doe")))
                                .andExpect(jsonPath("$.email", is("john@example.com")));

                verify(userService, times(1)).findById(testUserId);
        }

        @Test
        @DisplayName("GET /api/users/{id} should return 404 when user not found")
        void shouldReturn404WhenUserNotFound() throws Exception {
                // Given
                UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
                when(userService.findById(nonExistingId)).thenReturn(Optional.empty());

                // When/Then
                mockMvc.perform(get("/api/users/{id}", nonExistingId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                verify(userService, times(1)).findById(nonExistingId);
        }

        @Test
        @DisplayName("POST /api/users should create user successfully")
        void shouldCreateUser() throws Exception {
                // Given
                var request = new com.example.demo.dto.CreateUserRequest("New User", "newuser@example.com");

                UUID savedUserId = UUID.fromString("789e0123-e89b-12d3-a456-426614174002");
                User savedUser = new User();
                savedUser.setId(savedUserId);
                savedUser.setName("New User");
                savedUser.setEmail("newuser@example.com");

                when(userService.create(any(com.example.demo.dto.CreateUserRequest.class))).thenReturn(savedUser);

                // When/Then
                mockMvc.perform(post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id", is(savedUserId.toString())))
                                .andExpect(jsonPath("$.name", is("New User")))
                                .andExpect(jsonPath("$.email", is("newuser@example.com")));

                verify(userService, times(1)).create(any(com.example.demo.dto.CreateUserRequest.class));
        }

        @Test
        @DisplayName("POST /api/users should return 400 for validation errors")
        void shouldReturn400ForValidationErrors() throws Exception {
                // Given
                var invalidRequest = new com.example.demo.dto.CreateUserRequest("A", "invalid-email"); // Invalid

                // When/Then
                mockMvc.perform(post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest)))
                                .andExpect(status().isBadRequest());

                verify(userService, never()).create(any(com.example.demo.dto.CreateUserRequest.class));
        }

        @Test
        @DisplayName("POST /api/users should return 409 when email already exists")
        void shouldReturn409WhenEmailExists() throws Exception {
                // Given
                var request = new com.example.demo.dto.CreateUserRequest("Duplicate User", "existing@example.com");

                when(userService.create(any(com.example.demo.dto.CreateUserRequest.class)))
                                .thenThrow(new DuplicateResourceException("User", "email", "existing@example.com"));

                // When/Then
                mockMvc.perform(post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isConflict())
                                .andExpect(jsonPath("$.status", is(409)))
                                .andExpect(jsonPath("$.message").exists());

                verify(userService, times(1)).create(any(com.example.demo.dto.CreateUserRequest.class));
        }

        @Test
        @DisplayName("PUT /api/users/{id} should update user successfully")
        void shouldUpdateUser() throws Exception {
                // Given
                var request = new com.example.demo.dto.UpdateUserRequest(testUserId, "Updated Name",
                                "updated@example.com");

                User returnedUser = new User();
                returnedUser.setId(testUserId);
                returnedUser.setName("Updated Name");
                returnedUser.setEmail("updated@example.com");

                when(userService.update(eq(testUserId), any(com.example.demo.dto.UpdateUserRequest.class)))
                                .thenReturn(returnedUser);

                // When/Then
                mockMvc.perform(put("/api/users/{id}", testUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id", is(testUserId.toString())))
                                .andExpect(jsonPath("$.name", is("Updated Name")))
                                .andExpect(jsonPath("$.email", is("updated@example.com")));

                verify(userService, times(1)).update(eq(testUserId), any(com.example.demo.dto.UpdateUserRequest.class));
        }

        @Test
        @DisplayName("PUT /api/users/{id} should return 404 when user not found")
        void shouldReturn404WhenUpdatingNonExistentUser() throws Exception {
                // Given
                var request = new com.example.demo.dto.UpdateUserRequest(testUserId, "Updated Name",
                                "updated@example.com");

                UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
                when(userService.update(eq(nonExistingId), any(com.example.demo.dto.UpdateUserRequest.class)))
                                .thenThrow(new ResourceNotFoundException("User", "id", nonExistingId));

                // When/Then
                mockMvc.perform(put("/api/users/{id}", nonExistingId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isNotFound());

                verify(userService, times(1)).update(eq(nonExistingId),
                                any(com.example.demo.dto.UpdateUserRequest.class));
        }

        @Test
        @DisplayName("DELETE /api/users/{id} should delete user successfully")
        void shouldDeleteUser() throws Exception {
                // Given
                doNothing().when(userService).delete(testUserId);

                // When/Then
                mockMvc.perform(delete("/api/users/{id}", testUserId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());

                verify(userService, times(1)).delete(testUserId);
        }

        @Test
        @DisplayName("DELETE /api/users/{id} should return 404 when user not found")
        void shouldReturn404WhenDeletingNonExistentUser() throws Exception {
                // Given
                UUID nonExistingId = UUID.fromString("999e4567-e89b-12d3-a456-426614174999");
                doThrow(new ResourceNotFoundException("User", "id", nonExistingId))
                                .when(userService).delete(nonExistingId);

                // When/Then
                mockMvc.perform(delete("/api/users/{id}", nonExistingId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());

                verify(userService, times(1)).delete(nonExistingId);
        }
}
