package backend.restaurantservice.service;

import backend.restaurantservice.dto.MenuItemCreateDto;
import backend.restaurantservice.dto.MenuItemDto;
import backend.restaurantservice.entity.MenuItem;
import backend.restaurantservice.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public List<MenuItemDto> getAllIsAvailable() {
        return menuItemRepository.findByIsAvailableIsTrue().
                stream().
                map(this::mapMenuItemToMenuItemDto).toList();
    }


    public MenuItemDto createMenuItem(MenuItemCreateDto menuItemCreateDto) {
        MenuItem menuItem = menuItemRepository.save(new MenuItem(
                menuItemCreateDto.getName(),
                menuItemCreateDto.getDescription(),
                menuItemCreateDto.getPrice(),
                true
        ));

        return mapMenuItemToMenuItemDto(menuItem);
    }

    private MenuItemDto mapMenuItemToMenuItemDto(MenuItem menuItem) {
        return new MenuItemDto(menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice());
    }
}
