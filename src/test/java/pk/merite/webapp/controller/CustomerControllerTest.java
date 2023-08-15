package pk.merite.webapp.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(WebAppControllerTest.JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(Matchers.greaterThan(1))));
    }

    @Test
    public void testGetOne() throws Exception {
        get("01");
    }

    @Test
    public void testGetNotFound() throws Exception {
        String id = "07";
        notFound(id);
    }

    public void notFound(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + id)
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    public void get(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + id)
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(WebAppControllerTest.JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("First" + id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Last" + id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("first" + id + "@last" + id)));
    }

    public void create(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(WebAppControllerTest.JSON)
                .content("{\"id\":\"" + id + "\",\"email\":\"first" + id + "@last" + id + "\",\"firstName\":\"First"
                        + id
                        + "\",\"lastName\":\"Last" + id
                        + "\",\"dateOfBirth\":\"2017-01-01\",\"countryId\":\"pk\",\"languageId\":\"urd\",\"userId\":\"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        get(id);
    }

    @Test
    public void testCreate() throws Exception {
        create("03");
    }

    @Test
    public void testCreateWithIdGeneratedOnServer() throws Exception {
        String id = "06";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(WebAppControllerTest.JSON)
                .content("{\"email\":\"first" + id + "@last" + id + "\",\"firstName\":\"First" + id
                        + "\",\"lastName\":\"Last" + id
                        + "\",\"dateOfBirth\":\"2017-01-01\",\"countryId\":\"pk\",\"languageId\":\"urd\",\"userId\":\"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateExistingShouldFail() throws Exception {
        String id = "02";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(WebAppControllerTest.JSON)
                .content("{\"id\":\"" + id + "\",\"email\":\"first" + id + "@last" + id + "\",\"firstName\":\"First"
                        + id
                        + "\",\"lastName\":\"Last" + id
                        + "\",\"dateOfBirth\":1483228800000,\"countryId\":\"pk\",\"languageId\":\"urd\",\"userId\":\"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDelete() throws Exception {
        String id = "05";
        create(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/" + id)
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        notFound(id);
    }

    @Test
    public void testDeleteNonExistingShouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/10")
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdate() throws Exception {
        String id = "04";
        create(id);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/" + id)
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(WebAppControllerTest.JSON)
                .content("{\"id\":\"04\",\"email\":\"first44@last44\",\"firstName\":\"First44\",\"lastName\":\"Last44\""
                        + ",\"dateOfBirth\":\"2017-01-01\",\"countryId\":\"pk\",\"languageId\":\"urd\",\"userId\":\"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        try {
            get(id);
            Assertions.fail("testUpdate() failed");
        } catch (AssertionError e) {
        }
    }

    @Test
    public void testUpdateNonExistingShouldFail() throws Exception {
        String id = "10";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/" + id)
                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test123"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(WebAppControllerTest.JSON)
                .content("{\"id\":\"04\",\"email\":\"first44@last44\",\"firstName\":\"First44\",\"lastName\":\"Last44\""
                        + ",\"dateOfBirth\":\"2017-01-01\",\"countryId\":\"pk\",\"languageId\":\"urd\",\"userId\":\"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
