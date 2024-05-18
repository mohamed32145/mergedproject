package com.tsofnsalesforce.LoginandRegistration.Repository;

import com.tsofnsalesforce.LoginandRegistration.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {

    @Query(value = """
      select t from tokens t inner join appUser u\s
      on t.appUser.id = u.id\s
      where u.id = :userId and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer userId);

    
    Optional<Token> findByToken(String token);
}
