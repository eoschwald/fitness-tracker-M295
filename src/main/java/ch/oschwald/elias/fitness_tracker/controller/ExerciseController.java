package ch.oschwald.elias.fitness_tracker.controller;

import ch.oschwald.elias.fitness_tracker.dto.ExerciseRequest;
import ch.oschwald.elias.fitness_tracker.dto.ExerciseResponse;
import ch.oschwald.elias.fitness_tracker.entity.Exercise;
import ch.oschwald.elias.fitness_tracker.mapper.ExerciseMapper;
import ch.oschwald.elias.fitness_tracker.service.ExerciseService;
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
@RequestMapping("/api")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/exercises")
    public List<ExerciseResponse> getAllExercises() {
        return exerciseService.getAllExercises()
                .stream()
                .map(ExerciseMapper::toResponse)
                .toList();
    }

    @GetMapping("/exercises/{id}")
    public ExerciseResponse getExerciseById(@PathVariable Long id) {
        return ExerciseMapper.toResponse(exerciseService.getExerciseById(id));
    }

    @PostMapping("/workouts/{workoutId}/exercises")
    public ExerciseResponse createExercise(@PathVariable Long workoutId,
                                           @Valid @RequestBody ExerciseRequest request) {
        Exercise exercise = ExerciseMapper.toEntity(request);
        Exercise savedExercise = exerciseService.createExercise(workoutId, exercise);
        return ExerciseMapper.toResponse(savedExercise);
    }

    @PutMapping("/exercises/{id}")
    public ExerciseResponse updateExercise(@PathVariable Long id,
                                           @Valid @RequestBody ExerciseRequest request) {
        Exercise exercise = ExerciseMapper.toEntity(request);
        Exercise updatedExercise = exerciseService.updateExercise(id, exercise);
        return ExerciseMapper.toResponse(updatedExercise);
    }

    @DeleteMapping("/exercises/{id}")
    public void deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
    }
}