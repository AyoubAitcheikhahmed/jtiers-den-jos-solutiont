package org.acme;

import java.time.LocalTime;

public enum Workday {
    MONDAY(LocalTime.of(9, 0), LocalTime.of(16, 30)),
    TUESDAY(LocalTime.of(9, 0), LocalTime.of(16, 30)),
    WEDNESDAY(LocalTime.of(9, 0), LocalTime.of(16, 30)),
    THURSDAY(LocalTime.of(9, 0), LocalTime.of(16, 30)),
    FRIDAY(LocalTime.of(9, 0), LocalTime.of(16, 30)),
    SATURDAY(LocalTime.of(9, 0), LocalTime.of(11, 30));

    private final LocalTime startTime;
    private final LocalTime endTime;

    Workday(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
