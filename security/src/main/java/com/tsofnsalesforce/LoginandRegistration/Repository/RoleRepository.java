package com.tsofnsalesforce.LoginandRegistration.Repository;

import com.tsofnsalesforce.LoginandRegistration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(String roleName);
}
