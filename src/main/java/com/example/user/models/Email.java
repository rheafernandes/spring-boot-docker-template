package com.example.user.models;

import com.example.user.dto.EmailDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Email {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String mail;

    public EmailDto toDto() {
        return new EmailDto(this.id, this.mail);
    }
}
