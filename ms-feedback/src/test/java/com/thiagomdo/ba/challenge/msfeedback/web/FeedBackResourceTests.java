package com.thiagomdo.ba.challenge.msfeedback.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msfeedback.resources.FeedBackResource;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.thiagomdo.ba.challenge.msfeedback.common.FeedBackConstants.FEED_BACK_DTO_LIST;
import static com.thiagomdo.ba.challenge.msfeedback.common.FeedBackConstants.FEED_BACK_LIST;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedBackResource.class)
public class FeedBackResourceTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FeedBackService feedBackService;


    @Test
    void getAllFeedBack_With_With_FeedBackInDB_ReturnsFeedBackDTOList_Status200() throws Exception {
        when(feedBackService.getAll()).thenReturn(FEED_BACK_DTO_LIST);

        mockMvc.perform(get("/feedbacks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id").value(FEED_BACK_LIST.get(0).getId()))
        .andExpect(jsonPath("$[1].id").value(FEED_BACK_LIST.get(1).getId()))
        .andExpect(jsonPath("$[2].id").value(FEED_BACK_LIST.get(2).getId()));
    }

    @Test
    void getAllFeedBack_With_ListEmptyInDB_ThrowsEmptyListException() throws Exception{
        when(feedBackService.getAll()).thenThrow(EmptyListException.class);

        mockMvc.perform(get("/feedbacks"))
        .andExpect(status().isOk());
    }

}
