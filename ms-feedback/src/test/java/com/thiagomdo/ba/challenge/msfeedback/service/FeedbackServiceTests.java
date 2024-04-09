package com.thiagomdo.ba.challenge.msfeedback.service;

import com.thiagomdo.ba.challenge.msfeedback.clients.OrderFeign;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedbackDTO;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedbackRepository;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedbackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedbackNotFoundException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.NotPossibleToCommentOrderException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.thiagomdo.ba.challenge.msfeedback.common.FeedbackConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTests {

    @InjectMocks
    FeedbackService feedBackService;

    @Mock
    FeedbackRepository feedBackRepository;

    @Mock
    OrderFeign orderFeign;

    @Test
    void getAll_With_FeedBackInDB_ReturnsFeedBackDTOList(){
        when(feedBackRepository.findAll()).thenReturn(FEEDBACK_LIST);

        List<FeedbackDTO> result = feedBackService.getAll();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(FEEDBACK_DTO_LIST);
        verify(feedBackRepository, times(1)).findAll();
    }

    @Test
    void getAll_With_ListEmptyInDB_ThrowsEmptyListException(){
        when(feedBackRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EmptyListException.class, () -> feedBackService.getAll());
        verify(feedBackRepository, times(1)).findAll();
    }

    @Test
    void getById_With_IdValid_ReturnsFeedBackDTO(){
        when(feedBackRepository.findById(FEEDBACK01.getId())).thenReturn(Optional.of(FEEDBACK01));

        FeedbackDTO result = feedBackService.getById(FEEDBACK01.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(FEEDBACK01_DTO);
        verify(feedBackRepository, times(1)).findById(anyString());
    }

    @Test
    void getById_With_InvalidIdValid_ThrowsFeedBackNotFoundException(){
        when(feedBackRepository.findById("Id_Invalid")).thenThrow(FeedbackNotFoundException.class);

        assertThrows(FeedbackNotFoundException.class, () -> feedBackService.getById("Id_Invalid"));
        verify(feedBackRepository, times(1)).findById(anyString());
    }

    @Test
    void create_With_ValidData_ReturnsFeedBackDTO(){
        when(orderFeign.getOrderById(FEEDBACK_REQUEST01.getOrderId())).thenReturn(ORDER_MODEL01_CONFIRMED);
        when(feedBackRepository.save(FEEDBACK_CREATED)).thenReturn(FEEDBACK_CREATED_IN_DB);

        FeedbackDTO result = feedBackService.create(FEEDBACK_REQUEST01);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(FEEDBACK_CREATED_IN_DB_DTO);
        verify(feedBackRepository, times(1)).save(FEEDBACK_CREATED);
    }

    @Test
    void create_With_OrderIdInvalid_ThrowsOrderNotFoundException(){
        when(orderFeign.getOrderById(FEEDBACK_REQUEST0_OrderIdInvalid.getOrderId())).thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () -> feedBackService.create(FEEDBACK_REQUEST0_OrderIdInvalid));
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void create_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException(){
        when(orderFeign.getOrderById(FEEDBACK_REQUEST02_ORDER_CANCELED.getOrderId())).thenReturn(ORDER_MODEL01_CANCELED);

        assertThrows(NotPossibleToCommentOrderException.class, () -> feedBackService.create(FEEDBACK_REQUEST02_ORDER_CANCELED));
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void update_With_ValidData_ReturnsFeedBackDTO(){
        when(feedBackRepository.findById(FEEDBACK_CREATED_IN_DB_DTO.getId())).thenReturn(Optional.of(FEEDBACK_CREATED_IN_DB));
        when(orderFeign.getOrderById(FEEDBACK_REQUEST02_ORDER_TO_UPDATE.getOrderId())).thenReturn(ORDER_MODEL01_CONFIRMED);
        when(feedBackRepository.save(FEEDBACK_UPDATED_IN_DB)).thenReturn(FEEDBACK_UPDATED_IN_DB);

        FeedbackDTO result = feedBackService.update(FEEDBACK_CREATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST02_ORDER_TO_UPDATE);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(FEEDBACK_UPDATED_IN_DB_DTO);
        verify(feedBackRepository, times(1)).save(FEEDBACK_UPDATED_IN_DB);
    }

    @Test
    void update_With_InvalidIdValid_ThrowsFeedBackNotFoundException(){
        when(feedBackRepository.findById("Id_Invalid")).thenThrow(FeedbackNotFoundException.class);

        assertThrows(FeedbackNotFoundException.class, () -> feedBackService.update("Id_Invalid", FEEDBACK_REQUEST02_ORDER_TO_UPDATE));
        verify(feedBackRepository, times(1)).findById(anyString());
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void update_With_OrderIdInvalid_ThrowsOrderNotFoundException(){
        when(feedBackRepository.findById(FEEDBACK_CREATED_IN_DB_DTO.getId())).thenReturn(Optional.of(FEEDBACK_CREATED_IN_DB));
        when(orderFeign.getOrderById(FEEDBACK_REQUEST0_OrderIdInvalid.getOrderId())).thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () -> feedBackService.update(FEEDBACK_UPDATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST0_OrderIdInvalid));
        verify(feedBackRepository, times(1)).findById(anyString());
        verify(orderFeign, times(1)).getOrderById(anyString());
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void update_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException(){
        when(feedBackRepository.findById(FEEDBACK_CREATED_IN_DB_DTO.getId())).thenReturn(Optional.of(FEEDBACK_CREATED_IN_DB));
        when(orderFeign.getOrderById(FEEDBACK_REQUEST02_ORDER_CANCELED.getOrderId())).thenReturn(ORDER_MODEL01_CANCELED);

        assertThrows(NotPossibleToCommentOrderException.class, () -> feedBackService.update(FEEDBACK_CREATED_IN_DB_DTO.getId(), FEEDBACK_REQUEST02_ORDER_CANCELED));
        verify(feedBackRepository, times(1)).findById(anyString());
        verify(orderFeign, times(1)).getOrderById(anyString());
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void delete_With_IdFeedbackExistingInDB_ReturnsVoid(){
        when(feedBackRepository.findById(FEEDBACK01_DTO.getId())).thenReturn(Optional.of(FEEDBACK01));

        feedBackService.delete(FEEDBACK01_DTO.getId());

        verify(feedBackRepository, times(1)).findById(anyString());
        verify(feedBackRepository, times(1)).delete(any());

    }

    @Test
    void delete_With_IdFeedbackUnexistingInDb_ThrowsFeedbackNotFoundException(){
        when(feedBackRepository.findById("Id_invalid")).thenThrow(FeedbackNotFoundException.class);

        assertThrows(FeedbackNotFoundException.class, () -> feedBackService.delete("Id_invalid"));
        verify(feedBackRepository, never()).deleteById(any(String.class));
    }


}
