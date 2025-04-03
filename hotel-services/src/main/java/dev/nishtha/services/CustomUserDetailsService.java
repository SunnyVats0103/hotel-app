package dev.nishtha.services;

import dev.nishtha.entities.Customer;
import dev.nishtha.entities.Owner;
import dev.nishtha.repositories.CustomerRepository;
import dev.nishtha.repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findOwnerByName(username).orElse(null);
        if (owner != null) {
            return new User(owner.getName(), owner.getPassword(), getAuthorities("OWNER"));
        }

        Customer customer = customerRepository.findCustomerByName(username).orElse(null);
        if (customer != null) {
            return new User(customer.getName(), customer.getPassword(), getAuthorities("CUSTOMER"));
        }

        throw new UsernameNotFoundException("User not found");
    }

    private Set<GrantedAuthority> getAuthorities(String role) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}