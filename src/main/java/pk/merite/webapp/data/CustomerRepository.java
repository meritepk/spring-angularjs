package pk.merite.webapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import pk.merite.webapp.info.CustomerInfo;

public interface CustomerRepository extends JpaRepository<CustomerInfo, String> {

    CustomerInfo findByEmail(String email);
}
