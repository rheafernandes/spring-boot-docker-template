package com.example.user.repository;


import com.example.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByIdAndDeleted(Integer integer, Integer deleted);

    List<User> findAllByFirstNameAndLastNameAndDeleted(String firstName, String lastName, Integer deleted);

}
