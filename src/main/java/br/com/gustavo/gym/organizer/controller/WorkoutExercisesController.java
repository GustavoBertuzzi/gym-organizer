package br.com.gustavo.gym.organizer.controller;

import br.com.gustavo.gym.organizer.dto.exercisesDTO.WorkoutExercisesDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutExerciseResponseDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutSessionsResponseDTO;
import br.com.gustavo.gym.organizer.mapper.Mapper;
import br.com.gustavo.gym.organizer.model.WorkoutExerciseModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import br.com.gustavo.gym.organizer.service.exercises.WorkoutExercisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout-exercises")
public class WorkoutExercisesController {

    @Autowired
    private WorkoutExercisesService workoutExercisesService;

    @PostMapping("/add")
    public ResponseEntity<?> addWorkoutExercise(@RequestBody WorkoutExercisesDTO dto, Authentication authentication) {
        String email = authentication.getName();
        workoutExercisesService.addWorkoutExercise(dto, email);
        return ResponseEntity.ok("Exercício adicionado à sessão com sucesso.");
    }

    @GetMapping("/get/{sessionId}")
    public ResponseEntity<?> getWorkoutExercises(@PathVariable Long sessionId, Authentication authentication) {
        String email = authentication.getName();
        WorkoutExerciseModel session = workoutExercisesService.getWorkoutSessionByIdAndUser(sessionId, email);

        WorkoutExerciseResponseDTO responseDTO = Mapper.toResponse(session);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/update/{workoutExerciseId}")
    public ResponseEntity<?> updateWorkoutExercise(
            @PathVariable Long workoutExerciseId,
            @RequestBody WorkoutExercisesDTO dto,
            Authentication authentication) {

        String email = authentication.getName();
        WorkoutExerciseModel updated = workoutExercisesService.updateWorkoutExercise(workoutExerciseId, dto, email);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{workoutExerciseId}")
    public ResponseEntity<?> deleteWorkoutExercise(@PathVariable Long workoutExerciseId, Authentication authentication) {
        String email = authentication.getName();
        workoutExercisesService.deleteWorkoutExercise(workoutExerciseId, email);
        return ResponseEntity.ok("Exercício da sessão deletado com sucesso.");
    }
}
