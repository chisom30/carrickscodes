package com.ebanking.web;

import com.ebanking.entities.Role;
import com.ebanking.entities.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@CrossOrigin
@AllArgsConstructor
@Controller
public class RoleResource {
    private final RoleRepository roleRepository;

    @PostMapping({"/save-role"})
    public ResponseEntity<Role> saveUsers(@Valid @RequestBody Role roleDto, @RequestHeader("Authorization") String Authorization) {
        return ResponseEntity.ok(this.roleRepository.save(roleDto));
    }

    @PutMapping({"/update-role"})
    public ResponseEntity<Role> saveRole(@Valid @RequestBody Role roleDto, @RequestHeader("Authorization") String Authorization) {
        return ResponseEntity.ok(this.roleRepository.save(roleDto));
    }

    @GetMapping({"/get-role"})
    public ResponseEntity<List<Role>> getUsers(@RequestHeader("Authorization") String Authorization) {
        return ResponseEntity.ok(this.roleRepository.findAll());
    }

    @GetMapping({"/get-role-by-id{id}"})
    public ResponseEntity<Role> getUsersId(@PathVariable Long id, @RequestHeader("Authorization") String Authorization) {
        return ResponseEntity.ok(this.roleRepository.findById(id).get());
    }

    @DeleteMapping({"/delete-role/{id}"})
    public void delete(@PathVariable Long id, @RequestHeader("Authorization") String Authorization) {
        this.roleRepository.deleteById(id);
    }
}
