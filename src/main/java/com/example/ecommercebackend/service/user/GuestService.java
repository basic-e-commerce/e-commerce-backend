package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.dto.user.guest.GuestCreateDto;
import com.example.ecommercebackend.entity.user.Guest;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.user.GuestRepository;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest createGuest(GuestCreateDto guestCreateDto) {
        Guest guest = new Guest();
        return guestRepository.save(guest);
    }

    public Guest findGuestById(Integer id){
        return guestRepository.findById(id).orElseThrow();
    }
    public Guest findByUsername(String username){
        return guestRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Guest "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }
}
