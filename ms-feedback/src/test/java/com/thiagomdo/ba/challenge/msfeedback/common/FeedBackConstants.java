package com.thiagomdo.ba.challenge.msfeedback.common;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.FeedBack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FeedBackConstants {

    public static final FeedBack FEED_BACK01 = new FeedBack("6605903e1e2d5c55c2017888", Scale.NEUTRAL, "Neutro", "6605903e1e2d5c55c2017225");
    public static final FeedBack FEED_BACK02 = new FeedBack("6605903e1e2d5c55c2017999", Scale.DISSATISFIED, "Bad", "6605903e1e2d5c55c2017225");
    public static final FeedBack FEED_BACK03 = new FeedBack("6605903e1e2d5c55c2017444", Scale.VERY_SATISFIED, "It's good", "6605903e1e2d5c55c2017223");

    public static final FeedBackDTO FEED_BACK01_DTO = new FeedBackDTO("6605903e1e2d5c55c2017888", Scale.NEUTRAL, "Neutro", "6605903e1e2d5c55c2017225");
    public static final FeedBackDTO FEED_BACK02_DTO = new FeedBackDTO("6605903e1e2d5c55c2017999", Scale.DISSATISFIED, "Bad", "6605903e1e2d5c55c2017225");
    public static final FeedBackDTO FEED_BACK03_DTO = new FeedBackDTO("6605903e1e2d5c55c2017444", Scale.VERY_SATISFIED, "It's good", "6605903e1e2d5c55c2017223");

    public static final List<FeedBack> FEED_BACK_LIST = Arrays.asList(FEED_BACK01, FEED_BACK02, FEED_BACK03);

    public static final List<FeedBackDTO> FEED_BACK_DTO_LIST = FEED_BACK_LIST.stream().map(FeedBackDTO::new).collect(Collectors.toList());

}
