package br.com.gustavo.gym.organizer.repository;

import br.com.gustavo.gym.organizer.model.ExercisesModel;
import br.com.gustavo.gym.organizer.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExercisesRepository extends JpaRepository<ExercisesModel, Long> {
    List<ExercisesModel> findByUser(UsersModel user);
    boolean existsByUserAndNameAndDescriptionAndEquipmentAndMuscleGroup(UsersModel user, String name, String description, String equipment, String muscleGroup);
}
