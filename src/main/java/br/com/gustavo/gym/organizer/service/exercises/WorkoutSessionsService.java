package br.com.gustavo.gym.organizer.service.exercises;

import br.com.gustavo.gym.organizer.dto.exercisesDTO.WorkoutSessionsDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutSessionsResponseDTO;
import br.com.gustavo.gym.organizer.mapper.Mapper;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import br.com.gustavo.gym.organizer.repository.WorkoutSessionsRepository;
import br.com.gustavo.gym.organizer.service.UsersService;
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

    @Autowired
    private UsersService usersService;

    public WorkoutSessionsModel addSession(WorkoutSessionsDTO dto, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        WorkoutSessionsModel session = new WorkoutSessionsModel();
        session.setUser(user);
        session.setSessionDate(LocalDateTime.now());
        session.setSleepHours(dto.sleepHours());
        session.setPreWorkout(dto.preWorkout());
        session.setNotes(dto.notes());

        return workoutSessionsRepository.save(session);
    }

    public List<WorkoutSessionsResponseDTO> getAllSessions(String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        List<WorkoutSessionsModel> sessions = workoutSessionsRepository.findByUser(user);

        return sessions.stream()
                .map(Mapper::toResponse)
                .toList();
    }


    public WorkoutSessionsModel getSessionById(Long id, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        WorkoutSessionsModel session = workoutSessionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada: " + id));

        if (!session.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para acessar esta sessão");
        }

        return session;
    }

    public WorkoutSessionsModel updateSession(Long id, WorkoutSessionsDTO dto, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

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

    public void deleteSession(Long id, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        WorkoutSessionsModel session = workoutSessionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada: " + id));

        if (!session.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para deletar esta sessão");
        }

        workoutSessionsRepository.delete(session);
    }
}
