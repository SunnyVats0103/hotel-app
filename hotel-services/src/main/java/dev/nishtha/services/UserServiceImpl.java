package dev.nishtha.services;

import dev.nishtha.contracts.UserService;
import dev.nishtha.dtos.UserDTO;
import dev.nishtha.entities.Customer;
import dev.nishtha.entities.Owner;
import dev.nishtha.repositories.CustomerRepository;
import dev.nishtha.repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final CustomerRepository customerRepo;
    private final OwnerRepository ownerRepo;
    private final PasswordEncoder passEncoder;


    @Override
    public Customer registerCustomer(Customer customer) {
        customer.setPassword(passEncoder.encode(customer.getPassword()));
        return customerRepo.save(customer);
    }

    @Override
    public Owner registerOwner(Owner owner) {
        owner.setPassword(passEncoder.encode(owner.getPassword()));
        return ownerRepo.save(owner);
    }

    @Override
    public Customer loginCustomer(UserDTO userDTO) {
        Customer customer = customerRepo.findCustomerByName(userDTO.getUserName()).orElse(null);
        if (customer != null && passEncoder.matches(userDTO.getPassword(), customer.getPassword())) {
            return customer;
        }
        return null;
    }

    @Override
    public Owner loginOwner(UserDTO userDTO) {
        if (userDTO.getUserName() == null || userDTO.getPassword() == null) return null;

        Owner owner = ownerRepo.findOwnerByName(userDTO.getUserName()).orElse(null);
        if (owner != null && passEncoder.matches(userDTO.getPassword(), owner.getPassword())) {
            return owner;
        }
        return null;
    }
}
