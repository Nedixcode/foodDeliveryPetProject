package backend.restaurantservice.repository;

import backend.restaurantservice.dto.DishDto;
import backend.restaurantservice.entity.Dish;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<Dish, UUID> {

    Page<Dish> findByIsAvailableIsTrue(Pageable pageable);

    Page<Dish> findByNameContainsIgnoreCaseAndIsAvailableTrue(String name, Pageable pageable);
}
