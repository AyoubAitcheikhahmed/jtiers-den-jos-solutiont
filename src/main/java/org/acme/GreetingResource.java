package org.acme;

import ai.timefold.solver.core.api.solver.SolverManager;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/hello")
public class GreetingResource {

    @Inject
    SolverManager<Schedule,String> solverManager;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Schedule hello() throws ExecutionException, InterruptedException {
        var problem = new Schedule(
          List.of(
                  new Appointment("Ayoub AIT CHEIKH AHMED", Duration.ofMinutes(30)),
                  new Appointment("Lionel Messi",Duration.ofMinutes(30)),
                  new Appointment("Jhon van Dijk",Duration.ofMinutes(30)),
                  new Appointment("Paulo Naveri",Duration.ofMinutes(30)),
                  new Appointment("Big Poapa",Duration.ofMinutes(30)),
                  new Appointment("Sting Van de Sting",Duration.ofMinutes(30)),
                  new Appointment("Ronaldo Junior",Duration.ofMinutes(30))),
                List.of(
                        LocalTime.of(15,0),
                        LocalTime.of(15,0),
                        LocalTime.of(15,0),
                        LocalTime.of(13,0),
                        LocalTime.of(11,0),
                        LocalTime.of(16,0),
                        LocalTime.of(14,0)
                ),
                List.of(
                        LocalDate.of(2024,9,23),
                        LocalDate.of(2024,9,24),
                        LocalDate.of(2024,9,24),
                        LocalDate.of(2024,9,26),
                        LocalDate.of(2024,9,26),
                        LocalDate.of(2024,9,26),
                        LocalDate.of(2024,9,28)
                )
        );



        Schedule solution = solverManager.solve("Job 1", problem).getFinalBestSolution();

        solution.getAppointments().sort((a1, a2) -> {
            int dateCompare = a1.getDate().compareTo(a2.getDate());
            if (dateCompare == 0) {
                return a1.getStartTime().compareTo(a2.getStartTime());
            } else {
                return dateCompare;
            }
        });
        return solution;
    }
}
