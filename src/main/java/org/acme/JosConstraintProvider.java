package org.acme;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import static ai.timefold.solver.core.api.score.stream.Joiners.equal;
import static ai.timefold.solver.core.api.score.stream.Joiners.overlapping;
import static java.util.stream.Collectors.counting;

public class JosConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                preventOverlapInSameTimeSlotAndSameDay(constraintFactory),
    //                limitAppointmentsToMaxPerDay(constraintFactory),
                doNotScheduleOnWeekend(constraintFactory),
                //  "score": "0hard/0soft"
                preferEarlierAppointments(constraintFactory),
                //  "score": "-844hard/0soft"
//                preferEarlierTimesInTheDay(constraintFactory),

                //TODO: appointment priority
                /*
                 , app2 , app3

                after scheduling
                app3 , app1 , app2 😭
                app1 , app2 , app3 ❤️


                 */
        };
    }

    // ************************************************************************
    // Hard constraints
    // ************************************************************************

    //testing time only constraint
    private Constraint preventOverlapInSameTimeSlot(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Appointment.class,
                        overlapping(Appointment::getStartTime, Appointment::getEndTime))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("No overlapping in the same time slot");
    }
    //testing day only constraint
    private Constraint preventOverlapOnSameDay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Appointment.class)
                .filter((appointment1, appointment2) -> appointment1.getDate().equals(appointment2.getDate()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("No overlapping on the same day");
    }

    // DAY-TIME Overlap constraint
    private Constraint preventOverlapInSameTimeSlotAndSameDay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Appointment.class,
                        equal(Appointment::getDate),
                        overlapping(Appointment::getStartTime, Appointment::getEndTime))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("No overlapping in the same time slot and same day");
    }

    // DEN JOS, HEEL LUI - mag maar 2 afspraken per dag afwerken. TOF HÉ 😁
    private Constraint limitAppointmentsToMaxPerDay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                .groupBy(Appointment::getDate, ConstraintCollectors.count())
                .filter((date, count) -> count < 2)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Limit appointments to a max number of appointments per day");
    }

    //DEN JOS ZEGT: JA T'S WEEKEND È 🍻
    private Constraint doNotScheduleOnWeekend(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                .filter(appointment -> appointment.getDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
                        appointment.getDate().getDayOfWeek() == DayOfWeek.SUNDAY)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("No work on weekends");
    }

    private Constraint preferEarlierAppointments(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                .filter( appointment -> appointment.getDate().getDayOfWeek() == DayOfWeek.MONDAY)
                .penalize(HardSoftScore.ONE_SOFT,
                        appointment -> appointment.getStartTime().getHour() - 9) // Penalize more   as the appointment start time gets later in the day
                .asConstraint("Prefer earlier appointments starting from 9:00 AM");
    }

    // ************************************************************************
    // Soft constraints
    // ************************************************************************


    // Soft constraint: Prefer earlier appointments in the day, starting at 9:00 AM
    private Constraint preferEarlierTimesInTheDay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                .penalize(HardSoftScore.ONE_HARD,
                        appointment -> {
                            // Calculate the difference between the start time of the appointment and 9:00 AM
                            long minutesDifference = Duration.between(LocalTime.of(9, 0), appointment.getStartTime()).toMinutes();

                            // If the appointment starts earlier than 9:00 AM (unlikely), no penalty
                            if (minutesDifference < 0) {
                                return 0;
                            }

                            return (int) minutesDifference;  // Penalize later start times
                        })
                .asConstraint("Prefer earlier times in the day");
    }







}
