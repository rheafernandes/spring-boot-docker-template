package com.example.user.services;


import com.example.user.dto.EmailDto;
import com.example.user.dto.PhoneNumberDto;
import com.example.user.dto.UserDto;
import com.example.user.exceptions.InvalidRequestException;
import com.example.user.exceptions.ResourceNotFoundException;
import com.example.user.models.User;
import com.example.user.repository.EmailRepository;
import com.example.user.repository.PhoneNumberRepository;
import com.example.user.repository.UserRepository;
import com.example.user.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    EmailRepository emailRepository;

    @Mock
    PhoneNumberRepository phoneNumberRepository;


    @Test
    public void testCreateUser_Success() throws Exception {
        Mockito.when(emailRepository.findAllByMailIn(Mockito.anySet()))
                .thenReturn(new HashSet<>());
        Mockito.when(phoneNumberRepository.findAllByNumberIn(Mockito.anySet()))
                .thenReturn(new HashSet<>());
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(getSuccessUserDto().toEntity());
        UserDto savedUser = userServiceImpl.createUser(getSuccessUserDto());
        assertNotNull(savedUser != null);
        assertEquals("Rhea", savedUser.getFirstName());
        assertEquals(2, savedUser.getEmails().size());
    }

    @Test
    public void testCreateUser_InvalidRequest() throws Exception {
        Mockito.when(emailRepository.findAllByMailIn(Mockito.anySet()))
                .thenReturn(new HashSet<>());
        Mockito.when(phoneNumberRepository.findAllByNumberIn(Mockito.anySet()))
                .thenReturn(Set.of(gePhoneNumbersDto(1, "12345").toEntity()));
        assertThrows(InvalidRequestException.class, () -> {
            userServiceImpl.createUser(getSuccessUserDto());
        });
    }

    @Test
    public void testReadUserWithId_Success() throws Exception {
        Mockito.when(userRepository.findByIdAndDeleted(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.of(getSuccessUserDto().toEntity()));
        UserDto savedUser = userServiceImpl.readUserById(1);
        assertNotNull(savedUser != null);
        assertEquals("Rhea", savedUser.getFirstName());
        assertEquals(2, savedUser.getEmails().size());
    }

    @Test
    public void testReadUserWithId_NoUser() throws Exception {
        Mockito.when(userRepository.findByIdAndDeleted(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            userServiceImpl.readUserById(5);
        });
    }

    @Test
    public void testReadUserWithName_Success() throws Exception {
        Mockito.when(userRepository.findAllByFirstNameAndLastNameAndDeleted(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(List.of(getSuccessUserDto().toEntity()));
        List<UserDto> savedUsers = userServiceImpl.readUserByName("Rhea Fernandes");
        assertNotNull(savedUsers != null);
        assertEquals("Rhea", savedUsers.get(0).getFirstName());
        assertEquals(2, savedUsers.get(0).getEmails().size());
    }

    @Test
    public void testReadUserWithName_NoUser() throws Exception {
        Mockito.when(userRepository.findAllByFirstNameAndLastNameAndDeleted(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            userServiceImpl.readUserByName("Hema");
        });
    }

    @Test
    public void testReadUserWithName_InvalidRequest() throws Exception {
        assertThrows(InvalidRequestException.class, () -> {
            userServiceImpl.readUserByName(null);
        });
    }

    public UserDto getSuccessUserDto() {
        Set<EmailDto> emails = Set.of(
                getEmailDto(1, "abc@gmail.com"),
                getEmailDto(2, "xyz@gmail.com")
        );
        Set<PhoneNumberDto> numbers = Set.of(
                gePhoneNumbersDto(1, "12345"),
                gePhoneNumbersDto(2, "23456")
        );

        UserDto userDto = new UserDto(1, "Rhea", "Fernandes", emails, numbers);
        return userDto;
    }


    public EmailDto getEmailDto(int id, String email) {
        return new EmailDto(id, email);

    }

    public PhoneNumberDto gePhoneNumbersDto(int id, String number) {
        return new PhoneNumberDto(id, number);
    }

}
