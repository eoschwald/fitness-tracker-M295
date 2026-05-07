package ch.oschwald.elias.fitness_tracker.mapper;

import ch.oschwald.elias.fitness_tracker.dto.ExerciseResponse;
import ch.oschwald.elias.fitness_tracker.dto.WorkoutRequest;
import ch.oschwald.elias.fitness_tracker.dto.WorkoutResponse;
import ch.oschwald.elias.fitness_tracker.entity.Exercise;
import ch.oschwald.elias.fitness_tracker.entity.Workout;

import java.util.List;

public class WorkoutMapper {

    private WorkoutMapper() {
    }

    public static Workout toEntity(WorkoutRequest request) {
        Workout workout = new Workout();
        workout.setTitle(request.getTitle());
        workout.setDescription(request.getDescription());
        workout.setWorkoutDate(request.getWorkoutDate());
        return workout;
    }

    public static WorkoutResponse toResponse(Workout workout) {
        WorkoutResponse response = new WorkoutResponse();
        response.setId(workout.getId());
        response.setTitle(workout.getTitle());
        response.setDescription(workout.getDescription());
        response.setWorkoutDate(workout.getWorkoutDate());

        if (workout.getUser() != null) {
            response.setUserId(workout.getUser().getId());
        }

        if (workout.getExercises() != null) {
            response.setExercises(
                    workout.getExercises().stream()
                            .map(WorkoutMapper::mapExercise)
                            .toList()
            );
        } else {
            response.setExercises(List.of());
        }

        return response;
    }

    private static ExerciseResponse mapExercise(Exercise exercise) {
        ExerciseResponse response = new ExerciseResponse();
        response.setId(exercise.getId());
        response.setName(exercise.getName());
        response.setRepetitions(exercise.getRepetitions());
        response.setDurationInMinutes(exercise.getDurationInMinutes());
        response.setWeight(exercise.getWeight());
        return response;
    }
}