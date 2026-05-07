package ch.oschwald.elias.fitness_tracker.controller;

import ch.oschwald.elias.fitness_tracker.dto.WorkoutRequest;
import ch.oschwald.elias.fitness_tracker.dto.WorkoutResponse;
import ch.oschwald.elias.fitness_tracker.entity.Workout;
import ch.oschwald.elias.fitness_tracker.mapper.WorkoutMapper;
import ch.oschwald.elias.fitness_tracker.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public List<WorkoutResponse> getAllWorkouts() {
        return workoutService.getAllWorkouts()
                .stream()
                .map(WorkoutMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public WorkoutResponse getWorkoutById(@PathVariable Long id) {
        return WorkoutMapper.toResponse(workoutService.getWorkoutById(id));
    }

    @PostMapping
    public WorkoutResponse createWorkout(@Valid @RequestBody WorkoutRequest request) {
        Workout workout = WorkoutMapper.toEntity(request);
        Workout savedWorkout = workoutService.createWorkout(request.getUserId(), workout);
        return WorkoutMapper.toResponse(savedWorkout);
    }

    @PutMapping("/{id}")
    public WorkoutResponse updateWorkout(@PathVariable Long id,
                                         @Valid @RequestBody WorkoutRequest request) {
        Workout workout = WorkoutMapper.toEntity(request);
        Workout updatedWorkout = workoutService.updateWorkout(id, workout);
        return WorkoutMapper.toResponse(updatedWorkout);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
    }
}