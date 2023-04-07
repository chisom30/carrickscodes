package com.booking.web;

import com.booking.dto.CustormerDto;
import com.booking.entities.Custormer;
import com.booking.entities.Users;
import com.booking.entities.repositories.CustormerRepository;
import com.booking.entities.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
@Controller
public class CustormerResource {
    private final CustormerRepository custormerRepository;
    private final UsersRepository usersRepository;

    //this method is to save custormer
    @PostMapping("/save-custormer")
    public ResponseEntity saveCustormer(@Valid @RequestBody CustormerDto custormerDto) {
        Custormer custormer = new Custormer();
        Users users = usersRepository.
                findById(custormerDto.getUserId()).orElseThrow(() -> new RuntimeException("User id not found"));
        custormer.setName(custormerDto.getName());
        custormer.setEmail(custormerDto.getEmail());
        custormer.setAddress(custormerDto.getAddress());
        custormer.setUsers(users);
        this.custormerRepository.save(custormer);
        return ResponseEntity.ok().build();
    }

    //this method update custormer
    @PutMapping("/update-custormer")
    public ResponseEntity updateCustormer(@Valid @RequestBody CustormerDto custormerDto) {
        Custormer custormer = custormerRepository.
                findById(custormerDto.getId()).orElseThrow(() -> new RuntimeException("Custormer id not found"));
        Users users = usersRepository.
                findById(custormerDto.getUserId()).orElseThrow(() -> new RuntimeException("User id not found"));
        custormer.setUsers(users);
        custormer.setName(custormerDto.getName());
        custormer.setEmail(custormerDto.getEmail());
        custormer.setAddress(custormerDto.getAddress());
        this.custormerRepository.save(custormer);
        return ResponseEntity.ok().build();
    }

    //this method view custormer
    @GetMapping("/get-custormer")
    public ResponseEntity<List<CustormerDto>> getCustormer() {
        List<Custormer> custormerList = this.custormerRepository.findAll();
        List<CustormerDto> custormerDtos = new ArrayList<>();
        custormerList.forEach(custormer -> {
            CustormerDto custormerDto = new CustormerDto();
            custormerDto.setName(custormer.getName());
            custormerDto.setEmail(custormer.getEmail());
            custormerDto.setAddress(custormer.getAddress());
            custormerDto.setId(custormer.getId());
            custormerDtos.add(custormerDto);

        });
        return ResponseEntity.ok(custormerDtos);
    }

    //this method deletes custormer
    @DeleteMapping("/delete-customer/{id}")
    public void delete(@PathVariable Long id) {
        this.custormerRepository.deleteById(id);
    }
}
