package ch.oschwald.elias.fitness_tracker.controller;

import ch.oschwald.elias.fitness_tracker.dto.ExerciseRequest;
import ch.oschwald.elias.fitness_tracker.dto.ExerciseResponse;
import ch.oschwald.elias.fitness_tracker.dto.WorkoutResponse;
import ch.oschwald.elias.fitness_tracker.entity.Exercise;
import ch.oschwald.elias.fitness_tracker.mapper.ExerciseMapper;
import ch.oschwald.elias.fitness_tracker.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutExerciseController {

    private final WorkoutService workoutService;

    public WorkoutExerciseController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/{workoutId}/exercises")
    public List<ExerciseResponse> getExercisesByWorkoutId(
            @PathVariable Long workoutId
    ) {

        WorkoutResponse workout =
                workoutService.getWorkoutById(workoutId);

        return workout.getExercises();
    }

    @PreAuthorize("hasRole('UPDATE')")
    @PostMapping("/{workoutId}/exercises")
    public ExerciseResponse addExerciseToWorkout(
            @PathVariable Long workoutId,
            @Valid @RequestBody ExerciseRequest request
    ) {

        Exercise exercise =
                ExerciseMapper.toEntity(request);

        Exercise savedExercise =
                workoutService.addExerciseToWorkout(
                        workoutId,
                        exercise
                );

        return ExerciseMapper.toResponse(savedExercise);
    }

    @PreAuthorize("hasRole('UPDATE')")
    @DeleteMapping("/{workoutId}/exercises/{exerciseId}")
    public void removeExerciseFromWorkout(
            @PathVariable Long workoutId,
            @PathVariable Long exerciseId
    ) {

        workoutService.removeExerciseFromWorkout(
                workoutId,
                exerciseId
        );
    }
}