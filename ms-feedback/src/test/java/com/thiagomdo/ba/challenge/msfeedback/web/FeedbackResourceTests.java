package com.thiagomdo.ba.challenge.msfeedback.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msfeedback.resources.FeedBackResource;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedbackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedbackNotFoundException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.NotPossibleToCommentOrderException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msfeedback.common.FeedbackConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedBackResource.class)
class FeedbackResourceTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FeedbackService feedBackService;


    @Test
    void getAllFeedBack_With_With_FeedBackInDB_ReturnsFeedBackDTOList_Status200() throws Exception {
        when(feedBackService.getAll()).thenReturn(FEEDBACK_DTO_LIST);

        mockMvc.perform(get("/feedbacks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id").value(FEEDBACK_LIST.get(0).getId()))
        .andExpect(jsonPath("$[1].id").value(FEEDBACK_LIST.get(1).getId()))
        .andExpect(jsonPath("$[2].id").value(FEEDBACK_LIST.get(2).getId()));
    }

    @Test
    void getAllFeedBack_With_ListEmptyInDB_ThrowsEmptyListException_Status200() throws Exception{
        when(feedBackService.getAll()).thenThrow(EmptyListException.class);

        mockMvc.perform(get("/feedbacks"))
        .andExpect(status().isOk());
    }

    @Test
    void getFeedBackById_With_IdValid_ReturnsFeedBackDTO_Status200() throws Exception{
        when(feedBackService.getById(FEEDBACK01.getId())).thenReturn(FEEDBACK01_DTO);

        mockMvc.perform(get("/feedbacks/" + FEEDBACK01.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(FEEDBACK01_DTO)));
    }

    @Test
    void getFeedBackById_With_InvalidIdValid_ThrowsFeedBackNotFoundException_Status400() throws Exception{
        when(feedBackService.getById("Id_Invalid")).thenThrow(FeedbackNotFoundException.class);

        mockMvc.perform(get("/feedbacks/Id_Invalid"))
            .andExpect(status().isNotFound());
    }

    @Test
    void createFeedBack_With_ValidData_ReturnsFeedBackDTO_Status201() throws Exception{
        when(feedBackService.create(FEEDBACK_REQUEST01)).thenReturn(FEEDBACK_CREATED_IN_DB_DTO);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }

    @Test
    void createFeedBack_With_OrderIdInvalid_ThrowsOrderNotFoundException_Status404() throws Exception{
        when(feedBackService.create(FEEDBACK_REQUEST0_OrderIdInvalid)).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST0_OrderIdInvalid))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void createFeedBack_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException() throws Exception{
        when(feedBackService.create(FEEDBACK_REQUEST02_ORDER_CANCELED)).thenThrow(NotPossibleToCommentOrderException.class);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST02_ORDER_CANCELED))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateFeedBack_With_ValidData_ReturnsFeedBackDTO_Status200() throws Exception{
        when(feedBackService.update(FEEDBACK_UPDATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST01)).thenReturn(FEEDBACK_UPDATED_IN_DB_DTO);

        mockMvc.perform(put("/feedbacks/" + FEEDBACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    void updateFeedBack_With_InvalidIdValid_ThrowsFeedBackNotFoundException_Status400() throws Exception{
        when(feedBackService.update("Id_Invalid", FEEDBACK_REQUEST01)).thenThrow(FeedbackNotFoundException.class);

        mockMvc.perform(put("/feedbacks/Id_Invalid")
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void updateFeedBack_With_OrderIdInvalid_ThrowsOrderNotFoundException_Status404() throws Exception{
        when(feedBackService.update(FEEDBACK_UPDATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST0_OrderIdInvalid)).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(put("/feedbacks/" + FEEDBACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST0_OrderIdInvalid))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void updateFeedBack_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException() throws Exception{
        when(feedBackService.update(FEEDBACK_UPDATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST02_ORDER_CANCELED)).thenThrow(NotPossibleToCommentOrderException.class);

        mockMvc.perform(put("/feedbacks/" + FEEDBACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST02_ORDER_CANCELED))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void deleteFeedBack_With_IdFeedbackExistingInDB_ReturnsVoid_Status204() throws Exception{
        doNothing().when(feedBackService).delete(FEEDBACK01_DTO.getId());

        mockMvc.perform(delete("/feedbacks/" + FEEDBACK01_DTO.getId()))
        .andExpect(status().isNoContent());
    }

    @Test
    void deleteFeedBack_With_IdFeedbackUnexistingInDB_ReturnsVoid_Status404() throws Exception{
        doThrow(OrderNotFoundException.class).when(feedBackService).delete("Id_invalid");

        mockMvc.perform(delete("/feedbacks/Id_invalid"))
        .andExpect(status().isNotFound());
    }

}
