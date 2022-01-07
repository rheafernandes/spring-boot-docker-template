package com.example.user.repository;

import com.example.user.dto.EmailDto;
import com.example.user.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface EmailRepository extends JpaRepository<Email, Integer> {
    List<Email> findAllByMailIn(List<String> mails);
}
