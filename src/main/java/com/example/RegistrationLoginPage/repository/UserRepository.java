package com.example.RegistrationLoginPage.repository;

import com.example.RegistrationLoginPage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.devices WHERE u.email = :email")
    Optional<User> findByEmailWithDevices(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.devices WHERE u.id = :id")
    Optional<User> findByIdWithDevices(@Param("id") Long id);
}