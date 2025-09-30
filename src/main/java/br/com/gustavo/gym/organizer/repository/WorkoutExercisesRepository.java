package br.com.gustavo.gym.organizer.repository;

import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.model.WorkoutExerciseModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutExercisesRepository extends JpaRepository<WorkoutExerciseModel, Long> {
    List<WorkoutExerciseModel> findBySession(WorkoutSessionsModel session);
    List<WorkoutExerciseModel> findBySession_SessionIdAndSession_User(Long sessionId, UsersModel user);

}
