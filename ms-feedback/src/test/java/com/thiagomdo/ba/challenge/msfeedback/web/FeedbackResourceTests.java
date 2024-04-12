package com.thiagomdo.ba.challenge.msfeedback.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msfeedback.resources.FeedBackResource;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedbackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedbackNotFoundException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.NotPossibleToCommentOrderException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.OrderNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msfeedback.common.FeedbackConstants.*;
import static org.hamcrest.Matchers.hasSize;
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
    @DisplayName("GetAllFeedBack: FeedBackInDB > ReturnsFeedBackDTOList :: Status200")
    @Description("Tests if the GET endpoint '/feedbacks' returns a list of feedback DTOs when there are feedbacks in the database, " +
    "verifying that it returns status code 200 (OK).")
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
    @DisplayName("GetAllFeedBack: ListEmptyInDB > ThrowsEmptyListException :: Status200")
    @Description("Tests if the GET endpoint '/feedbacks' throws EmptyListException when the list in the database is empty, " +
    "verifying that it returns status code 200 (OK).")
    void getAllFeedBack_With_ListEmptyInDB_ThrowsEmptyListException_Status200() throws Exception{
        when(feedBackService.getAll()).thenThrow(EmptyListException.class);

        mockMvc.perform(get("/feedbacks"))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GetFeedBackById: IdValid > ReturnsFeedBackDTO :: Status200")
    @Description("Tests if the GET endpoint '/feedbacks/{id}' returns a feedback DTO when a valid ID is provided, " +
    "verifying that it returns status code 200 (OK).")
    void getFeedBackById_With_IdValid_ReturnsFeedBackDTO_Status200() throws Exception{
        when(feedBackService.getById(FEEDBACK01.getId())).thenReturn(FEEDBACK01_DTO);

        mockMvc.perform(get("/feedbacks/" + FEEDBACK01.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(FEEDBACK01_DTO)));
    }

    @Test
    @DisplayName("GetFeedBackById: InvalidIdValid > ThrowsFeedBackNotFoundException :: Status404")
    @Description("Tests if the GET endpoint '/feedbacks/{id}' throws FeedbackNotFoundException when an invalid ID is provided, " +
    "verifying that it returns status code 404 (Not Found).")
    void getFeedBackById_With_InvalidIdValid_ThrowsFeedBackNotFoundException_Status400() throws Exception{
        when(feedBackService.getById("Id_Invalid")).thenThrow(FeedbackNotFoundException.class);

        mockMvc.perform(get("/feedbacks/Id_Invalid"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("CreateFeedBack: ValidData > ReturnsFeedBackDTO :: Status201")
    @Description("Tests if the POST endpoint '/feedbacks' creates a feedback with valid data and returns the created feedback DTO, " +
    "verifying that it returns status code 201 (Created).")
    void createFeedBack_With_ValidData_ReturnsFeedBackDTO_Status201() throws Exception{
        when(feedBackService.create(FEEDBACK_REQUEST01)).thenReturn(FEEDBACK_CREATED_IN_DB_DTO);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("CreateFeedBack: OrderIdInvalid > ThrowsOrderNotFoundException :: Status404")
    @Description("Tests if the POST endpoint '/feedbacks' throws OrderNotFoundException when an invalid order ID is provided, " +
    "verifying that it returns status code 404 (Not Found).")
    void createFeedBack_With_OrderIdInvalid_ThrowsOrderNotFoundException_Status404() throws Exception{
        when(feedBackService.create(FEEDBACK_REQUEST0_OrderIdInvalid)).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST0_OrderIdInvalid))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("CreateFeedBack: OrderAlreadyCANCELED > ThrowsNotPossibleToCommentOrderException :: Status400")
    @Description("Tests if the POST endpoint '/feedbacks' throws NotPossibleToCommentOrderException when attempting to create feedback for a canceled order, " +
    "verifying that it returns status code 400 (Bad Request).")
    void createFeedBack_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException() throws Exception{
        when(feedBackService.create(FEEDBACK_REQUEST02_ORDER_CANCELED)).thenThrow(NotPossibleToCommentOrderException.class);

        mockMvc.perform(post("/feedbacks")
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST02_ORDER_CANCELED))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("UpdateFeedBack: ValidData > ReturnsFeedBackDTO :: Status200")
    @Description("Tests if the PUT endpoint '/feedbacks/{id}' updates a feedback with valid data and returns the updated feedback DTO, " +
    "verifying that it returns status code 200 (OK).")
    void updateFeedBack_With_ValidData_ReturnsFeedBackDTO_Status200() throws Exception{
        when(feedBackService.update(FEEDBACK_UPDATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST01)).thenReturn(FEEDBACK_UPDATED_IN_DB_DTO);

        mockMvc.perform(put("/feedbacks/" + FEEDBACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("UpdateFeedBack: InvalidIdValid > ThrowsFeedBackNotFoundException :: Status400")
    @Description("Tests if the PUT endpoint '/feedbacks/{id}' throws FeedbackNotFoundException when an invalid ID is provided, " +
    "verifying that it returns status code 404 (Not Found).")
    void updateFeedBack_With_InvalidIdValid_ThrowsFeedBackNotFoundException_Status400() throws Exception{
        when(feedBackService.update("Id_Invalid", FEEDBACK_REQUEST01)).thenThrow(FeedbackNotFoundException.class);

        mockMvc.perform(put("/feedbacks/Id_Invalid")
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST01))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("UpdateFeedBack: OrderIdInvalid > ThrowsOrderNotFoundException :: Status404")
    @Description("Tests if the PUT endpoint '/feedbacks/{id}' throws OrderNotFoundException when an invalid order ID is provided, " +
    "verifying that it returns status code 404 (Not Found).")
    void updateFeedBack_With_OrderIdInvalid_ThrowsOrderNotFoundException_Status404() throws Exception{
        when(feedBackService.update(FEEDBACK_UPDATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST0_OrderIdInvalid)).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(put("/feedbacks/" + FEEDBACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST0_OrderIdInvalid))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("UpdateFeedBack: OrderAlreadyCANCELED > ThrowsNotPossibleToCommentOrderException :: Status400")
    @Description("Tests if the PUT endpoint '/feedbacks/{id}' throws NotPossibleToCommentOrderException when attempting to update feedback for a canceled order, " +
    "verifying that it returns status code 400 (Bad Request).")
    void updateFeedBack_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException() throws Exception{
        when(feedBackService.update(FEEDBACK_UPDATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST02_ORDER_CANCELED)).thenThrow(NotPossibleToCommentOrderException.class);

        mockMvc.perform(put("/feedbacks/" + FEEDBACK_UPDATED_IN_DB_DTO.getId())
        .content(objectMapper.writeValueAsString(FEEDBACK_REQUEST02_ORDER_CANCELED))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DeleteFeedBack: IdFeedbackExistingInDB > ReturnsVoid :: Status204")
    @Description("Tests if the DELETE endpoint '/feedbacks/{id}' deletes a feedback when the ID exists in the database, " +
    "verifying that it returns status code 204 (No Content).")
    void deleteFeedBack_With_IdFeedbackExistingInDB_ReturnsVoid_Status204() throws Exception{
        doNothing().when(feedBackService).delete(FEEDBACK01_DTO.getId());

        mockMvc.perform(delete("/feedbacks/" + FEEDBACK01_DTO.getId()))
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DeleteFeedBack: IdFeedbackUnexistingInDB > ReturnsVoid :: Status404")
    @Description("Tests if the DELETE endpoint '/feedbacks/{id}' returns status code 404 (Not Found) when attempting to delete a feedback with an ID that does not exist in the database.")
    void deleteFeedBack_With_IdFeedbackUnexistingInDB_ReturnsVoid_Status404() throws Exception{
        doThrow(OrderNotFoundException.class).when(feedBackService).delete("Id_invalid");

        mockMvc.perform(delete("/feedbacks/Id_invalid"))
        .andExpect(status().isNotFound());
    }

}
