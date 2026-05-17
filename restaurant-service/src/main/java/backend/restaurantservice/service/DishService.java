package backend.restaurantservice.service;

import backend.restaurantservice.dto.DishCreateDto;
import backend.restaurantservice.dto.DishDto;
import backend.restaurantservice.entity.Dish;
import backend.restaurantservice.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    @Cacheable(value = "available_dishes")
    public Page<DishDto> getAllIsAvailable(Pageable pageable) {
        return dishRepository.findByIsAvailableIsTrue(pageable).
                map(this::mapDishToDishDto);
    }

    @CacheEvict(value = "available_dishes", allEntries = true)
    public DishDto createDish(DishCreateDto dishCreateDto) {
        Dish dish = dishRepository.save(new Dish(
                dishCreateDto.getName(),
                dishCreateDto.getDescription(),
                dishCreateDto.getPrice(),
                true
        ));

        return mapDishToDishDto(dish);
    }

    private DishDto mapDishToDishDto(Dish dish) {
        return new DishDto(dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice());
    }

    @CacheEvict(value = "available_dishes", allEntries = true)
    public DishDto updateDish(UUID id, DishCreateDto dishCreateDto) {
        Dish dishToUpdate = dishRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("блюдо с таким id не найдено"));

        dishToUpdate.setName(dishCreateDto.getName());
        dishToUpdate.setDescription(dishCreateDto.getDescription());
        dishToUpdate.setPrice(dishCreateDto.getPrice());

        Dish updatedDish = dishRepository.save(dishToUpdate);

        return mapDishToDishDto(updatedDish);
    }

    @CacheEvict(value = "available_dishes", allEntries = true)
    public String changeStatus(UUID id, boolean isActivate) {
        Dish dishToUpdate = dishRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("блюдо с таким id не найдено"));

        dishToUpdate.setIsAvailable(isActivate);
        dishRepository.save(dishToUpdate);

        return "статус блюда изменен на " + (isActivate ? "activate" : "deactivate");
    }

    @CacheEvict(value = "available_dishes", allEntries = true)
    public void deleteDish(UUID id) {
        Dish dishToDelete = dishRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("блюдо с таким id не найдено"));

        dishRepository.delete(dishToDelete);
    }

    public Page<DishDto> searchDish(String keyword, Pageable pageable) {
        return dishRepository.findByNameContainsIgnoreCaseAndIsAvailableTrue(keyword, pageable).
                map(this::mapDishToDishDto);
    }
}
