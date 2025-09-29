package br.com.gustavo.gym.organizer.service.exercises;

import br.com.gustavo.gym.organizer.dto.exercisesDTO.WorkoutSessionsDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutSessionsGetResponseDTO;
import br.com.gustavo.gym.organizer.exception.UserNotFoundException;
import br.com.gustavo.gym.organizer.model.ExercisesModel;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import br.com.gustavo.gym.organizer.repository.WorkoutSessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkoutSessionsService {

    @Autowired
    private WorkoutSessionsRepository workoutSessionsRepository;

    @Autowired
    private UsersRepository usersRepository;

    // Criar sessão
    public WorkoutSessionsModel addSession(WorkoutSessionsDTO dto, String email) {
        UsersModel user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + email));

        WorkoutSessionsModel session = new WorkoutSessionsModel();
        session.setUser(user);
        session.setSessionDate(LocalDateTime.now()); // auto preenchido
        session.setSleepHours(dto.sleepHours());
        session.setPreWorkout(dto.preWorkout());
        session.setNotes(dto.notes());

        return workoutSessionsRepository.save(session);
    }

    // Listar todas as sessões do usuário
    public List<WorkoutSessionsGetResponseDTO> getAllSessions(String email) {
        UsersModel user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + email));

        List<WorkoutSessionsModel> sessions = workoutSessionsRepository.findByUser(user);

        return sessions.stream()
                .map(WorkoutSessionsGetResponseDTO::new)
                .toList();
    }

    // Buscar sessão por ID
    public WorkoutSessionsModel getSessionById(Long id, String email) {
        UsersModel user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + email));

        WorkoutSessionsModel session = workoutSessionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada: " + id));

        if (!session.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para acessar esta sessão");
        }

        return session;
    }

    // Atualizar sessão
    public WorkoutSessionsModel updateSession(Long id, WorkoutSessionsDTO dto, String email) {
        UsersModel user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + email));

        WorkoutSessionsModel session = workoutSessionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada: " + id));

        if (!session.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para atualizar esta sessão");
        }

        if (dto.sleepHours() != null) session.setSleepHours(dto.sleepHours());
        if (dto.preWorkout() != null) session.setPreWorkout(dto.preWorkout());
        if (dto.notes() != null) session.setNotes(dto.notes());

        return workoutSessionsRepository.save(session);
    }

    // Deletar sessão
    public void deleteSession(Long id, String email) {
        UsersModel user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado: " + email));

        WorkoutSessionsModel session = workoutSessionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada: " + id));

        if (!session.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para deletar esta sessão");
        }

        workoutSessionsRepository.delete(session);
    }
}
