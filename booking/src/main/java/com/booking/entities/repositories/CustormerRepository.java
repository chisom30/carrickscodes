package com.booking.entities.repositories;

import com.booking.entities.Custormer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustormerRepository extends JpaRepository<Custormer, Long> {
}
