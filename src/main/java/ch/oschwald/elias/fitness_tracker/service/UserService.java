package ch.oschwald.elias.fitness_tracker.service;

import ch.oschwald.elias.fitness_tracker.entity.User;
import ch.oschwald.elias.fitness_tracker.entity.Workout;
import ch.oschwald.elias.fitness_tracker.exception.NotFoundException;
import ch.oschwald.elias.fitness_tracker.repository.UserRepository;
import ch.oschwald.elias.fitness_tracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;

    public UserService(UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User mit ID " + id + " wurde nicht gefunden"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setUsername(updatedUser.getUsername());
        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);

        List<Workout> workouts = workoutRepository.findAll().stream()
                .filter(workout -> workout.getUser() != null && workout.getUser().getId().equals(user.getId()))
                .toList();

        workouts.forEach(workoutRepository::delete);

        userRepository.delete(user);
    }
}