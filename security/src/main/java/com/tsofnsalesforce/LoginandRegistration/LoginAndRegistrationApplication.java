package com.tsofnsalesforce.LoginandRegistration;

import com.tsofnsalesforce.LoginandRegistration.Repository.RoleRepository;
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
	public CommandLineRunner runner(RoleRepository roleRepository){

		return args -> {
			if(roleRepository.findAll().isEmpty()) {
				roleRepository.save(
						Role.builder().createdDate(LocalDateTime.now()).name("READ").build()
				);
				roleRepository.save(
						Role.builder().createdDate(LocalDateTime.now()).name("ADMIN").build()
				);
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
