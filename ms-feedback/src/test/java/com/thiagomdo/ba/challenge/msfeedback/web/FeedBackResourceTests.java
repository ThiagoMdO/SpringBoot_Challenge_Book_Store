package com.thiagomdo.ba.challenge.msfeedback.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedBackRequest;
import com.thiagomdo.ba.challenge.msfeedback.resources.FeedBackResource;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedBackNotFoundException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.NotPossibleToCommentOrderException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msfeedback.common.FeedBackConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void getFeedBackById_With_IdValid_ReturnsFeedBackDTO_Status200() throws Exception{
        when(feedBackService.getById(FEED_BACK01.getId())).thenReturn(FEED_BACK01_DTO);

        mockMvc.perform(get("/feedbacks/" + FEED_BACK01.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(FEED_BACK01_DTO)));
    }

    @Test
    void getFeedBackById_With_InvalidIdValid_ThrowsFeedBackNotFoundException_Status400() throws Exception{
        when(feedBackService.getById("Id_Invalid")).thenThrow(FeedBackNotFoundException.class);

        mockMvc.perform(get("/feedbacks/Id_Invalid"))
            .andExpect(status().isNotFound());
    }

    @Test
    void createFeedBack_With_ValidData_ReturnsFeedBackDTO_Status201() throws Exception{
        when(feedBackService.create(FEED_BACK_REQUEST01)).thenReturn(FEED_BACK_CREATED_IN_DB_DTO);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEED_BACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }

    @Test
    void createFeedBack_With_OrderIdInvalid_ThrowsOrderNotFoundException_Status404() throws Exception{
        when(feedBackService.create(FEED_BACK_REQUEST0_OrderIdInvalid)).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEED_BACK_REQUEST0_OrderIdInvalid))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void createFeedBack_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException() throws Exception{
        when(feedBackService.create(FEED_BACK_REQUEST02_ORDER_CANCELED)).thenThrow(NotPossibleToCommentOrderException.class);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEED_BACK_REQUEST02_ORDER_CANCELED))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateFeedBack_With_ValidData_ReturnsFeedBackDTO_Status200() throws Exception{
        when(feedBackService.update(FEED_BACK_UPDATED_IN_DB_DTO.getId(), FEED_BACK_REQUEST01)).thenReturn(FEED_BACK_UPDATED_IN_DB_DTO);

        mockMvc.perform(put("/feedbacks/" + FEED_BACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEED_BACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    void updateFeedBack_With_InvalidIdValid_ThrowsFeedBackNotFoundException_Status400() throws Exception{
        when(feedBackService.update("Id_Invalid", FEED_BACK_REQUEST01)).thenThrow(FeedBackNotFoundException.class);

        mockMvc.perform(put("/feedbacks/Id_Invalid")
        .content(objectMapper.writeValueAsString(FEED_BACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void updateFeedBack_With_OrderIdInvalid_ThrowsOrderNotFoundException_Status404() throws Exception{
        when(feedBackService.update(FEED_BACK_UPDATED_IN_DB_DTO.getId(), FEED_BACK_REQUEST0_OrderIdInvalid)).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(put("/feedbacks/" + FEED_BACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEED_BACK_REQUEST0_OrderIdInvalid))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void updateFeedBack_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException() throws Exception{
        when(feedBackService.update(FEED_BACK_UPDATED_IN_DB_DTO.getId(), FEED_BACK_REQUEST02_ORDER_CANCELED)).thenThrow(NotPossibleToCommentOrderException.class);

        mockMvc.perform(put("/feedbacks/" + FEED_BACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEED_BACK_REQUEST02_ORDER_CANCELED))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }
}
