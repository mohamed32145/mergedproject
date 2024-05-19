package com.tsofnsalesforce.LoginandRegistration;

import com.tsofnsalesforce.LoginandRegistration.Repository.AccountRepository;
import com.tsofnsalesforce.LoginandRegistration.Repository.RoleRepository;
import com.tsofnsalesforce.LoginandRegistration.Repository.UserRepository;
import com.tsofnsalesforce.LoginandRegistration.model.Account;
import com.tsofnsalesforce.LoginandRegistration.model.AppUser;
import com.tsofnsalesforce.LoginandRegistration.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class LoginAndRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginAndRegistrationApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository, AccountRepository accountRepository){

		return args -> {
			Role adminrole=Role.builder().createdDate(LocalDateTime.now()).name("ADMIN").build();
			if(roleRepository.findAll().isEmpty()) {
				roleRepository.save(
						Role.builder().createdDate(LocalDateTime.now()).name("READ").build()
				);
				Account account = new Account();
				account.setName("custom Account 1");
				account.setId(2103);

				// Save the Account
				account = accountRepository.save(account);

				Account account2 = new Account();
				account2.setName("custom Account 2");
				account2.setId(1001);

				// Save the Account
				account2 = accountRepository.save(account2);


				// Create an AppUser
				AppUser appUser = new AppUser();
				appUser.setFirstName("admin");
				appUser.setLastName("admin");
				appUser.setEmail("admin@gmail.com");
				appUser.setPassword("1234");
				appUser.setAccountLocked(false);
				appUser.setEnabled(true);

				// Set the Account for the AppUser
				appUser.setAccount(account);
				roleRepository.save(adminrole

				);

				// Add roles to the AppUser
				appUser.getRoles().add(adminrole);


				// Save the AppUser
				userRepository.save(appUser);

				roleRepository.save(
						Role.builder().createdDate(LocalDateTime.now()).name("CREATE_ACTION").build()
				);
				roleRepository.save(
						Role.builder().createdDate(LocalDateTime.now()).name("DELETE_ACTION").build()
				);
				roleRepository.save(
						Role.builder().createdDate(LocalDateTime.now()).name("UPDATE_ACTION").build()
				);
				roleRepository.save(
						Role.builder().createdDate(LocalDateTime.now()).name("TRIGGER_MANUAL_SCAN").build()
				);
			}

		};
	}
}


//			if(roleRepository.findByName("USER").isEmpty()){
//				roleRepository.save(
//						Role.builder().createdDate(LocalDateTime.now()).name("USER").build()
//				);
//			}
