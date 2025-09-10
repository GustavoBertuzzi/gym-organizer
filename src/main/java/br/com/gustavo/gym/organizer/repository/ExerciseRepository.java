package br.com.gustavo.gym.organizer.repository;

import br.com.gustavo.gym.organizer.model.ExercisesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExercisesModel, Long> {
    List<ExercisesModel> findBySession_SessionId(Long sessionId);

}
