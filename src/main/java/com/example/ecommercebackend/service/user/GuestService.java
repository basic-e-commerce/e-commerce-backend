package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.dto.user.guest.GuestCreateDto;
import com.example.ecommercebackend.entity.user.Guest;
import com.example.ecommercebackend.entity.user.Role;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.user.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GuestService {
    private final GuestRepository guestRepository;
    private final RoleService roleService;

    public GuestService(GuestRepository guestRepository, RoleService roleService) {
        this.guestRepository = guestRepository;
        this.roleService = roleService;
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

    public Guest findByUsernameOrNull(String username) {
        return guestRepository.findByUsername(username).orElse(null);

    }

    public Guest save(String firstName, String lastName, String phoneNo, String username, boolean accountNonLocked, boolean enabled) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName("guest"));
        Guest newGuest = new Guest(
                firstName,
                lastName,
                phoneNo,
                username,
                "pass",
                roles,
                false,
                false
        );
        return guestRepository.save(newGuest);
    }

}
