package ru.job4j.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class AuthApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void whenGetFindAllPersonThenReturnOkAndList() throws Exception {
        Person test = Person.of("login", "password");
        List<Person> expectList = Arrays.asList(test);
        repository.save(test);
        Mockito.when(repository.findAll()).thenReturn(expectList);
        this.mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectList)));
    }

    @Test
    public void whenGetFindByIdPersonThenReturnOkAndPerson() throws Exception {
        Person test = Person.of("login", "password");
        repository.save(test);
        Mockito.when(repository.findById(test.getId())).thenReturn(Optional.of(test));
        mockMvc.perform(
                get("/person/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("login"))
                .andExpect(jsonPath("$.password").value("password"));

    }

    @Test
    public void whenPostCreatePersonThenReturnCreated() throws Exception {
        Person test = Person.of("login", "password");
        mockMvc.perform(
                post("/person/")
                .content(mapper.writeValueAsString(test))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenUpdatePersonThenReturnOkAndPerson() throws Exception {
        Person test = Person.of("login", "password");
        repository.save(test);
        Mockito.when(repository.findById(0)).thenReturn(Optional.of(test));
        test.setPassword("edit password");
        mockMvc.perform(
                put("/person/")
                        .content(mapper.writeValueAsString(test))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(
                get("/person/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("login"))
                .andExpect(jsonPath("$.password").value("edit password"));
    }

    @Test
    public void whenDeletePersonThenOk() throws Exception {
        Person test = Person.of("login", "password");
        repository.save(test);
        mockMvc.perform(
                delete("/person/0"))
                .andExpect(status().isOk());
        mockMvc.perform(
                get("/person/0"))
                .andExpect(status().isNotFound());
    }
}
