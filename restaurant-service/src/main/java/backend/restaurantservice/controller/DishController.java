package backend.restaurantservice.controller;

import backend.restaurantservice.dto.DishCreateDto;
import backend.restaurantservice.dto.DishDto;
import backend.restaurantservice.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/api/restaurant-service")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping()
    public ResponseEntity<Page<DishDto>> getAllIsAvailable(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(dishService.getAllIsAvailable(pageable));
    }

    @PostMapping
    public ResponseEntity<DishDto> createDish(
            @Valid @RequestBody DishCreateDto dishCreateDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(dishService.createDish(dishCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDto> updateDish(
            @PathVariable UUID id,
            @Valid @RequestBody DishCreateDto dishCreateDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(dishService.updateDish(id, dishCreateDto));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<String> activateDish(@PathVariable UUID id) {
        return ResponseEntity.ok(dishService.changeStatus(id, true));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateDish(@PathVariable UUID id) {
        return ResponseEntity.ok(dishService.changeStatus(id, false));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable UUID id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<Page<DishDto>> searchDish(@PathVariable String keyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(dishService.searchDish(keyword, pageable));
    }
}
