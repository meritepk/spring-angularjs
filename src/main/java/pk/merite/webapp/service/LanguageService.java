package pk.merite.webapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pk.merite.webapp.data.LanguageRepository;
import pk.merite.webapp.info.LanguageInfo;

@Service
public class LanguageService {

    private LanguageRepository repository;

    public LanguageService(LanguageRepository repository) {
        this.repository = repository;
    }

    public List<LanguageInfo> retrieveAll() {
        return repository.findAll();
    }
}
