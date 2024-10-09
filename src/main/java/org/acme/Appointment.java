package org.acme;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@PlanningEntity
public class Appointment {

    @PlanningId
    private String name;

    @PlanningVariable(valueRangeProviderRefs = {"timeSlotRange"})
    private LocalDateTime timeSlot;

    public Appointment() {
    }

    public Appointment(String name, LocalDateTime timeSlot) {
        this.name = name;
        this.timeSlot = timeSlot;
    }

    public LocalTime getEndTime() {
        return this.timeSlot.plus(Duration.ofMinutes(30)).toLocalTime();
    }

    public LocalTime getStartTime() {
        return this.timeSlot.toLocalTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalDateTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getTimeSlot(), that.getTimeSlot());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTimeSlot());
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "name='" + name + '\'' +
                ", timeSlot=" + timeSlot +
                '}';
    }
}
