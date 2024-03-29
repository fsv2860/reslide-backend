package com.mygroup.backendReslide.repository;

import com.mygroup.backendReslide.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByUsernameIgnoreCaseAndEnabled(String username, boolean enabled);
    List<User> findByUsernameContainingIgnoreCaseAndEnabled(String username, boolean enabled);
}
