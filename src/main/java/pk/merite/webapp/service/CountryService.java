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

    public List<CountryInfo> read() {
        return repository.findAll();
    }
}
