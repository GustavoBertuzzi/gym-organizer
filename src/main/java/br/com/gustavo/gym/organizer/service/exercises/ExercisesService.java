package br.com.gustavo.gym.organizer.service.exercises;

import br.com.gustavo.gym.organizer.dto.exercisesDTO.ExercisesAddDTO;
import br.com.gustavo.gym.organizer.exception.ExerciseAlreadyInUse;
import br.com.gustavo.gym.organizer.exception.ExerciseNotFoundException;
import br.com.gustavo.gym.organizer.exception.UserNotFoundException;
import br.com.gustavo.gym.organizer.model.ExercisesModel;
import br.com.gustavo.gym.organizer.model.UsersModel;
import br.com.gustavo.gym.organizer.repository.ExercisesRepository;
import br.com.gustavo.gym.organizer.repository.UsersRepository;
import br.com.gustavo.gym.organizer.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExercisesService {

    @Autowired
    private ExercisesRepository exercisesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    public ExercisesModel AddExercise (ExercisesAddDTO exercisesAddDTO, String email){
        UsersModel user = usersService.loadUserEntityByEmail(email);

        boolean exists = exercisesRepository.existsByUserAndNameAndDescriptionAndEquipmentAndMuscleGroup(
                user,
                exercisesAddDTO.name(),
                exercisesAddDTO.description(),
                exercisesAddDTO.equipment(),
                exercisesAddDTO.muscleGroup()
        );

        if (exists) {
            throw new ExerciseAlreadyInUse("Exercício já cadastrado com exatamente os mesmos dados.");
        }

        ExercisesModel exercise = new ExercisesModel();
                exercise.setName(exercisesAddDTO.name());
                exercise.setDescription(exercisesAddDTO.description());
                exercise.setEquipment(exercisesAddDTO.equipment());
                exercise.setMuscleGroup(exercisesAddDTO.muscleGroup());
                exercise.setUser(user);
        return exercisesRepository.save(exercise);
    }

    public List<ExercisesModel> showExercises(String email){
        UsersModel user = usersService.loadUserEntityByEmail(email);

        return exercisesRepository.findByUser(user);
    }

    public ExercisesModel updateExercises(String email, Long exerciseId, ExercisesAddDTO dto) {
        UsersModel user = usersService.loadUserEntityByEmail(email);

        ExercisesModel exercise = exercisesRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercício não encontrado: " + exerciseId));

        if (!exercise.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para atualizar este exercício.");
        }

        if (!exercise.getExerciseId().equals(exerciseId)) {
            throw new ExerciseNotFoundException("Exercício não encontrado: " + exerciseId);
        }

        if (dto.name() != null && !dto.name().isBlank()) {
            exercise.setName(dto.name());
        }

        if (dto.muscleGroup() != null && !dto.muscleGroup().isBlank()) {
            exercise.setMuscleGroup(dto.muscleGroup());
        }

        if (dto.equipment() != null && !dto.equipment().isBlank()) {
            exercise.setEquipment(dto.equipment());
        }

        if (dto.description() != null && !dto.description().isBlank()) {
            exercise.setDescription(dto.description());
        }

        return exercisesRepository.save(exercise);
    }

    public void deleteExercises (String email, Long exerciseId){
        UsersModel user = usersService.loadUserEntityByEmail(email);

        ExercisesModel exercise = exercisesRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercício não encontrado: " + exerciseId));

        if (!exercise.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Você não tem permissão para deletar este exercício.");
        }

        exercisesRepository.deleteById(exerciseId);

    }

}
