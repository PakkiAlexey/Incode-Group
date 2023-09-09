package com.example.testtask.demo.persistence;

import com.example.testtask.demo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'age' : { $gt: ?0 } }")
    List<User> findUsersByAgeGreaterThan(int age);
}
