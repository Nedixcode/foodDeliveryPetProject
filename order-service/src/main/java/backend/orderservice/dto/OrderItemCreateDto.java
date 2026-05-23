package backend.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OrderItemCreateDto {

    @NotNull(message = "отсутствует id блюда")
    private UUID dishId;

    @NotNull(message = "отсутствует цена позиции")
    @Positive(message = "цена должна быть больше нуля")
    private BigDecimal price;

    @NotNull(message = "отсутствует количество позиций")
    @Min(value = 1, message = "количество товара должно быть минимум 1")
    private Integer quantity;
}
