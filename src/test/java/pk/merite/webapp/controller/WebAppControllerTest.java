package pk.merite.webapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import pk.merite.webapp.WebApp;

@SpringBootTest(classes = WebApp.class)
@AutoConfigureMockMvc
public class WebAppControllerTest {

    public static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRedirectToMainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/web/index.html"));

    }

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login").param("username", "test").param("password", "test123")
            .with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testLoginFails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    public void testAuthenticationEnabled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/success")
            .with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testCsrfEnabled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/success")
            .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123")))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testAuthenticationSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/success")
            .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123"))
            .with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
