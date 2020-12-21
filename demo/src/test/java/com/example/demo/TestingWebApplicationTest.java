package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String STUDENT_PATH = "/student";

    @Test
    public void shouldReturnStatusNotFoundOnGetStudentAPIWhenIDDoesNotExist() throws Exception {
        this.mockMvc.perform(get(STUDENT_PATH + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnStatusCreateOnPostStudent() throws Exception {
        this.mockMvc.perform(post(STUDENT_PATH).contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\n" +
                        "    \"name\" : \"nir\",\n" +
                        "    \"email\" : \"nir@blabla.com\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldFindStudentByNameAfterPostStudent() throws Exception {
        this.mockMvc.perform(post(STUDENT_PATH).contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\n" +
                        "    \"name\" : \"elhai\",\n" +
                        "    \"email\" : \"elhai@blabla.com\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get(STUDENT_PATH).queryParam("name", "elhai"))
                .andDo(print())
                .andExpect(jsonPath("$.[0].name").value("elhai"))
                .andExpect(jsonPath("$.[0].email").value("elhai@blabla.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindDeletedStudent() throws Exception {
        this.mockMvc.perform(post(STUDENT_PATH).contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\n" +
                        "    \"name\" : \"elhai\",\n" +
                        "    \"email\" : \"elhai@blabla.com\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get(STUDENT_PATH).queryParam("name", "elhai"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("elhai"))
                .andExpect(jsonPath("$.[0].email").value("elhai@blabla.com"))
                .andReturn();
    }
}