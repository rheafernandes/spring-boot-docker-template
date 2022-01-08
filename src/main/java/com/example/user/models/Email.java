package com.example.user.models;

import com.example.user.dto.EmailDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Email(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public EmailDto toDto() {
        return new EmailDto(this.id, this.mail);
    }
}
