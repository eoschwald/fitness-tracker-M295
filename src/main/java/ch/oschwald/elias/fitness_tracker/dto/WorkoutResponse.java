package ch.oschwald.elias.fitness_tracker.dto;

import java.time.LocalDate;
import java.util.List;

public class WorkoutResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate workoutDate;
    private Long userId;
    private List<ExerciseResponse> exercises;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public Long getUserId() {
        return userId;
    }

    public List<ExerciseResponse> getExercises() {
        return exercises;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setExercises(List<ExerciseResponse> exercises) {
        this.exercises = exercises;
    }
}