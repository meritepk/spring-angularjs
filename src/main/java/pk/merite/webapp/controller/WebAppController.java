package pk.merite.webapp.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebAppController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping({ "/", "/login" })
    public String index() {
        return "redirect:/web/index.html";
    }

    @PostMapping("/api/v1/login/success")
    @ResponseBody
    public void loginSuccess(Principal user) {
        logger.info("User {} logged in successfully", user.getName().replaceAll("\r\n", ""));
    }
}
