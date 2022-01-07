package com.example.user.repository;


import com.example.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);

}
