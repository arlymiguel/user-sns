package com.qcaldwell.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends CommonIntegrationTest{

    @Test
    void saveUser_test() throws Exception{
        MvcResult documentStackingResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildUserRequest()))
                .andExpect(status().is(200))
                .andReturn();
    } //http://localhost:9922/api/v1/user

    @Test
    void getUser_Test() throws Exception{
        MvcResult documentStackingResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/user/1"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void putUser_Test() throws Exception{
        MvcResult documentStackingResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildUserRequest()))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void deleteUser_Test() throws Exception{
        MvcResult documentStackingResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/user/1"))
                .andExpect(status().is(200))
                .andReturn();
    }

    private String buildUserRequest()throws Exception {
        return readFileAsString("classpath:userRequest.json");
    }
    public static String readFileAsString(String fileName) throws Exception {
        File file = ResourceUtils.getFile(fileName);
        String jsonResource = new String(Files.readAllBytes(file.toPath()));
        return jsonResource.replaceAll("\\r", "");
    }
}
