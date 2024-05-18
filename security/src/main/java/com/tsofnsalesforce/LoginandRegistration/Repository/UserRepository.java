package com.tsofnsalesforce.LoginandRegistration.Repository;

import com.tsofnsalesforce.LoginandRegistration.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findById(int id);
    Boolean existsByEmail(String email);

}
