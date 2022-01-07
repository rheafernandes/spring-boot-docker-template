package com.example.user.dto;

import com.example.user.models.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDto implements Serializable {
    private int id;

    private String number;

    public PhoneNumber toEntity() {
        return new PhoneNumber(this.id, this.number);
    }
}
