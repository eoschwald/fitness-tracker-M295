package ch.oschwald.elias.fitness_tracker.service;

import ch.oschwald.elias.fitness_tracker.dto.WorkoutResponse;
import ch.oschwald.elias.fitness_tracker.entity.Exercise;
import ch.oschwald.elias.fitness_tracker.entity.User;
import ch.oschwald.elias.fitness_tracker.entity.Workout;
import ch.oschwald.elias.fitness_tracker.exception.NotFoundException;
import ch.oschwald.elias.fitness_tracker.mapper.WorkoutMapper;
import ch.oschwald.elias.fitness_tracker.repository.ExerciseRepository;
import ch.oschwald.elias.fitness_tracker.repository.UserRepository;
import ch.oschwald.elias.fitness_tracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutService(WorkoutRepository workoutRepository,
                          UserRepository userRepository,
                          ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponse> getAllWorkouts() {
        return workoutRepository.findAll()
                .stream()
                .map(WorkoutMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public WorkoutResponse getWorkoutById(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout mit ID " + id + " wurde nicht gefunden"));

        return WorkoutMapper.toResponse(workout);
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponse> getWorkoutsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User mit ID " + userId + " wurde nicht gefunden"));

        return workoutRepository.findAll().stream()
                .filter(workout -> workout.getUser() != null && workout.getUser().getId().equals(user.getId()))
                .map(WorkoutMapper::toResponse)
                .toList();
    }

    @Transactional
    public Workout createWorkout(Long userId, Workout workout) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User mit ID " + userId + " wurde nicht gefunden"));

        workout.setUser(user);
        return workoutRepository.save(workout);
    }

    @Transactional
    public WorkoutResponse updateWorkout(Long id, Workout updatedWorkout) {
        Workout existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout mit ID " + id + " wurde nicht gefunden"));

        existingWorkout.setTitle(updatedWorkout.getTitle());
        existingWorkout.setDescription(updatedWorkout.getDescription());
        existingWorkout.setWorkoutDate(updatedWorkout.getWorkoutDate());

        Workout savedWorkout = workoutRepository.save(existingWorkout);
        return WorkoutMapper.toResponse(savedWorkout);
    }

    @Transactional
    public void deleteWorkout(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout mit ID " + id + " wurde nicht gefunden"));
        workoutRepository.delete(workout);
    }

    @Transactional
    public Exercise addExerciseToWorkout(Long workoutId, Exercise exercise) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new NotFoundException("Workout mit ID " + workoutId + " wurde nicht gefunden"));

        exercise.setWorkout(workout);
        Exercise savedExercise = exerciseRepository.save(exercise);

        workout.getExercises().add(savedExercise);
        workoutRepository.save(workout);

        return savedExercise;
    }

    @Transactional
    public void removeExerciseFromWorkout(Long workoutId, Long exerciseId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new NotFoundException("Workout mit ID " + workoutId + " wurde nicht gefunden"));

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NotFoundException("Exercise mit ID " + exerciseId + " wurde nicht gefunden"));

        if (exercise.getWorkout() == null || !exercise.getWorkout().getId().equals(workout.getId())) {
            throw new NotFoundException("Exercise gehört nicht zu diesem Workout");
        }

        workout.getExercises().remove(exercise);
        exerciseRepository.delete(exercise);
        workoutRepository.save(workout);
    }
}