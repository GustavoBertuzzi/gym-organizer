package br.com.gustavo.gym.organizer.controller;

import br.com.gustavo.gym.organizer.dto.exercisesDTO.ExercisesAddDTO;
import br.com.gustavo.gym.organizer.model.ExercisesModel;
import br.com.gustavo.gym.organizer.service.exercises.ExercisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExercisesController {

    @Autowired
    private ExercisesService exercisesService;

    @PostMapping("/add")
    public ResponseEntity<?> addExercise (@RequestBody ExercisesAddDTO exercisesAddDTO, Authentication authentication){

        String email = authentication.getName();
        ExercisesModel exercise = exercisesService.AddExercise(exercisesAddDTO, email);
        return ResponseEntity.ok("Exercicio criado.");
    }

    @GetMapping("/get")
    public ResponseEntity<?> showExercise (Authentication authentication){
        String email = authentication.getName();
        List<ExercisesModel> exercise = exercisesService.showExercises(email);
        return ResponseEntity.ok(exercise);
    }

    @PutMapping("/update/{exerciseId}")
    public ResponseEntity<?> updateExercise(
            @PathVariable Long exerciseId,
            @RequestBody ExercisesAddDTO exercisesAddDTO,
            Authentication authentication) {

        String email = authentication.getName();
        ExercisesModel exercise = exercisesService.updateExercises(email, exerciseId, exercisesAddDTO);

        return ResponseEntity.ok(exercise);
    }

    @DeleteMapping("/delete/{exerciseId}")
    public ResponseEntity<?> deleteExercise (@PathVariable Long exerciseId, Authentication authentication){
        String email = authentication.getName();
        exercisesService.deleteExercises(email, exerciseId);
        return ResponseEntity.ok("Exercicio deletado com sucesso");
    }

}
