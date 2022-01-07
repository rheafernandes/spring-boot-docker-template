package com.example.user.controllers;

import com.example.user.dto.ResponseDto;
import com.example.user.dto.UserDto;
import com.example.user.exceptions.InvalidRequestException;
import com.example.user.exceptions.ResourceNotFoundException;
import com.example.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/create")
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto response = userService.createUser(userDto);
            ResponseDto responseDto = new ResponseDto(HttpStatus.CREATED, null, List.of(response));
            return new ResponseEntity<ResponseDto>(responseDto, null, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleExceptions(e);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto> fetchUserById(@PathVariable int userId) {
        try {
            UserDto response = userService.readUserById(userId);
            ResponseDto responseDto = new ResponseDto(HttpStatus.OK, null, List.of(response));
            return new ResponseEntity<ResponseDto>(responseDto, null, HttpStatus.OK);
        } catch (Exception e) {
            return handleExceptions(e);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseDto> fetchUserByName(@RequestParam String name) {
        try {
            List<UserDto> response = userService.readUserByName(name);
            ResponseDto responseDto = new ResponseDto(HttpStatus.OK, null, response);
            return new ResponseEntity<ResponseDto>(responseDto, null, HttpStatus.OK);
        } catch (Exception e) {
            return handleExceptions(e);
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody UserDto userDto, @PathVariable int userId) {
        try {
            UserDto response = userService.updateUserDetails(userId, userDto.getEmails(), userDto.getPhoneNumbers());
            ResponseDto responseDto = new ResponseDto(HttpStatus.OK, null, List.of(response));
            return new ResponseEntity<ResponseDto>(responseDto, null, HttpStatus.OK);
        } catch (Exception e) {
            return handleExceptions(e);
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable int userId) {
        try {
            userService.deleteUser(userId);
            ResponseDto responseDto = new ResponseDto(HttpStatus.OK, null, List.of(new UserDto()));
            return new ResponseEntity<ResponseDto>(responseDto, null, HttpStatus.OK);
        } catch (Exception e) {
            return handleExceptions(e);
        }
    }

    private static ResponseEntity<ResponseDto> handleExceptions(Exception e) {
        ResponseDto responseDto = new ResponseDto();
        if (e instanceof ResourceNotFoundException) {
            responseDto.setErrorMessage(e.getMessage());
            responseDto.setStatusCode(HttpStatus.NOT_FOUND);
        } else if (e instanceof InvalidRequestException) {
            responseDto.setErrorMessage(e.getMessage());
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST);
        } else {
            responseDto.setErrorMessage("There was an error while processing your request.");
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ResponseDto>(responseDto, null, responseDto.getStatusCode());
    }
}
