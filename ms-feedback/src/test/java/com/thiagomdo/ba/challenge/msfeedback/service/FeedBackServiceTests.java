package com.thiagomdo.ba.challenge.msfeedback.service;

import com.thiagomdo.ba.challenge.msfeedback.clients.OrderFeign;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedBackRepository;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedBackNotFoundException;
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

import static com.thiagomdo.ba.challenge.msfeedback.common.FeedBackConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedBackServiceTests {

    @InjectMocks
    FeedBackService feedBackService;

    @Mock
    FeedBackRepository feedBackRepository;

    @Mock
    OrderFeign orderFeign;

    @Test
    void getAll_With_FeedBackInDB_ReturnsFeedBackDTOList(){
        when(feedBackRepository.findAll()).thenReturn(FEED_BACK_LIST);

        List<FeedBackDTO> result = feedBackService.getAll();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(FEED_BACK_DTO_LIST);
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
        when(feedBackRepository.findById(FEED_BACK01.getId())).thenReturn(Optional.of(FEED_BACK01));

        FeedBackDTO result = feedBackService.getById(FEED_BACK01.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(FEED_BACK01_DTO);
        verify(feedBackRepository, times(1)).findById(anyString());
    }

    @Test
    void getById_With_InvalidIdValid_ThrowsFeedBackNotFoundException(){
        when(feedBackRepository.findById("Id_Invalid")).thenThrow(FeedBackNotFoundException.class);

        assertThrows(FeedBackNotFoundException.class, () -> feedBackService.getById("Id_Invalid"));
        verify(feedBackRepository, times(1)).findById(anyString());
    }

    @Test
    void create_With_ValidData_ReturnsFeedBackDTO(){
        when(orderFeign.getOrderById(FEED_BACK_REQUEST01.getOrderId())).thenReturn(ORDER_MODEL01_CONFIRMED);
        when(feedBackRepository.save(FEED_BACK_CREATED)).thenReturn(FEED_BACK_CREATED_IN_DB);

        FeedBackDTO result = feedBackService.create(FEED_BACK_REQUEST01);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(FEED_BACK_CREATED_IN_DB_DTO);
        verify(feedBackRepository, times(1)).save(FEED_BACK_CREATED);
    }

    @Test
    void create_With_OrderIdInvalid_ThrowsOrderNotFoundException(){
        when(orderFeign.getOrderById(FEED_BACK_REQUEST0_OrderIdInvalid.getOrderId())).thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () -> feedBackService.create(FEED_BACK_REQUEST0_OrderIdInvalid));
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void create_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException(){
        when(orderFeign.getOrderById(FEED_BACK_REQUEST02_ORDER_CANCELED.getOrderId())).thenReturn(ORDER_MODEL01_CANCELED);

        assertThrows(NotPossibleToCommentOrderException.class, () -> feedBackService.create(FEED_BACK_REQUEST02_ORDER_CANCELED));
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void update_With_ValidData_ReturnsFeedBackDTO(){
        when(feedBackRepository.findById(FEED_BACK_CREATED_IN_DB_DTO.getId())).thenReturn(Optional.of(FEED_BACK_CREATED_IN_DB));
        when(orderFeign.getOrderById(FEED_BACK_REQUEST02_ORDER_TO_UPDATE.getOrderId())).thenReturn(ORDER_MODEL01_CONFIRMED);
        when(feedBackRepository.save(FEED_BACK_UPDATED_IN_DB)).thenReturn(FEED_BACK_UPDATED_IN_DB);

        FeedBackDTO result = feedBackService.update(FEED_BACK_CREATED_IN_DB_DTO.getId(), FEED_BACK_REQUEST02_ORDER_TO_UPDATE);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(FEED_BACK_UPDATED_IN_DB_DTO);
        verify(feedBackRepository, times(1)).save(FEED_BACK_UPDATED_IN_DB);
    }

    @Test
    void update_With_InvalidIdValid_ThrowsFeedBackNotFoundException(){
        when(feedBackRepository.findById("Id_Invalid")).thenThrow(FeedBackNotFoundException.class);

        assertThrows(FeedBackNotFoundException.class, () -> feedBackService.update("Id_Invalid", FEED_BACK_REQUEST02_ORDER_TO_UPDATE));
        verify(feedBackRepository, times(1)).findById(anyString());
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void update_With_OrderIdInvalid_ThrowsOrderNotFoundException(){
        when(feedBackRepository.findById(FEED_BACK_CREATED_IN_DB_DTO.getId())).thenReturn(Optional.of(FEED_BACK_CREATED_IN_DB));
        when(orderFeign.getOrderById(FEED_BACK_REQUEST0_OrderIdInvalid.getOrderId())).thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () -> feedBackService.update(FEED_BACK_UPDATED_IN_DB_DTO.getId(), FEED_BACK_REQUEST0_OrderIdInvalid));
        verify(feedBackRepository, times(1)).findById(anyString());
        verify(orderFeign, times(1)).getOrderById(anyString());
        verify(feedBackRepository, never()).save(any());
    }

    @Test
    void update_With_OrderAlreadyCANCELED_ThrowsNotPossibleToCommentOrderException(){
        when(feedBackRepository.findById(FEED_BACK_CREATED_IN_DB_DTO.getId())).thenReturn(Optional.of(FEED_BACK_CREATED_IN_DB));
        when(orderFeign.getOrderById(FEED_BACK_REQUEST02_ORDER_CANCELED.getOrderId())).thenReturn(ORDER_MODEL01_CANCELED);

        assertThrows(NotPossibleToCommentOrderException.class, () -> feedBackService.update(FEED_BACK_CREATED_IN_DB_DTO.getId(), FEED_BACK_REQUEST02_ORDER_CANCELED));
        verify(feedBackRepository, times(1)).findById(anyString());
        verify(orderFeign, times(1)).getOrderById(anyString());
        verify(feedBackRepository, never()).save(any());
    }


}
