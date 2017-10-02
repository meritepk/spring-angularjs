package pk.merite.webapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pk.merite.webapp.info.ApiResponseInfo;
import pk.merite.webapp.info.CustomerInfo;
import pk.merite.webapp.service.CustomerService;

@RestController
@RequestMapping("/webservices/customers")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponseInfo<List<CustomerInfo>>> get() {
        return ResponseEntity.ok(new ApiResponseInfo<List<CustomerInfo>>(null, service.read()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerInfo> get(@PathVariable("id") String id) {
        CustomerInfo customer = service.read(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CustomerInfo customer) {
        if (service.create(customer)) {
            CustomerInfo created = service.readByEmail(customer.getEmail());
            URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody CustomerInfo customer) {
        customer.setId(id);
        if (service.update(customer)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
