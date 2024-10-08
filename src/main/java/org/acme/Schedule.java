package org.acme;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@PlanningSolution
public class Schedule {

    @PlanningEntityCollectionProperty
    List<Appointment> appointments;
    @ValueRangeProvider
    List<LocalTime> startTimes;

    @ValueRangeProvider
    List<LocalDate> dates;

    @PlanningScore
    HardSoftScore score;

    public Schedule() {
    }

    public Schedule(List<Appointment> appointments, List<LocalTime> startTimes,List<LocalDate> dates) {
        this.appointments = appointments;
        this.startTimes = startTimes;
        this.dates = dates;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<LocalTime> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(List<LocalTime> startTimes) {
        this.startTimes = startTimes;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(startTimes, schedule.startTimes) && Objects.equals(dates, schedule.dates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTimes, dates);
    }
}

