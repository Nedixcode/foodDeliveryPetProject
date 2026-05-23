package backend.orderservice.service;

import backend.orderservice.dto.EventPayload;
import backend.orderservice.dto.OrderCreateDto;
import backend.orderservice.dto.OrderItemCreateDto;
import backend.orderservice.entity.*;
import backend.orderservice.enums.EventType;
import backend.orderservice.enums.OrderStatus;
import backend.orderservice.repository.OrderRepository;
import backend.orderservice.repository.OutboxMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OutboxMessageRepository outboxMessageRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public UUID createOrder(OrderCreateDto orderCreateDto, UUID userId) {
        BigDecimal totalAmount = orderCreateDto.getItemList().stream().
                map(dto -> dto.getPrice().
                        multiply(BigDecimal.valueOf(dto.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order(
                userId,
                orderCreateDto.getRestaurantId(),
                orderCreateDto.getIdempotencyKey(),
                totalAmount,
                OrderStatus.PENDING
        );

        List<OrderItem> orderItemList = orderCreateDto.getItemList().
                stream().map(dto -> mapCreateDtoToEntity(dto, order)).toList();

        order.setOrderItemlist(orderItemList);

        Order savedOrder = orderRepository.save(order);

        EventPayload eventPayloadObject = new EventPayload(savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getRestaurantId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus()
        );

        String eventPayloadJson = objectMapper.writeValueAsString(eventPayloadObject);

        OutboxMessage outboxMessage = new OutboxMessage(savedOrder.getId(),
                EventType.ORDER_CREATED,
                eventPayloadJson);

        outboxMessageRepository.save(outboxMessage);

        return savedOrder.getId();
    }

    private OrderItem mapCreateDtoToEntity(OrderItemCreateDto dto, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(dto.getPrice());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setDishId(dto.getDishId());
        orderItem.setOrder(order);

        return orderItem;
    }
}
