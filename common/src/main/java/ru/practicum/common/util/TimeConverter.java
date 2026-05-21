package ru.practicum.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeConverter {
    private static final ZoneId zoneId = ZoneId.systemDefault();

    public static LocalDateTime instantToLocal(Instant time) {
        return LocalDateTime.ofInstant(time, zoneId);
    }

    public static Instant localToInstant(LocalDateTime local) {
        return local.atZone(zoneId).toInstant();
    }
}