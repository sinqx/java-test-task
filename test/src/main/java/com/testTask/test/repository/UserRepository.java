package com.testTask.test.repository;

import com.testTask.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);
    Optional<User> findByEmail(String username);
    Optional<User> findByPhoneNumber(Long phoneNumber);
}
