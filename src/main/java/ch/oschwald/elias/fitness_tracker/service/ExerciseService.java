package ch.oschwald.elias.fitness_tracker.service;

import ch.oschwald.elias.fitness_tracker.entity.Exercise;
import ch.oschwald.elias.fitness_tracker.entity.Workout;
import ch.oschwald.elias.fitness_tracker.exception.NotFoundException;
import ch.oschwald.elias.fitness_tracker.repository.ExerciseRepository;
import ch.oschwald.elias.fitness_tracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;

    public ExerciseService(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exercise mit ID " + id + " wurde nicht gefunden"));
    }

    @Transactional
    public Exercise createExercise(Long workoutId, Exercise exercise) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new NotFoundException("Workout mit ID " + workoutId + " wurde nicht gefunden"));

        exercise.setWorkout(workout);
        Exercise savedExercise = exerciseRepository.save(exercise);

        workout.getExercises().add(savedExercise);
        workoutRepository.save(workout);

        return savedExercise;
    }

    @Transactional
    public Exercise updateExercise(Long id, Exercise updatedExercise) {
        Exercise existingExercise = getExerciseById(id);

        existingExercise.setName(updatedExercise.getName());
        existingExercise.setRepetitions(updatedExercise.getRepetitions());
        existingExercise.setDurationInMinutes(updatedExercise.getDurationInMinutes());
        existingExercise.setWeight(updatedExercise.getWeight());

        return exerciseRepository.save(existingExercise);
    }

    @Transactional
    public void deleteExercise(Long id) {
        Exercise exercise = getExerciseById(id);

        Workout workout = exercise.getWorkout();
        if (workout != null) {
            workout.getExercises().remove(exercise);
        }

        exerciseRepository.delete(exercise);
    }
}