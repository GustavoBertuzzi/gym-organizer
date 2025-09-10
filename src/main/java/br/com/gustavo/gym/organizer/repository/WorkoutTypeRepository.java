package br.com.gustavo.gym.organizer.repository;

import br.com.gustavo.gym.organizer.model.WorkoutTypesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutTypeRepository extends JpaRepository<WorkoutTypesModel, Long> {
    Optional<WorkoutTypesModel> findByWorkoutTypeName(String name);
}
