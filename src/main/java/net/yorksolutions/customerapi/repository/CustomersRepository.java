package net.yorksolutions.customerapi.repository;

import net.yorksolutions.customerapi.model.Customers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CustomersRepository extends CrudRepository<Customers, Long>{
}
