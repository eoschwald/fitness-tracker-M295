package ch.oschwald.elias.fitness_tracker.controller;

import ch.oschwald.elias.fitness_tracker.dto.ExerciseRequest;
import ch.oschwald.elias.fitness_tracker.dto.ExerciseResponse;
import ch.oschwald.elias.fitness_tracker.entity.Exercise;
import ch.oschwald.elias.fitness_tracker.mapper.ExerciseMapper;
import ch.oschwald.elias.fitness_tracker.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/exercises")
    public List<ExerciseResponse> getAllExercises() {
        return exerciseService.getAllExercises()
                .stream()
                .map(ExerciseMapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/exercises/{id}")
    public ExerciseResponse getExerciseById(@PathVariable Long id) {
        return ExerciseMapper.toResponse(
                exerciseService.getExerciseById(id)
        );
    }

    @PreAuthorize("hasRole('UPDATE')")
    @PutMapping("/exercises/{id}")
    public ExerciseResponse updateExercise(@PathVariable Long id,
                                           @Valid @RequestBody ExerciseRequest request) {

        Exercise exercise = ExerciseMapper.toEntity(request);

        Exercise updatedExercise =
                exerciseService.updateExercise(id, exercise);

        return ExerciseMapper.toResponse(updatedExercise);
    }

    @PreAuthorize("hasRole('UPDATE')")
    @DeleteMapping("/exercises/{id}")
    public void deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
    }
}