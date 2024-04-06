package com.thiagomdo.ba.challenge.msfeedback.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msfeedback.resources.FeedBackResource;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedBackNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msfeedback.common.FeedBackConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void getAllFeedBack_With_ListEmptyInDB_ThrowsEmptyListException_Status200() throws Exception{
        when(feedBackService.getAll()).thenThrow(EmptyListException.class);

        mockMvc.perform(get("/feedbacks"))
        .andExpect(status().isOk());
    }

    @Test
    void getAllFeedBack_With_IdValid_ReturnsFeedBackDTO_Status200() throws Exception{
        when(feedBackService.getById(FEED_BACK01.getId())).thenReturn(FEED_BACK01_DTO);

        mockMvc.perform(get("/feedbacks/" + FEED_BACK01.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(FEED_BACK01_DTO)));
    }

    @Test
    void getAllFeedBack_With_InvalidIdValid_ThrowsFeedBackNotFoundException_Status400() throws Exception{
        when(feedBackService.getById("Id_Invalid")).thenThrow(FeedBackNotFoundException.class);

        mockMvc.perform(get("/feedbacks/Id_Invalid"))
            .andExpect(status().isNotFound());
    }

}
