package com.thiagomdo.ba.challenge.msfeedback.common;

import com.thiagomdo.ba.challenge.msfeedback.clients.models.OrderModel;
import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.FeedBack;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedBackRequest;

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


    public static final FeedBackRequest FEED_BACK_REQUEST01 = new FeedBackRequest(Scale.NEUTRAL, "Neutro", "6605903e1e2d5c55c2017223");
    public static final FeedBackRequest FEED_BACK_REQUEST0_OrderIdInvalid = new FeedBackRequest(Scale.NEUTRAL, "Neutro", "OrderIdInvalid");
    public static final FeedBackRequest FEED_BACK_REQUEST02_ORDER_CANCELED = new FeedBackRequest(Scale.NEUTRAL, "Neutro", "6605903e1e2d5c55c20171111");
    public static final FeedBackRequest FEED_BACK_REQUEST02_ORDER_TO_UPDATE = new FeedBackRequest(Scale.SATISFIED, "Any other comment", "6605903e1e2d5c55c20171111");

    public static final OrderModel ORDER_MODEL01_CONFIRMED = new OrderModel("6605903e1e2d5c55c2017223", "CONFIRMED");
    public static final OrderModel ORDER_MODEL01_CANCELED = new OrderModel("6605903e1e2d5c55c20171111", "CANCELED");

    public static final FeedBack FEED_BACK_CREATED = new FeedBack(FEED_BACK_REQUEST01, ORDER_MODEL01_CONFIRMED.getId());
    public static final FeedBack FEED_BACK_UPDATED = new FeedBack(FEED_BACK_REQUEST02_ORDER_TO_UPDATE, ORDER_MODEL01_CONFIRMED.getId());

    public static final FeedBack FEED_BACK_CREATED_IN_DB = new FeedBack("6605903e1e2d5c55c2017777", FEED_BACK_CREATED);
    public static final FeedBackDTO FEED_BACK_CREATED_IN_DB_DTO = new FeedBackDTO(FEED_BACK_CREATED_IN_DB);

    public static final FeedBack FEED_BACK_UPDATED_IN_DB = new FeedBack("6605903e1e2d5c55c2017777", FEED_BACK_UPDATED);
    public static final FeedBackDTO FEED_BACK_UPDATED_IN_DB_DTO = new FeedBackDTO(FEED_BACK_UPDATED_IN_DB);


}
