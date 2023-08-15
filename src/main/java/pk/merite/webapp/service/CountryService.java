package pk.merite.webapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pk.merite.webapp.data.CountryRepository;
import pk.merite.webapp.info.CountryInfo;

@Service
public class CountryService {

    private CountryRepository repository;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public List<CountryInfo> retrieveAll() {
        return repository.findAll();
    }

    public boolean create(CountryInfo country) {
        CountryInfo existing = repository.findByCode(country.getCode());
        if (existing == null) {
            repository.save(country);
            return true;
        }
        return false;
    }
}
