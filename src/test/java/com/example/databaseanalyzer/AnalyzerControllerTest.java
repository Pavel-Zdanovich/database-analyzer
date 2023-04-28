package com.example.databaseanalyzer;

import com.example.databaseanalyzer.dto.Credentials;
import com.example.databaseanalyzer.model.Statistics;
import com.example.databaseanalyzer.service.Analyzer;
import com.example.databaseanalyzer.service.StatisticsService;
import com.example.databaseanalyzer.web.AnalyzerController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = {AnalyzerController.class})
public class AnalyzerControllerTest {

    @MockBean
    StatisticsService statisticsService;

    @MockBean
    Analyzer analyzer;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void analyze() throws Exception {
        Statistics statistics = new Statistics();

        Mockito.when(analyzer.analyze(Mockito.any())).thenReturn(statistics);

        Credentials credentials = new Credentials();
        credentials.setUrl("jdbc:mysql://localhost:3306");
        credentials.setUsername("username");
        credentials.setPassword("password");
        String json = objectMapper.writeValueAsString(credentials);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/analysis")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(json)
                ).andExpect(
                        MockMvcResultMatchers.status().isCreated()
                );
    }

}
