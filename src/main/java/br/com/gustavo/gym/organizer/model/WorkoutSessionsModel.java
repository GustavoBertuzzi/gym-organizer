package br.com.gustavo.gym.organizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name= "workoutSessionsModel")
@Table(name= "workoutSessionsModel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkoutSessionsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "sessionId")
    private Long sessionId;
    @Column(name= "userId")
    private Long userId;
    @Column(name= "sessionDate")
    private LocalDateTime sessionDate;
    @Column(name= "sleepHours")
    private Long sleepHours;
    @Column(name= "preWorkout")
    private boolean preWorkout;
    @Column(name= "notes")
    private String notes;

}
