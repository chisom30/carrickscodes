package com.ebanking.web;

import com.ebanking.config.JwtTokenUtil;
import com.ebanking.dto.JwtResponse;
import com.ebanking.dto.LoginRequest;
import com.ebanking.dto.UsersDto;
import com.ebanking.entities.Account;
import com.ebanking.entities.Role;
import com.ebanking.entities.Users;
import com.ebanking.entities.repositories.AccountRepository;
import com.ebanking.entities.repositories.RoleRepository;
import com.ebanking.entities.repositories.UsersRepository;
import com.ebanking.service.JwtUserDetailsService;
import com.ebanking.util.ResourceException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
//
//@RequestMapping("/api")
@Controller
@CrossOrigin
@AllArgsConstructor
public class UsersResource {
    private final UsersRepository usersRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        Users user = this.usersRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new ResourceException("User Name does not Exist"));
        return ResponseEntity.ok(new JwtResponse(token, user.getUsername(), user.getId()));
    }

    @SneakyThrows
    @PostMapping("/save-users")
    public ResponseEntity saveUsers(@Valid @RequestBody UsersDto usersDto) {
        Users users = new Users();
        Account account = this.accountRepository.findById(usersDto.getAccountId()).orElseThrow(() -> {
            return new ResourceException("Account Id not found");
        });
        users.setAccount(account);
        Role role = this.roleRepository.findById(usersDto.getRoleId()).orElseThrow(() -> {
            return new ResourceException("Role Id not found");
        });
        users.setRole(role);
        users.setUsername(usersDto.getUsername());
        users.setPassword(bcryptEncoder.encode(usersDto.getPassword()));
        this.usersRepository.save(users);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-users")
    public ResponseEntity updateUsers(@Valid @RequestBody UsersDto usersDto) {
        Users users = new Users();
        Account account = (Account) this.accountRepository.findById(usersDto.getAccountId()).get();
        users.setAccount(account);
        Role role = (Role) this.roleRepository.findById(usersDto.getRoleId()).get();
        users.setRole(role);
        users.setUsername(usersDto.getUsername());
        users.setPassword(usersDto.getPassword());
        this.usersRepository.save(users);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/view-users")
    public ResponseEntity<List<UsersDto>> getUsers() {
        List<Users> usersList = this.usersRepository.findAll();
        List<UsersDto> usersDtos = new ArrayList<>();
        usersList.forEach(users -> {
            UsersDto usersDto = new UsersDto();
            usersDto.setUsername(users.getPassword());
            usersDto.setPassword(users.getPassword());
            usersDto.setAccountId(users.getAccount().getId());
            usersDto.setRoleId(users.getRole().getId());
            usersDtos.add(usersDto);
        });
        return ResponseEntity.ok(usersDtos);
    }
    @GetMapping({"/get-users-by-id{id}"})
    public ResponseEntity<Users> getUsersId(@PathVariable Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User ID not found"));
        return ResponseEntity.ok(users);
    }
    @DeleteMapping({"/delete-users/{id}"})
    public void delete(@PathVariable Long id) {
        this.usersRepository.deleteById(id);
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
