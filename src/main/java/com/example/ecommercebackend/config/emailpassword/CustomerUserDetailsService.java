package com.example.ecommercebackend.config.emailpassword;

import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.service.user.CustomerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final CustomerService customerService;

    public CustomerUserDetailsService( CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadby usernameee");
        Customer byUsername = customerService.findByUsername(username);
        System.out.println("loadby usernameee 22");
        return byUsername;
    }

}
