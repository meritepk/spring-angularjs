package pk.merite.webapp;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApp {

    static {
        init();
    }

    private static void init() {
        // java -Duser.timezone=UTC -Duser.country=US -Duser.language=en ...
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
