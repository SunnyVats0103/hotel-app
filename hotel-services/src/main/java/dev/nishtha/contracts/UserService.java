package dev.nishtha.contracts;

import dev.nishtha.dtos.UserDTO;
import dev.nishtha.entities.Customer;
import dev.nishtha.entities.Owner;

public interface UserService {

    Customer registerCustomer(Customer customer);
    Owner registerOwner(Owner owner);
    Customer loginCustomer(UserDTO userDTO);
    Owner loginOwner(UserDTO userDTO);

}
