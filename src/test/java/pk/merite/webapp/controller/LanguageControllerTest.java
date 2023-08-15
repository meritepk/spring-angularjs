package pk.merite.webapp.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import pk.merite.webapp.WebApp;

@SpringBootTest(classes = WebApp.class)
@AutoConfigureMockMvc
public class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/languages")
            .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123")))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(WebAppControllerTest.JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(2)));
    }
}
