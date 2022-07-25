package retailer.rewards.repository;

import org.springframework.data.repository.CrudRepository;

import retailer.rewards.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    public Customer findByCustomerId(Long customerId);
}
