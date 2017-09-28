package pk.merite.webapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pk.merite.webapp.info.ApiResponseInfo;
import pk.merite.webapp.info.LanguageInfo;
import pk.merite.webapp.service.LanguageService;

@RestController
@RequestMapping("/webservices/languages")
public class LanguageController {

    private LanguageService service;

    public LanguageController(LanguageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponseInfo<List<LanguageInfo>>> get() {
        return ResponseEntity.ok(new ApiResponseInfo<List<LanguageInfo>>(null, service.read()));
    }

}
