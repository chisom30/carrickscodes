package com.booking.web;

import com.booking.dto.BookingDto;
import com.booking.entities.Booking;
import com.booking.entities.Custormer;
import com.booking.entities.repositories.BookingRepository;
import com.booking.entities.repositories.CustormerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
@Controller
public class BookingResource {
    private final BookingRepository bookingRepository;
    private final CustormerRepository custormerRepository;

    //this method is to save Booking
    @PostMapping("/save-booking")
    public ResponseEntity saveBooking(@Valid @RequestBody BookingDto bookingDto) {
        Booking booking = new Booking();
        Custormer custormer = custormerRepository.
                findById(bookingDto.getCustormerId()).orElseThrow(() -> new RuntimeException("Customer Id not found"));
        booking.setCustormer(custormer);
        booking.setEndDate(LocalDate.parse(bookingDto.getEndDate()));
        booking.setStartDate(LocalDate.parse(bookingDto.getStartDate()));
        this.bookingRepository.save(booking);
        return ResponseEntity.ok().build();
    }

    //this method is to update Booking
    @PutMapping("/update-booking")
    public ResponseEntity updateBooking(@Valid @RequestBody BookingDto bookingDto) {
        Booking booking = bookingRepository.
                findById(bookingDto.getId()).orElseThrow(() -> new RuntimeException("Booking Id not found"));
        Custormer custormer = custormerRepository.
                findById(bookingDto.getCustormerId()).orElseThrow(() -> new RuntimeException("Customer Id not found"));
        booking.setCustormer(custormer);
        booking.setEndDate(LocalDate.parse(bookingDto.getEndDate()));
        booking.setStartDate(LocalDate.parse(bookingDto.getStartDate()));
        this.bookingRepository.save(booking);
        return ResponseEntity.ok().build();
    }

    //this method is to view booking
    @GetMapping("/get-booking")
    public ResponseEntity<List<BookingDto>> getBooking() {
        List<Booking> bookingList = this.bookingRepository.findAll();
        List<BookingDto> bookingDtos = new ArrayList<>();
        bookingList.stream().map(booking -> {
            BookingDto bookingDto = new BookingDto();
            bookingDto.setCustormerId(booking.getCustormer().getId());
            bookingDto.setEndDate(String.valueOf(booking.getEndDate()));
            bookingDto.setStartDate(String.valueOf(booking.getStartDate()));
            bookingDto.setId(booking.getId());
            bookingDtos.add(bookingDto);
            return bookingDtos;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(bookingDtos);
    }

    //this method deletes booking
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        this.bookingRepository.deleteById(id);
    }
}

