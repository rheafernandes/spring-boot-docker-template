package com.example.user.controllers;

import com.example.user.dto.UserDto;
import com.example.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    private UserDto userDto = null;

    @BeforeEach
    public void before() {
        userDto = new UserDto();
        userDto.setEmails(new ArrayList<>());
        userDto.setPhoneNumbers(new ArrayList<>());
    }

    @Test
    public void testCreateUser() throws Exception {
        controller.createUser(userDto);
        verify(userService).createUser(userDto);
    }

    @Test
    public void testGetUserById() throws Exception {
        controller.fetchUserById(2);
        verify(userService).readUserById(2);
    }

    @Test
    public void testGetUserByName() throws Exception {
        controller.fetchUserByName("rhea");
        verify(userService).readUserByName("rhea");
    }

    @Test
    public void testUpdateUserDetails() throws Exception {
        controller.updateUser(userDto, 2);
        verify(userService).updateUserDetails(2, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testDeleteUser() throws Exception {
        controller.deleteUser(2);
        verify(userService).deleteUser(2);
    }
}
