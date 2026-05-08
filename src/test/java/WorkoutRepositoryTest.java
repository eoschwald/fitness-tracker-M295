package ch.oschwald.elias.fitness_tracker.repository;

import ch.oschwald.elias.fitness_tracker.entity.User;
import ch.oschwald.elias.fitness_tracker.entity.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveWorkout() {
        User user = new User();
        user.setUsername("testuser");

        User savedUser = userRepository.save(user);

        Workout workout = new Workout();
        workout.setTitle("Leg Day");
        workout.setDescription("Heavy squats");
        workout.setWorkoutDate(LocalDate.now());
        workout.setUser(savedUser);

        Workout savedWorkout = workoutRepository.save(workout);

        assertThat(savedWorkout.getId()).isNotNull();
        assertThat(savedWorkout.getTitle()).isEqualTo("Leg Day");
    }

    @Test
    void shouldFindWorkoutById() {
        User user = new User();
        user.setUsername("finduser");

        User savedUser = userRepository.save(user);

        Workout workout = new Workout();
        workout.setTitle("Push Day");
        workout.setDescription("Chest training");
        workout.setWorkoutDate(LocalDate.now());
        workout.setUser(savedUser);

        Workout savedWorkout = workoutRepository.save(workout);

        Workout foundWorkout = workoutRepository.findById(savedWorkout.getId()).orElse(null);

        assertThat(foundWorkout).isNotNull();
        assertThat(foundWorkout.getTitle()).isEqualTo("Push Day");
    }

    @Test
    void shouldDeleteWorkout() {
        User user = new User();
        user.setUsername("deleteuser");

        User savedUser = userRepository.save(user);

        Workout workout = new Workout();
        workout.setTitle("Delete Workout");
        workout.setDescription("To be deleted");
        workout.setWorkoutDate(LocalDate.now());
        workout.setUser(savedUser);

        Workout savedWorkout = workoutRepository.save(workout);

        workoutRepository.delete(savedWorkout);

        assertThat(workoutRepository.findById(savedWorkout.getId())).isEmpty();
    }
}