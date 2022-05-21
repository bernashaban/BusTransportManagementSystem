package project.service.impl;

import project.dao.impl.CustomerRepository;
import project.model.Customer;
import project.service.Service;

import java.util.Collection;

public class CustomerService implements Service<Customer> {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer add(Customer entity) {
        return customerRepository.create(entity);
    }

    @Override
    public Customer update(Customer entity) {
        return customerRepository.update(entity);
    }

    @Override
    public Customer delete(Integer id) {
        return customerRepository.deleteById(id);
    }
    public Customer findCustomerByUsername(String username){
        var customer = customerRepository.findByUsername(username);
        if (customer != null) {
            return customer;
        }
        return null;
    }
}
