package com.tsofnsalesforce.LoginandRegistration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsofnsalesforce.LoginandRegistration.Repository.AccountRepository;
import com.tsofnsalesforce.LoginandRegistration.Repository.RoleRepository;
import com.tsofnsalesforce.LoginandRegistration.Repository.TokenRepository;
import com.tsofnsalesforce.LoginandRegistration.Repository.UserRepository;
import com.tsofnsalesforce.LoginandRegistration.Response.AddPermissionResponse;
import com.tsofnsalesforce.LoginandRegistration.Response.AuthenticationResponse;
import com.tsofnsalesforce.LoginandRegistration.Response.DeletePermissionResponse;
import com.tsofnsalesforce.LoginandRegistration.model.*;
import com.tsofnsalesforce.LoginandRegistration.request.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

import static com.tsofnsalesforce.LoginandRegistration.enums.EmailTemplate.ACTIVATE_ACCOUNT;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final AccountRepository accountRepository;
    private static int id_counter=1;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


    public AuthenticationResponse register(RegisterRequest request) throws MessagingException {

        Account account = accountRepository.findAccountByName(request.getAccountName()).orElseThrow(() -> new IllegalArgumentException("account not present"));
        if(request.getAccountName().isEmpty())
            throw new MessagingException("Please specify the account name");

        var userRole = roleRepository.findByName("READ")
                // TODO - make the exception more
                .orElseThrow(()-> new IllegalArgumentException("ROLE USER IS NOT FOUND!"));

        var user = AppUser.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .account(account)
                .build();
        var savedUser = userRepository.save(user);
        sendValidationEmail(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String ,Object>();
        var user = ((AppUser)auth.getPrincipal());
        claims.put("fullName",user.getFirstName() + user.getLastName());
        var jwtToken = jwtService.generateToken(claims,user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
//                .token(jwtToken)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void addAccount(AddAccountRequest request) {
        if (id_counter==1001||id_counter==2103)
            id_counter++;
        var account = Account.builder()
                .name(request.getAccountName())
                .id(id_counter)
                .build();
        id_counter++;
        accountRepository.save(account);
    }
//    @Transactional
    public void activateAccount(String token) throws MessagingException {
        var userToken = tokenRepository.findByToken(token)
                //TODO need to check more then one condition
                .orElseThrow(()-> new RuntimeException("invalid token"));
        if(LocalDateTime.now().isAfter(userToken.getExpiresAt())) {
            sendValidationEmail(userToken.getAppUser());
            throw new RuntimeException("Activation token has ben expired");
        }
        var user = userToken.getAppUser();
        user.setEnabled(true);
        userRepository.save(user);
        userToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(userToken);
    }
    //-------------------------------------------------------//
    public AddPermissionResponse AddPermission(AddPermissionRequest request) throws MessagingException {


        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new IllegalArgumentException("User Doesn't exists!!"));
        Set<Role>  addedRoles = new HashSet<>();
        var userRoles = user.getRoles();
        for(int i=0;i<request.getRoles().size();i++){
            var role = roleRepository.findByName(request.getRoles().get(i).getName()).orElseThrow(()-> new IllegalArgumentException("THE ROLE DOESN'T EXISTS"));
                addedRoles.add(role);
        }
        userRoles.addAll(addedRoles);
        user.setRoles(userRoles);
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return AddPermissionResponse.builder()
                .token(jwtToken)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public DeletePermissionResponse deletePermission(DeletePermissionRequest request) throws MessagingException {

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        System.out.println("user Roles before delete: " + user.getAuthorities());
        List<Role>  deleteRoles = new ArrayList<>();
        var userRoles = user.getRoles();
        System.out.println("user roles"+ userRoles);
        for(int i=0;i<request.getRoles().size();i++){
            var role = roleRepository.findByName(request.getRoles().get(i).getName()).orElseThrow(()-> new IllegalArgumentException("THE ROLE DOESN'T EXISTS"));
            deleteRoles.add(role);
        }
        deleteRoles.forEach(userRoles::remove);
        user.setRoles(userRoles);
        System.out.println("user Roles after delete: " + user.getAuthorities());
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return DeletePermissionResponse.builder()
                .token(jwtToken)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    //---------------------------------------------------------------------------------//

    private void sendValidationEmail(AppUser user) throws MessagingException {

        var newToken = generateAndSaveActivationToken(user);
        emailService.emailSender(
                user.getEmail(),
                user.getFirstName() + user.getLastName(),
                ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(AppUser user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .cratedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .appUser(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int len) {
        String  characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i=0;i<len;i++){
            int randIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randIndex));
        }
        return codeBuilder.toString();
    }

    private void saveUserToken(AppUser user, String jwtToken) {
        var token = Token.builder()
                .appUser(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(AppUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public boolean validateToken(String token) {

        if (token == null ||!token.startsWith("Bearer ")) {
            return false;
        }
        final String userEmail;
        var extractedToken = token.substring(7);
        userEmail = jwtService.extractUsername(extractedToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            return jwtService.isTokenValid(extractedToken, user);
        }
        return false;
   }

    public List<String> getUserRoles(String token) {
        List<String> roles = new ArrayList<>();
        if (token == null ||!token.startsWith("Bearer ")) {
            return Collections.emptyList();
        }
        if(validateToken(token)){
            final String userEmail;
            var extractedToken = token.substring(7);
            userEmail = jwtService.extractUsername(extractedToken);
            if (userEmail != null) {
                var user = this.userRepository.findByEmail(userEmail)
                        .orElseThrow();
                List<String> rolesnames=new ArrayList<>();

                for (Role role:user.getRoles())
                    rolesnames.add(role.getName());

                roles.addAll(rolesnames);
                System.out.println("the roles are "+roles);

//                for(int i=0;i<user.getRoles().size();i++){
//                    roles.add();
//                }
                return roles;
            }
        }
        return Collections.emptyList();
    }
}
