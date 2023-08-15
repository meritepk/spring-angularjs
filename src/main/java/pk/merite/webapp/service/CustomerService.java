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
        CustomerInfo existing = repository.findById(customer.getId()).orElse(null);
        if (existing != null) {
            repository.save(customer);
            return true;
        }
        return false;
    }

    public boolean delete(String id) {
        CustomerInfo existing = repository.findById(id).orElse(null);
        if (existing != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CustomerInfo> retrieveAll() {
        return repository.findAll();
    }

    public CustomerInfo retrieveById(String id) {
        return repository.findById(id).orElse(null);
    }

    public CustomerInfo retrieveByEmail(String email) {
        return repository.findByEmail(email);
    }
}
