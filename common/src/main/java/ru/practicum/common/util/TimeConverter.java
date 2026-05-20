package ru.practicum.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeConverter {
    public static LocalDateTime instantToLocal(Instant time) {
        return LocalDateTime.ofInstant(time, ZoneId.of("Europe/Moscow"));
    }

    public static Instant localToInstant(LocalDateTime local) {
        return local.atZone(ZoneId.of("Europe/Moscow")).toInstant();
    }
}