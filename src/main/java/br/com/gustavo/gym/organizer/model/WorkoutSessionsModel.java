package br.com.gustavo.gym.organizer.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workout_sessions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkoutSessionsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersModel user;

    @Column(name = "session_date")
    private LocalDateTime sessionDate;

    @Column(name = "sleep_hours")
    private Long sleepHours;

    @Column(name = "pre_workout")
    private Boolean preWorkout;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @JsonManagedReference
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<WorkoutExerciseModel> exercises;
}
