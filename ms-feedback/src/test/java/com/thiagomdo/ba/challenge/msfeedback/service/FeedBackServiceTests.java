package com.thiagomdo.ba.challenge.msfeedback.service;

import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedBackRepository;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedBackNotFoundException;
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

}
