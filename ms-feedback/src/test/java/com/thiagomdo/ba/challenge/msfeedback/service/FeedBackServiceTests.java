package com.thiagomdo.ba.challenge.msfeedback.service;

import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedBackRepository;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.thiagomdo.ba.challenge.msfeedback.common.FeedBackConstants.FEED_BACK_DTO_LIST;
import static com.thiagomdo.ba.challenge.msfeedback.common.FeedBackConstants.FEED_BACK_LIST;
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
    }
}
