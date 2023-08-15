package pk.merite.webapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pk.merite.webapp.info.ApiResponseInfo;
import pk.merite.webapp.info.CountryInfo;
import pk.merite.webapp.service.CountryService;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponseInfo<List<CountryInfo>>> get() {
        return ResponseEntity.ok(new ApiResponseInfo<List<CountryInfo>>(service.retrieveAll()));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> create(@RequestBody CountryInfo country) {
        if (service.create(country)) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(country.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }
}
