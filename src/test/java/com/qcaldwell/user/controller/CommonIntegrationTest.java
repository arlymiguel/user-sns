package com.qcaldwell.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@Transactional
@ContextConfiguration(initializers = {CommonIntegrationTest.Initializer.class})
public class CommonIntegrationTest {
    @Autowired
    protected ObjectMapper objectMapper;
    //@Autowired
    //protected WireMockServer wireMockServer;
    @Autowired
    protected MockMvc mockMvc;
    @Container
    public static PostgreSQLContainer<DocumentPostgresContainer> postgresqlContainer = DocumentPostgresContainer.getInstance();
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            System.out.println("getJdbcUrl: " +postgresqlContainer.getJdbcUrl());
            System.out.println("getUsername: " + postgresqlContainer.getUsername());
            System.out.println("getPassword: "+ postgresqlContainer.getPassword());
            TestPropertyValues.of("spring.datasource.url=" + postgresqlContainer.getJdbcUrl(), "spring.datasource.username=" + postgresqlContainer.getUsername(), "spring.datasource.password=" + postgresqlContainer.getPassword()).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
