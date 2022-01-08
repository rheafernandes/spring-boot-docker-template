package com.example.user.models;

import com.example.user.dto.PhoneNumberDto;
import com.example.user.dto.UserDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PhoneNumber(int id, String number) {
        this.id = id;
        this.number = number;
    }

    public PhoneNumberDto toDto() {
        return new PhoneNumberDto(this.id, this.number);
    }
}
