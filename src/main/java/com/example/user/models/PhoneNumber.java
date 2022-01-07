package com.example.user.models;

import com.example.user.dto.PhoneNumberDto;
import com.example.user.dto.UserDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="phone_number")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PhoneNumber {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String number;

    public PhoneNumberDto toDto() {
        return new PhoneNumberDto(this.id, this.number);
    }
}
