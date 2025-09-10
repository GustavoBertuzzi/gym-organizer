package br.com.gustavo.gym.organizer.repository;

import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutSessionsRepository extends JpaRepository<WorkoutSessionsModel, Long> {
    List<WorkoutSessionsModel> findByUserId(Long userId);
}
