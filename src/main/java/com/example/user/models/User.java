package com.example.user.models;

import com.example.user.dto.EmailDto;
import com.example.user.dto.PhoneNumberDto;
import com.example.user.dto.UserDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Email> emails;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PhoneNumber> phoneNumbers;

    private int deleted;

    public UserDto toDto() {
        Set<EmailDto> emails = this.emails.stream().map(Email::toDto).collect(Collectors.toSet());
        Set<PhoneNumberDto> phoneNumbers = this.phoneNumbers.stream().map(PhoneNumber::toDto).collect(Collectors.toSet());
        return new UserDto(this.id, this.firstName, this.lastName, emails, phoneNumbers);
    }
}
