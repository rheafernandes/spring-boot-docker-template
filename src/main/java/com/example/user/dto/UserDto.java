package com.example.user.dto;

import com.example.user.models.Email;
import com.example.user.models.PhoneNumber;
import com.example.user.models.User;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
    int id;

    String firstName;

    String lastName;

    Set<EmailDto> emails;

    Set<PhoneNumberDto> phoneNumbers;

    public User toEntity() {
        Set<Email> emails = this.emails.stream().map(EmailDto::toEntity).collect(Collectors.toSet());
        Set<PhoneNumber> phoneNumbers = this.phoneNumbers.stream().map(PhoneNumberDto::toEntity).collect(Collectors.toSet());
        return new User(this.id, this.firstName, this.lastName, emails, phoneNumbers, 0);
    }

}
