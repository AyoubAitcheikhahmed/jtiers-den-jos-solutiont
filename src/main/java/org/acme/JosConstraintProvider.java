package org.acme;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.DayOfWeek.MONDAY;

public class JosConstraintProvider implements ConstraintProvider {
    private final LocalDateTime MONDAY_9_AM = LocalDateTime.now().with(MONDAY).withHour(9).withMinute(0).withSecond(0);
    private final HardSoftScore PENALTY_HARD = HardSoftScore.ofHard(100);
    private final HardSoftScore PENALTY_SOFT = HardSoftScore.ofSoft(10);
    private final String PREVENT_OVERLAP_IN_TIMESLOTS = "appointmentTimeAndDateDoNotOverlap";
    private final String START_ASAP = "appointmentStartsAsEarlyAsPossible";

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[]{
                startAsSoonAsPossible(factory),
                preventOverlapInTimeslots(factory),
        };
    }

    private Constraint startAsSoonAsPossible(ConstraintFactory factory) {
        return factory.forEach(Appointment.class)
                .penalize(PENALTY_SOFT, this::calculateMinutesPassedSinceMonday9Am)
                .asConstraint(START_ASAP);
    }

    private Constraint preventOverlapInTimeslots(ConstraintFactory factory) {
        return factory.forEachUniquePair(Appointment.class)
                .filter(this::isOnSameDay)
                .filter(this::hasOverlappingTimeslot)
                .penalize(PENALTY_HARD, this::calculateOverlappingTimeInMinutes)
                .asConstraint(PREVENT_OVERLAP_IN_TIMESLOTS);
    }

    private int calculateMinutesPassedSinceMonday9Am(Appointment appointment) {
        return (int) Duration.between(MONDAY_9_AM, appointment.getTimeSlot()).toMinutes();
    }

    private int calculateOverlappingTimeInMinutes(Appointment existing, Appointment projected) {
        LocalTime start = existing.getStartTime().isAfter(projected.getStartTime()) ? existing.getStartTime() : projected.getStartTime();
        LocalTime end = existing.getEndTime().isBefore(projected.getEndTime()) ? existing.getEndTime() : projected.getEndTime();

        return (int) Duration.between(start, end).toMinutes();
    }

    private boolean hasOverlappingTimeslot(Appointment existing, Appointment projected) {
        return (projected.getEndTime().isAfter(existing.getStartTime()) &&
                projected.getStartTime().isBefore(existing.getEndTime())) ||
                (existing.getEndTime().isAfter(projected.getStartTime()) &&
                        existing.getStartTime().isBefore(projected.getEndTime()));
    }

    private boolean isOnSameDay(Appointment scheduled, Appointment projected) {
        return scheduled.getTimeSlot().toLocalDate().equals(projected.getTimeSlot().toLocalDate());
    }
}