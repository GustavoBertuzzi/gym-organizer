package br.com.gustavo.gym.organizer.service.exercises;

import br.com.gustavo.gym.organizer.dto.exercisesDTO.WorkoutExercisesDTO;
import br.com.gustavo.gym.organizer.exception.ExerciseNotFoundException;
import br.com.gustavo.gym.organizer.exception.UserNotFoundException;
import br.com.gustavo.gym.organizer.exception.WorkoutSessionNotFoundException;
import br.com.gustavo.gym.organizer.model.ExercisesModel;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.model.WorkoutExerciseModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import br.com.gustavo.gym.organizer.repository.ExercisesRepository;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import br.com.gustavo.gym.organizer.repository.WorkoutExercisesRepository;
import br.com.gustavo.gym.organizer.repository.WorkoutSessionsRepository;
import br.com.gustavo.gym.organizer.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExercisesService {

    @Autowired
    private WorkoutExercisesRepository workoutExercisesRepository;

    @Autowired
    private WorkoutSessionsRepository workoutSessionsRepository;

    @Autowired
    private ExercisesRepository exercisesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    public WorkoutExerciseModel addWorkoutExercise(WorkoutExercisesDTO dto, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        WorkoutSessionsModel session = workoutSessionsRepository.findById(dto.sessionId())
                .orElseThrow(() -> new WorkoutSessionNotFoundException("Sessão não encontrada: " + dto.sessionId()));

        if (!session.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para adicionar exercícios nesta sessão.");
        }

        ExercisesModel exercise = exercisesRepository.findById(dto.exerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException("Exercício não encontrado: " + dto.exerciseId()));

        WorkoutExerciseModel workoutExercise = new WorkoutExerciseModel();
        workoutExercise.setSession(session);
        workoutExercise.setExercise(exercise);
        workoutExercise.setWeight(dto.weight());
        workoutExercise.setRepetitions(dto.repetitions());
        workoutExercise.setSets(dto.sets());
        workoutExercise.setBreakTime(dto.breakTime());

        return workoutExercisesRepository.save(workoutExercise);
    }

    public List<WorkoutExerciseModel> getWorkoutExercises(Long sessionId, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        WorkoutSessionsModel session = workoutSessionsRepository.findById(sessionId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException("Sessão não encontrada: " + sessionId));

        if (!session.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para visualizar esta sessão.");
        }

        return workoutExercisesRepository.findBySession(session);
    }

    public WorkoutExerciseModel updateWorkoutExercise(Long workoutExerciseId, WorkoutExercisesDTO dto, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        WorkoutExerciseModel workoutExercise = workoutExercisesRepository.findById(workoutExerciseId)
                .orElseThrow(() -> new RuntimeException("Exercício da sessão não encontrado: " + workoutExerciseId));

        if (!workoutExercise.getSession().getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para atualizar este exercício.");
        }

        if (dto.weight() != null) workoutExercise.setWeight(dto.weight());
        if (dto.repetitions() > 0) workoutExercise.setRepetitions(dto.repetitions());
        if (dto.sets() > 0) workoutExercise.setSets(dto.sets());
        if (dto.breakTime() != null) workoutExercise.setBreakTime(dto.breakTime());

        return workoutExercisesRepository.save(workoutExercise);
    }

    public void deleteWorkoutExercise(Long workoutExerciseId, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        WorkoutExerciseModel workoutExercise = workoutExercisesRepository.findById(workoutExerciseId)
                .orElseThrow(() -> new RuntimeException("Exercício da sessão não encontrado: " + workoutExerciseId));

        if (!workoutExercise.getSession().getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para deletar este exercício.");
        }

        workoutExercisesRepository.delete(workoutExercise);
    }

    public WorkoutSessionsModel getWorkoutSessionByIdAndUser(Long sessionId, String email) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        WorkoutSessionsModel session = workoutExercisesRepository
                .findBySessionIdAndUser(sessionId, user)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada para este usuário"));

        return session;
    }
}
