package ru.practicum.common.dto.item;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.common.exception.ValidationException;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ItemDto {
    Long id;
    String name;
    String description;
    Boolean available;
    Long requestId;

    public boolean hasRequestId() {
        return requestId != null;
    }

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