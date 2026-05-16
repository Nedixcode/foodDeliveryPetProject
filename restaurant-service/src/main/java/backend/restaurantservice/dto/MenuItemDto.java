package backend.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class MenuItemDto {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
}
