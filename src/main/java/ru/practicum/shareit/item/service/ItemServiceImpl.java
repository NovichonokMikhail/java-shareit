package ru.practicum.shareit.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.InMemoryItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dao.InMemoryUserStorage;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class ItemServiceImpl implements ItemService {
    public static final NotFoundException ITEM_NOT_FOUND = new NotFoundException("Item with this id does not exist");
    InMemoryItemStorage itemStorage;
    InMemoryUserStorage userStorage;

    @Override
    public ItemDto create(Item item, Long ownerId) {
        User owner = userStorage.get(ownerId).orElseThrow(() -> UserServiceImpl.USER_NOT_FOUND);
        item.setOwnerId(ownerId);
        Item createdItem = itemStorage.create(item);
        owner.getItems().add(createdItem.getId());
        return ItemDtoMapper.itemToDto(createdItem);
    }

    @Override
    public ItemDto remove(Long id) {
        userStorage.get(id).orElseThrow(() -> UserServiceImpl.USER_NOT_FOUND);
        return ItemDtoMapper.itemToDto(itemStorage.delete(id));
    }

    @Override
    public ItemDto modify(final ItemDto itemDto, final long ownerId) {
        // Checking owner and item existence
        final User owner = userStorage.get(ownerId).orElseThrow(() -> UserServiceImpl.USER_NOT_FOUND);
        final Item origin = itemStorage.get(itemDto.getId()).orElseThrow(() -> ITEM_NOT_FOUND);
        // Validating ownership of an item
        if (!owner.getItems().contains(origin.getId()))
            throw new NotFoundException("This user cannot access this item");
        // Updating and returning in proper form
        final Item updItem = itemStorage.update(ItemDtoMapper.dtoToItem(itemDto, origin));
        return ItemDtoMapper.itemToDto(updItem);
    }

    @Override
    public Collection<ItemDto> find(final String text) {
        Predicate<Item> consumer = (Item item) -> {
            boolean containsName = item.getName().equalsIgnoreCase(text);
            boolean containsDescription = item.getDescription().equalsIgnoreCase(text);
            return containsName || containsDescription;
        };
        return itemStorage.findAll()
                .stream()
                .filter(Item::getAvailable)
                .filter(consumer)
                .map(ItemDtoMapper::itemToDto)
                .toList();
    }

    @Override
    public ItemDto find(Long id) {
        final Item item = itemStorage.get(id).orElseThrow(() -> ITEM_NOT_FOUND);
        return ItemDtoMapper.itemToDto(item);
    }

    @Override
    public Collection<ItemDto> findAllUserItems(Long ownerId) {
        User owner = userStorage.get(ownerId).orElseThrow(() -> UserServiceImpl.USER_NOT_FOUND);
        return owner.getItems().stream()
                .map(itemStorage::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ItemDtoMapper::itemToDto)
                .toList();
    }
}