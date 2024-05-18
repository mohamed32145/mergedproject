package com.tsofnsalesforce.LoginandRegistration.Repository;

import com.tsofnsalesforce.LoginandRegistration.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository  extends JpaRepository<Account,Integer> {

    Optional<Account> findAccountByName(String name);

}
