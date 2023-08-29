package com.hopsncode.challenge.notifications.catalog.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("Integration")
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMessageCategoryCatalogReturnsExpected() throws Exception {
        mockMvc.perform(get("/api/v1/catalog/message-category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(100001, 100002, 100003)))
                .andExpect(jsonPath("$[*].option", containsInAnyOrder("Sports", "Finance", "Movies")))
        ;
    }
}