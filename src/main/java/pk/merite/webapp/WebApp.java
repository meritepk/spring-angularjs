package pk.merite.webapp;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class WebApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        init();
        SpringApplication.run(WebApp.class, args);
    }

    @PostConstruct
    protected static void init() {
        // java -Duser.timezone=UTC -Duser.country=US -Duser.language=en ...
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Locale.setDefault(Locale.US);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebApp.class);
    }
}
