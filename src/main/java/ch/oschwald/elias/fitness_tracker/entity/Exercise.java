package ch.oschwald.elias.fitness_tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name darf nicht leer sein")
    @Size(max = 150, message = "Name darf maximal 150 Zeichen haben")
    @Column(nullable = false, length = 150)
    private String name;

    @PositiveOrZero(message = "Repetitions müssen >= 0 sein")
    private Integer repetitions;

    @PositiveOrZero(message = "DurationInMinutes müssen >= 0 sein")
    private Integer durationInMinutes;

    @PositiveOrZero(message = "Weight muss >= 0 sein")
    private Double weight;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    public Exercise() {
    }

    public Exercise(String name, Integer repetitions, Integer durationInMinutes, Double weight, Workout workout) {
        this.name = name;
        this.repetitions = repetitions;
        this.durationInMinutes = durationInMinutes;
        this.weight = weight;
        this.workout = workout;
    }

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

    public Workout getWorkout() {
        return workout;
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

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}