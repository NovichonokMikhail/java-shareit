package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.exception.ValidationException;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
public class ItemDto {
    Long id;
    String name;
    String description;
    Boolean available;

    public boolean hasName() {
        if (name == null)
            return false;
        if (name.isBlank())
            throw new ValidationException("Name cannot be blank");
        return true;
    }

    public boolean hasDescription() {
        if (description == null)
            return false;
        if (description.isBlank())
            throw new ValidationException("Description cannot be blank");
        return true;
    }

    public boolean hasAvailable() {
        return available != null;
    }
}