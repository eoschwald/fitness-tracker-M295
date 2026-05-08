package ch.oschwald.elias.fitness_tracker.controller;

import ch.oschwald.elias.fitness_tracker.dto.WorkoutResponse;
import ch.oschwald.elias.fitness_tracker.entity.User;
import ch.oschwald.elias.fitness_tracker.entity.Workout;
import ch.oschwald.elias.fitness_tracker.service.WorkoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.oauth2.server.resource.autoconfigure.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = WorkoutController.class,
        excludeAutoConfiguration = OAuth2ResourceServerAutoConfiguration.class
)
class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkoutService workoutService;

    @Test
    void shouldReturnAllWorkouts() throws Exception {
        WorkoutResponse response = new WorkoutResponse();
        response.setId(1L);
        response.setTitle("Leg Day");
        response.setDescription("Heavy squat training");
        response.setWorkoutDate(LocalDate.now());

        when(workoutService.getAllWorkouts()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Leg Day"));
    }

    @Test
    void shouldCreateWorkout() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Workout savedWorkout = new Workout();
        savedWorkout.setId(1L);
        savedWorkout.setTitle("Push Day");
        savedWorkout.setDescription("Chest workout");
        savedWorkout.setWorkoutDate(LocalDate.parse("2026-05-08"));
        savedWorkout.setUser(user);

        when(workoutService.createWorkout(anyLong(), any(Workout.class))).thenReturn(savedWorkout);

        String requestBody = """
                {
                  "title": "Push Day",
                  "description": "Chest workout",
                  "workoutDate": "2026-05-08",
                  "userId": 1
                }
                """;

        mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Push Day"));
    }
}