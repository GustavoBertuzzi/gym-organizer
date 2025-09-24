package br.com.gustavo.gym.organizer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workout_exercises")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkoutExerciseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_exercise_id")
    private Long workoutExerciseId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private WorkoutSessionsModel session;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExercisesModel exercise;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private int repetitions;

    @Column(nullable = false)
    private int sets;

    @Column(name = "break_time")
    private Integer breakTime; // segundos, nullable
}
