package ch.oschwald.elias.fitness_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ExerciseRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @PositiveOrZero
    private Integer repetitions;

    @PositiveOrZero
    private Integer durationInMinutes;

    @PositiveOrZero
    private Double weight;

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
}