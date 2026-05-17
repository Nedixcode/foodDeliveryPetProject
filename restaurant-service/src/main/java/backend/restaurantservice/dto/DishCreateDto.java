package backend.restaurantservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class DishCreateDto {

    @NotBlank(message = "имя не может быть пустым")
    private String name;

    private String description;

    @NotNull(message = "цена не может быть пустой")
    @Positive(message = "цена должна быть больше нуля")
    private BigDecimal price;
}
