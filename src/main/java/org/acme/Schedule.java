package org.acme;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

import java.time.LocalDateTime;
import java.util.List;


@PlanningSolution
public class Schedule {

    @PlanningEntityCollectionProperty
    private List<Appointment> appointments;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "timeSlotRange")
    private List<LocalDateTime> timeSlotRange;

    @PlanningScore
    private HardSoftScore score;

    public Schedule() {
    }

    public Schedule(List<Appointment> appointments, List<LocalDateTime> timeSlotRange) {
        this.appointments = appointments;
        this.timeSlotRange = timeSlotRange;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<LocalDateTime> getTimeSlotRange() {
        return timeSlotRange;
    }

    public void setTimeSlotRange(List<LocalDateTime> timeSlotRange) {
        this.timeSlotRange = timeSlotRange;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}