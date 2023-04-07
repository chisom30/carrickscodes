package com.chisom.redditApplication.service;

import com.chisom.redditApplication.dto.LoginRequest;
import com.chisom.redditApplication.dto.RegisterRequest;
import com.chisom.redditApplication.model.NotificationEmail;
import com.chisom.redditApplication.model.Users;
import com.chisom.redditApplication.model.VerificationToken;
import com.chisom.redditApplication.repository.UsersRepository;
import com.chisom.redditApplication.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        Users user = new Users();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(), "Thank you for signing up to Reddit Application, " +
                "Please click on the Url below to activate your account: " +
                "http://localhost:8081/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(Users user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUsers(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    //    public void login(LoginRequest loginRequest) {
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
//                loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        String token = jwtProvider.generateToken(authenticate);
//    }
    public Users save(LoginRequest user) {
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(newUser);
    }
}
