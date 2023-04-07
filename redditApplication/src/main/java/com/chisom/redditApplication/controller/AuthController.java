package com.chisom.redditApplication.controller;

import com.chisom.redditApplication.dto.AuthenticationResponse;
import com.chisom.redditApplication.dto.LoginRequest;
import com.chisom.redditApplication.dto.RegisterRequest;
import com.chisom.redditApplication.exceptions.SpringRedditException;
import com.chisom.redditApplication.model.Users;
import com.chisom.redditApplication.repository.UsersRepository;
import com.chisom.redditApplication.security.JwtProvider;
import com.chisom.redditApplication.service.AuthService;
import com.chisom.redditApplication.service.UserDetailServiceImple;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final UserDetailServiceImple userDetailServiceImple;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailServiceImple
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtProvider.generateToken(userDetails);
        Users user = this.usersRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new SpringRedditException("User Name does not Exist"));
        return ResponseEntity.ok(new AuthenticationResponse(token, user.getUsername(), user.getUserId()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
