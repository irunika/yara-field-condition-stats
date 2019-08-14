package org.irunika.yara.challenge.controller;

import org.irunika.yara.challenge.model.FieldStatsSummary;
import org.irunika.yara.challenge.service.FieldConditionStatsService;
import org.irunika.yara.challenge.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FieldConditionStatsController.class)
public class FieldConditionStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FieldConditionStatsService fieldConditionStatsService;

    @Test
    public void getFieldStatistics_generateSummary_respondWithExpectedValue() throws Exception {
        Mockito.when(fieldConditionStatsService.generateFieldStatsSummary(ArgumentMatchers.anyInt()))
                .thenReturn(new FieldStatsSummary(0.2, 0.3, 0.1));

        mockMvc.perform(get(Constants.URI_FIELD_STATISTICS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{'vegetation':{'min':0.2, 'max':0.3, 'avg':0.1}}"));
    }

    @Test
    public void postFieldConditions_withValidDate_CreateEntriesSuccessfully() throws Exception {
        mockMvc.perform(post(Constants.URI_FIELD_CONDITIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"vegetation\": 0.5, \"occurrenceAt\": \"2019-04-23T08:50Z\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void postFieldConditions_withInvalidDate_respondWithBadRequest() throws Exception {
        mockMvc.perform(post(Constants.URI_FIELD_CONDITIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"vegetation\": 0.5, \"occurrenceAt\": \"2019-04-23\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid date 2019-04-23. Expecting ISO_8601 date time format for field 'occurrenceAt'"));
    }

    @Test
    public void postFieldConditions_withInvalidVegetation_respondWithBadRequest() throws Exception {
        mockMvc.perform(post(Constants.URI_FIELD_CONDITIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"vegetation\": \"invalid\", \"occurrenceAt\": \"2019-04-23\"}"))
                .andExpect(status().isBadRequest());
    }

}
