package org.acme;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotRangeFactory {
    public static List<LocalDateTime> create() {
        List<LocalDateTime> timeSlots = new ArrayList<>();

        for (Workday workday : Workday.values()) {
            LocalDateTime dayStart = LocalDateTime.now()
                    .with(DayOfWeek.valueOf(workday.name()))
                    .withHour(workday.getStartTime().getHour())
                    .withMinute(workday.getStartTime().getMinute())
                    .withSecond(0)
                    .withNano(0);

            LocalDateTime dayEnd = LocalDateTime.of(
                    dayStart.getYear(),
                    dayStart.getMonth(),
                    dayStart.getDayOfMonth(),
                    workday.getEndTime().getHour(),
                    workday.getEndTime().getMinute()
            );

            while (!dayStart.isAfter(dayEnd)) {
                timeSlots.add(dayStart);
                dayStart = dayStart.plusMinutes(30);
            }
        }

        return timeSlots;
    }
}
