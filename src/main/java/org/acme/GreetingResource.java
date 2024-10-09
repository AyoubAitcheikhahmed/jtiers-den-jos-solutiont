package org.acme;

import ai.timefold.solver.core.api.solver.SolverManager;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/hello")
public class GreetingResource {

    @Inject
    SolverManager<Schedule, String> solverManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Schedule hello() throws ExecutionException, InterruptedException {
        var appointments = List.of(
                new Appointment("A", LocalDateTime.of(2024, 10, 8, 9, 0)),
                new Appointment("B", LocalDateTime.of(2024, 10, 8, 9, 0)),
                new Appointment("C", LocalDateTime.of(2024, 10, 8, 9, 0)),
                new Appointment("D", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("E", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("F", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("G", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("H", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("I", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("J", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("K", LocalDateTime.of(2024, 10, 8, 9, 0)),
                new Appointment("L", LocalDateTime.of(2024, 10, 8, 9, 0)),
                new Appointment("M", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("N", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("O", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("P", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("Q", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("R", LocalDateTime.of(2024, 10, 8, 13, 0)),
                new Appointment("S", LocalDateTime.of(2024, 10, 8, 13, 0))
        );

        var timeSlotRange = TimeSlotRangeFactory.create();
        var problem = new Schedule(appointments, timeSlotRange);

        return solverManager.solve("Appointments", problem).getFinalBestSolution();
    }
}
