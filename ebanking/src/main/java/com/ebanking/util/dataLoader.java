package com.ebanking.util;

import com.ebanking.entities.Users;
import com.ebanking.entities.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@AllArgsConstructor
public class dataLoader {
    private final UsersRepository userRepository;

    public void save() {
        Optional<Users> user = this.userRepository.findByUsername("admin");
        if (!user.isPresent()) {
            Users user1 = new Users();
            user1.setUsername("admin");
            user1.setPassword("$2a$10$cMFcK/LrKHuBnuGiKH.FwO1KD3kiDzmaxObbW4QRtSrzon0s/QkSa");
            this.userRepository.save(user1);
        }

    }


}
