package org.acme;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@PlanningEntity
public class Appointment {

    @PlanningId
    private String appointment;
    private Duration duration;

    // New field to assign the day of the appointment
    @PlanningVariable
    private LocalDate date;

    @PlanningVariable
    private LocalTime startTime;

    public LocalTime getEndTime() {
        return startTime.plus(duration);
    }

    public Appointment(String appointment, Duration duration) {
        this.appointment = appointment;
        this.duration = duration;
    }

    public Appointment() {}

    // Getters and setters
    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }
}
