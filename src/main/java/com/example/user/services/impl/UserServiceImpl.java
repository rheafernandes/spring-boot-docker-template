package com.example.user.services.impl;

import com.example.user.dto.EmailDto;
import com.example.user.dto.PhoneNumberDto;
import com.example.user.dto.UserDto;
import com.example.user.exceptions.InvalidRequestException;
import com.example.user.exceptions.ResourceNotFoundException;
import com.example.user.models.Email;
import com.example.user.models.PhoneNumber;
import com.example.user.models.User;
import com.example.user.repository.EmailRepository;
import com.example.user.repository.PhoneNumberRepository;
import com.example.user.repository.UserRepository;
import com.example.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rhea
 */

@Service
public class UserServiceImpl implements UserService {
    //TODO: Add logs

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Override
    public UserDto createUser(UserDto userDetails) throws Exception {
        if (userDetails == null)
            throw new InvalidRequestException("Can't create empty user details");
        if (userDetails != null && !isValidRequest(userDetails.getEmails(), userDetails.getPhoneNumbers()))
            throw new InvalidRequestException("Phone numbers or Email ids already exist");
        User userToSave = userDetails.toEntity();
        userToSave.getEmails().forEach(email -> email.setUser(userToSave));
        userToSave.getPhoneNumbers().forEach(email -> email.setUser(userToSave));
        User createdUser = userRepository.save(userToSave);
        return createdUser.toDto();
    }

    @Override
    public UserDto readUserById(int userId) throws Exception {
        Optional<User> optionalUser = userRepository.findByIdAndDeleted(userId, 0);
        if (optionalUser == null)
            throw new ResourceNotFoundException("No user found with this id: " + userId);
        return optionalUser.orElseThrow(() -> {
            throw new ResourceNotFoundException("No user found with this id: " + userId);
        }).toDto();
    }

    @Override
    public List<UserDto> readUserByName(String name) throws Exception {
        List<String> fullName = convertName(name);
        List<User> users = userRepository.findAllByFirstNameAndLastNameAndDeleted(fullName.get(0), fullName.get(1), 0);
        if (users == null)
            throw new ResourceNotFoundException("No users found with the name: " + name);
        return users.stream().map(User::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUserDetails(int userId, Set<EmailDto> emails, Set<PhoneNumberDto> phoneNumbers) throws Exception {
        if (!isValidRequest(emails, phoneNumbers))
            throw new InvalidRequestException("Phone numbers or Email ids already exist");
        Optional<User> optionalUser = userRepository.findById(userId);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmails(emails);
        userDto.setPhoneNumbers(phoneNumbers);
        optionalUser.ifPresentOrElse(user -> {
            Set<Email> emailEntities = emails.stream().map(EmailDto::toEntity).collect(Collectors.toSet());
            Set<PhoneNumber> phoneNumberEntities = phoneNumbers.stream().map(PhoneNumberDto::toEntity).collect(Collectors.toSet());
            emailEntities.forEach(email -> email.setUser(user));
            phoneNumberEntities.forEach(number -> number.setUser(user));
            user.setEmails(emailEntities);
            user.setPhoneNumbers(phoneNumberEntities);
            userRepository.save(user);
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
        }, () -> {
            throw new ResourceNotFoundException("No Such User Found with id: " + userId);
        });
        return userDto;
    }

    @Override
    public void deleteUser(int userId) throws Exception {
        Optional<User> optionalUser = userRepository.findByIdAndDeleted(userId, 0);
        optionalUser.ifPresentOrElse(user -> {
            List<Integer> emailIds = user.getEmails().stream().map(Email::getId).collect(Collectors.toList());
            emailRepository.deleteAllById(emailIds);
            List<Integer> phoneNumberIds = user.getPhoneNumbers().stream().map(PhoneNumber::getId).collect(Collectors.toList());
            phoneNumberRepository.deleteAllById(phoneNumberIds);
            user.setDeleted(1);
            userRepository.save(user);
        }, () -> {
            throw new ResourceNotFoundException("No Such User Found with id: " + userId);
        });
    }

    private Boolean isValidRequest(Set<EmailDto> emails, Set<PhoneNumberDto> phoneNumbers) {
        Set<Email> mails = emailRepository.findAllByMailIn(emails.stream().map(EmailDto::getMail).collect(Collectors.toSet()));
        Set<PhoneNumber> numbers = phoneNumberRepository.findAllByNumberIn(phoneNumbers.stream().map(PhoneNumberDto::getNumber).collect(Collectors.toSet()));
        return (numbers == null && mails == null) || (numbers.isEmpty() && mails.isEmpty());
    }

    private List<String> convertName(String name) throws Exception {
        if (name == null || name.isEmpty())
            throw new InvalidRequestException("Can't have empty name");
        String[] names = name.split("\s");
        if (names.length == 0 || names.length > 2)
            throw new InvalidRequestException("Can't have name with 0 or more than 2 words");
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : "";
        return List.of(firstName, lastName);
    }
}
