package com.example.user.services;

import com.example.user.dto.EmailDto;
import com.example.user.dto.PhoneNumberDto;
import com.example.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDetails) throws Exception;

    UserDto readUserById(int userId) throws Exception;

    List<UserDto> readUserByName(String name) throws Exception;

    UserDto updateUserDetails(int userId, List<EmailDto> emails, List<PhoneNumberDto> phoneNumbers) throws Exception;

    void deleteUser(int userId) throws Exception;

}
