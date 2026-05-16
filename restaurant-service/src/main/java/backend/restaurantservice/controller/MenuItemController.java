package backend.restaurantservice.controller;

import backend.restaurantservice.dto.MenuItemCreateDto;
import backend.restaurantservice.dto.MenuItemDto;
import backend.restaurantservice.entity.MenuItem;
import backend.restaurantservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/restaurant-service")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping()
    public ResponseEntity<List<MenuItemDto>> getAllIsAvailable() {
        return ResponseEntity.ok(menuItemService.getAllIsAvailable());
    }

    @PostMapping
    public ResponseEntity<MenuItemDto> createMenuItem(
            @RequestBody MenuItemCreateDto menuItemCreateDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(menuItemService.createMenuItem(menuItemCreateDto));
    }
}
