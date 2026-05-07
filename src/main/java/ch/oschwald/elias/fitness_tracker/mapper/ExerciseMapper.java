package ch.oschwald.elias.fitness_tracker.mapper;

import ch.oschwald.elias.fitness_tracker.dto.ExerciseRequest;
import ch.oschwald.elias.fitness_tracker.dto.ExerciseResponse;
import ch.oschwald.elias.fitness_tracker.entity.Exercise;

public class ExerciseMapper {

    private ExerciseMapper() {
    }

    public static Exercise toEntity(ExerciseRequest request) {
        Exercise exercise = new Exercise();
        exercise.setName(request.getName());
        exercise.setRepetitions(request.getRepetitions());
        exercise.setDurationInMinutes(request.getDurationInMinutes());
        exercise.setWeight(request.getWeight());
        return exercise;
    }

    public static ExerciseResponse toResponse(Exercise exercise) {
        ExerciseResponse response = new ExerciseResponse();
        response.setId(exercise.getId());
        response.setName(exercise.getName());
        response.setRepetitions(exercise.getRepetitions());
        response.setDurationInMinutes(exercise.getDurationInMinutes());
        response.setWeight(exercise.getWeight());

        if (exercise.getWorkout() != null) {
            response.setWorkoutId(exercise.getWorkout().getId());
        }

        return response;
    }
}