package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dto.CreateUserRequest;
import cat.itacademy.s04.t01.userapi.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Neymar PSG", "neymar@football.es");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Neymar PSG"))
                .andExpect(jsonPath("$.email").value("neymar@football.es"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getUserById_returnsCorrectUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Ronaldinho Blaugrana", "ronaldinho@football.es");

        MvcResult result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);


        mockMvc.perform(get("/users/{id}", createdUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ronaldinho Blaugrana"))
                .andExpect(jsonPath("$.email").value("ronaldinho@football.es"))
                .andExpect(jsonPath("$.id").value(createdUser.id().toString()));
    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {
        String randomUUID = UUID.randomUUID().toString();

        mockMvc.perform(get("/users/{id}", randomUUID))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Ronaldinho Blaugrana", "ronaldinho@football.es");
        CreateUserRequest request2 = new CreateUserRequest("Neymar PSG", "neymar@football.es");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)));

        mockMvc.perform(get("/users").param("name", "ney"))
                .andExpect(jsonPath("$[0].name").value("Neymar PSG"))
                .andExpect(status().isOk());
    }
}
