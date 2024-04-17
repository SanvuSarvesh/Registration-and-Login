package com.example.authenticationservice.repositories;

import com.example.authenticationservice.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByEmailId(String emailId);

}
