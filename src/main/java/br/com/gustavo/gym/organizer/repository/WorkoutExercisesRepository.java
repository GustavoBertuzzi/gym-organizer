package br.com.gustavo.gym.organizer.repository;

import br.com.gustavo.gym.organizer.model.WorkoutExerciseModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutExercisesRepository extends JpaRepository<WorkoutExerciseModel, Long> {
    List<WorkoutExerciseModel> findBySession(WorkoutSessionsModel session);
}
