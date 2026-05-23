package backend.orderservice.dto;

import backend.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EventPayload {

    private UUID orderId;

    private UUID userId;

    private UUID restaurantId;

    private BigDecimal totalAmount;

    private OrderStatus status;
}
