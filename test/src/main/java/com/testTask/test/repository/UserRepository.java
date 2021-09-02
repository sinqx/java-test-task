package com.testTask.test.repository;

import com.testTask.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);
    Optional<User> findByEmail(String username);
    Optional<User> findByPhoneNumber(Long phoneNumber);
}
