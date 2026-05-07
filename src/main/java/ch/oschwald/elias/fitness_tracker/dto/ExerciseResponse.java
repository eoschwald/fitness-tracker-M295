package ch.oschwald.elias.fitness_tracker.dto;

public class ExerciseResponse {

    private Long id;
    private String name;
    private Integer repetitions;
    private Integer durationInMinutes;
    private Double weight;
    private Long workoutId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public Double getWeight() {
        return weight;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }
}