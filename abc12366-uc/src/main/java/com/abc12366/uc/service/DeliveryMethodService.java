package com.abc12366.uc.service;

import com.abc12366.uc.model.DeliveryMethod;
import com.abc12366.uc.model.bo.DeliveryMethodBO;
import com.abc12366.uc.model.bo.DeliveryMethodUpdateBO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeliveryMethodService {

    List<DeliveryMethod> selectList(DeliveryMethod deliveryMethod);

    DeliveryMethodBO add(DeliveryMethodBO deliveryMethodBO);

    DeliveryMethodBO update(DeliveryMethodBO deliveryMethodBO);

    DeliveryMethodBO selectDeliveryMethod(String id);

    void delete(String id);

    void enable(DeliveryMethodUpdateBO deliveryMethodUpdateBO);
}
