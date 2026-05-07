package ch.oschwald.elias.fitness_tracker.repository;

import ch.oschwald.elias.fitness_tracker.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}