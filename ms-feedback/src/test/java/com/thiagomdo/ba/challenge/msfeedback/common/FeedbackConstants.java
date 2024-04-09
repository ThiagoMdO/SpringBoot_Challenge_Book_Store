package com.thiagomdo.ba.challenge.msfeedback.common;

import com.thiagomdo.ba.challenge.msfeedback.clients.models.OrderModel;
import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedbackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.Feedback;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedbackRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackConstants {

    public static final Feedback FEEDBACK01 = new Feedback("6605903e1e2d5c55c2017888", Scale.NEUTRAL, "Neutro", "6605903e1e2d5c55c2017225");
    public static final Feedback FEEDBACK02 = new Feedback("6605903e1e2d5c55c2017999", Scale.DISSATISFIED, "Bad", "6605903e1e2d5c55c2017225");
    public static final Feedback FEEDBACK03 = new Feedback("6605903e1e2d5c55c2017444", Scale.VERY_SATISFIED, "It's good", "6605903e1e2d5c55c2017223");

    public static final FeedbackDTO FEEDBACK01_DTO = new FeedbackDTO("6605903e1e2d5c55c2017888", Scale.NEUTRAL, "Neutro", "6605903e1e2d5c55c2017225");
    public static final FeedbackDTO FEEDBACK02_DTO = new FeedbackDTO("6605903e1e2d5c55c2017999", Scale.DISSATISFIED, "Bad", "6605903e1e2d5c55c2017225");
    public static final FeedbackDTO FEEDBACK03_DTO = new FeedbackDTO("6605903e1e2d5c55c2017444", Scale.VERY_SATISFIED, "It's good", "6605903e1e2d5c55c2017223");

    public static final List<Feedback> FEEDBACK_LIST = Arrays.asList(FEEDBACK01, FEEDBACK02, FEEDBACK03);

    public static final List<FeedbackDTO> FEEDBACK_DTO_LIST = FEEDBACK_LIST.stream().map(FeedbackDTO::new).collect(Collectors.toList());


    public static final FeedbackRequest FEEDBACK_REQUEST01 = new FeedbackRequest(Scale.NEUTRAL, "Neutro", "6605903e1e2d5c55c2017223");
    public static final FeedbackRequest FEEDBACK_REQUEST0_OrderIdInvalid = new FeedbackRequest(Scale.NEUTRAL, "Neutro", "OrderIdInvalid");
    public static final FeedbackRequest FEEDBACK_REQUEST02_ORDER_CANCELED = new FeedbackRequest(Scale.NEUTRAL, "Neutro", "6605903e1e2d5c55c20171111");
    public static final FeedbackRequest FEEDBACK_REQUEST02_ORDER_TO_UPDATE = new FeedbackRequest(Scale.SATISFIED, "Any other comment", "6605903e1e2d5c55c20171111");

    public static final OrderModel ORDER_MODEL01_CONFIRMED = new OrderModel("6605903e1e2d5c55c2017223", "CONFIRMED");
    public static final OrderModel ORDER_MODEL01_CANCELED = new OrderModel("6605903e1e2d5c55c20171111", "CANCELED");

    public static final Feedback FEEDBACK_CREATED = new Feedback(FEEDBACK_REQUEST01, ORDER_MODEL01_CONFIRMED.getId());
    public static final Feedback FEEDBACK_UPDATED = new Feedback(FEEDBACK_REQUEST02_ORDER_TO_UPDATE, ORDER_MODEL01_CONFIRMED.getId());

    public static final Feedback FEEDBACK_CREATED_IN_DB = new Feedback("6605903e1e2d5c55c2017777", FEEDBACK_CREATED);
    public static final FeedbackDTO FEEDBACK_CREATED_IN_DB_DTO = new FeedbackDTO(FEEDBACK_CREATED_IN_DB);

    public static final Feedback FEEDBACK_UPDATED_IN_DB = new Feedback("6605903e1e2d5c55c2017777", FEEDBACK_UPDATED);
    public static final FeedbackDTO FEEDBACK_UPDATED_IN_DB_DTO = new FeedbackDTO(FEEDBACK_UPDATED_IN_DB);


}
