package backend.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderCreateDto {

    @NotNull(message = "отсутствует id ресторана")
    private UUID restaurantId;

    @NotBlank(message = "ключ идемпотентности не может быть пустой")
    private String idempotencyKey;

    @Valid
    @NotEmpty(message = "список позиций заказа не может быть пустым")
    private List<OrderItemCreateDto> itemList;
}
