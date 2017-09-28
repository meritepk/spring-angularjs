package pk.merite.webapp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import pk.merite.webapp.data.CustomerRepository;
import pk.merite.webapp.info.CustomerInfo;

@Service
public class CustomerService {

    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public boolean create(CustomerInfo customer) {
        CustomerInfo existing = repository.findByEmail(customer.getEmail());
        if (existing == null) {
            if (customer.getId() == null) {
                customer.setId(UUID.randomUUID().toString());
            }
            repository.save(customer);
            return true;
        }
        return false;
    }

    public boolean update(CustomerInfo customer) {
        CustomerInfo existing = repository.findOne(customer.getId());
        if (existing != null) {
            repository.save(customer);
            return true;
        }
        return false;
    }

    public boolean delete(String id) {
        CustomerInfo existing = repository.findOne(id);
        if (existing != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    public List<CustomerInfo> read() {
        return repository.findAll();
    }

    public CustomerInfo read(String id) {
        return repository.findOne(id);
    }

    public CustomerInfo readByEmail(String email) {
        return repository.findByEmail(email);
    }
}
