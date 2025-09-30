package br.com.gustavo.gym.organizer.controller;

import br.com.gustavo.gym.organizer.dto.exercisesDTO.WorkoutSessionsDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutSessionsGetResponseDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutSessionsResponseDTO;
import br.com.gustavo.gym.organizer.mapper.WorkoutSessionsMapper;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import br.com.gustavo.gym.organizer.service.exercises.WorkoutSessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout-sessions")
public class WorkoutSessionsController {

    @Autowired
    private WorkoutSessionsService workoutSessionsService;

    @PostMapping("/add")
    public ResponseEntity<?> addSession(@RequestBody WorkoutSessionsDTO dto, Authentication authentication) {
        String email = authentication.getName();
        WorkoutSessionsModel session = workoutSessionsService.addSession(dto, email);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/get")
    public ResponseEntity<List<WorkoutSessionsResponseDTO>> getAllSessions(Authentication authentication) {
        String email = authentication.getName();
        List<WorkoutSessionsResponseDTO> sessions = workoutSessionsService.getAllSessions(email);
        return ResponseEntity.ok(sessions);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        WorkoutSessionsModel session = workoutSessionsService.getSessionById(id, email);
        return ResponseEntity.ok(session);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSession(
            @PathVariable Long id,
            @RequestBody WorkoutSessionsDTO dto,
            Authentication authentication) {

        String email = authentication.getName();
        WorkoutSessionsModel session = workoutSessionsService.updateSession(id, dto, email);
        return ResponseEntity.ok(session);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        workoutSessionsService.deleteSession(id, email);
        return ResponseEntity.ok("Sessão deletada com sucesso");
    }
}
