package br.com.gustavo.gym.organizer.repository;

import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutSessionsRepository extends JpaRepository<WorkoutSessionsModel, Long> {
    List<WorkoutSessionsModel> findByUser(UsersModel user);
    Optional<WorkoutSessionsModel> findBySessionIdAndUser(Long sessionId, UsersModel user);

}
