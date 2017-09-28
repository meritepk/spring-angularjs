package pk.merite.webapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pk.merite.webapp.info.ApiResponseInfo;
import pk.merite.webapp.info.CountryInfo;
import pk.merite.webapp.service.CountryService;

@RestController
@RequestMapping("/webservices/countries")
public class CountryController {

    private CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponseInfo<List<CountryInfo>>> get() {
        return ResponseEntity.ok(new ApiResponseInfo<List<CountryInfo>>(null, service.read()));
    }

}
