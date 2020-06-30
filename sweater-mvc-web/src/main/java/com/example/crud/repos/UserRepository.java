package com.example.crud.repos;

import com.example.crud.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

    List<User> findAll();

    User findByEmail(String email);


}