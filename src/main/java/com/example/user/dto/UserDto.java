package com.example.user.dto;

import com.example.user.models.Email;
import com.example.user.models.PhoneNumber;
import com.example.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    int id;

    String firstName;

    String lastName;

    List<EmailDto> emails;

    List<PhoneNumberDto> phoneNumbers;

    public User toEntity() {
        List<Email> emails = this.emails.stream().map(EmailDto::toEntity).collect(Collectors.toList());
        List<PhoneNumber> phoneNumbers = this.phoneNumbers.stream().map(PhoneNumberDto::toEntity).collect(Collectors.toList());
        return new User(this.id, this.firstName, this.lastName, emails, phoneNumbers, 0);
    }

}
