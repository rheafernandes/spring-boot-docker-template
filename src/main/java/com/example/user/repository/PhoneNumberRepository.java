package com.example.user.repository;

import com.example.user.dto.EmailDto;
import com.example.user.models.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
    List<PhoneNumber> findAllByNumberIn(List<String> numbers);

}
