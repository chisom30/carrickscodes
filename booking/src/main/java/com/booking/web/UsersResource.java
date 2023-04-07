package com.booking.web;

import com.booking.dto.UsersDto;
import com.booking.entities.Users;
import com.booking.entities.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
@Controller
public class UsersResource {
    private final UsersRepository usersRepository;

    @PostMapping("/save-users")
    public ResponseEntity<Users> saveUsers(@Valid @RequestBody Users usersDto) {
        return ResponseEntity.ok(this.usersRepository.save(usersDto));

    }

    @PutMapping("/update-users")
    public ResponseEntity<Users> updateUsers(@Valid @RequestBody Users usersDto) {
        return ResponseEntity.ok(this.usersRepository.save(usersDto));

    }

    @GetMapping("/get-users")
    public ResponseEntity<List<Users>> getUsers() {
        return ResponseEntity.ok(this.usersRepository.findAll());

    }

    @GetMapping("/get-users-by-id{id}")
    public ResponseEntity<Users> getUsersId(@PathVariable Long id) {
        return ResponseEntity.ok(this.usersRepository.findById(id).get());

    }

    @DeleteMapping("/delete-user/{id}")
    public void delete(@PathVariable Long id) {
        this.usersRepository.deleteById(id);
    }
}