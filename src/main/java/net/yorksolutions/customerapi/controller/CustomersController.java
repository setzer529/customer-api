package net.yorksolutions.customerapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.yorksolutions.customerapi.model.Customers;
import net.yorksolutions.customerapi.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/customers")
@RestController
public class CustomersController {
    @Autowired
    CustomersRepository customersRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/all")
    String getAllCustomers() throws JsonProcessingException {
        return objectMapper.writeValueAsString(customersRepository.findAll());
    }



    @GetMapping("/getByRowAmount")
    String getCustomersByRows(@RequestParam("rows") String rows) throws JsonProcessingException {
        List<Customers> customersList = (List<Customers>) customersRepository.findAll();
        customersList = customersList.stream().limit(Long.parseLong(rows)).collect(Collectors.toList());
        return objectMapper.writeValueAsString(customersList);
    }

    @DeleteMapping("/delete")
    String deleteCustomers(@RequestParam("id") Long id) {
        try {
            customersRepository.deleteById(id);
        } catch (EmptyResultDataAccessException | MethodArgumentTypeMismatchException e) {
            return e.getMessage();
        }
        return "deleted";
    }

    @PutMapping("/update{id}")
    String deleteCustomers(@RequestBody Customers customers, @PathVariable Long id) {
        try {
            customersRepository.findById(id).map(target -> {
                target.setFname(customers.getFname());
                target.setLname(customers.getLname());
                target.setCity(customers.getCity());
                target.setState(customers.getState());
                target.setStreet(customers.getStreet());
                target.setZip(customers.getZip());
                target.setEmail(customers.getEmail());
                target.setPhone(customers.getPhone());
                return customersRepository.save(target);
            });
        } catch (EmptyResultDataAccessException e) {
            return e.getMessage();
        }
        return "updated";
    }

    @PostMapping("/add")
    String postCustomers(@RequestBody String body) {
        try {
            Customers customers = objectMapper.readValue(body, Customers.class);
            customersRepository.save(customers);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return "post successful";
    }

}
