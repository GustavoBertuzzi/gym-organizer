package br.com.gustavo.gym.organizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name= "exercisesModel")
@Table(name= "exercisesModel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExercisesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "exerciseId" )
    private Long exerciseId;

    @ManyToOne
    @JoinColumn(name = "sessionId", nullable = false)
    private WorkoutSessionsModel session;

    @Column(name= "exerciseName", nullable = false)
    private String exerciseName;
    @Column(name= "exerciseWeight", nullable = false )
    private Double exerciseWeight;
    @Column(name= "exerciseRepetition", nullable = false )
    private int exerciseRepetition;
    @Column(name= "exerciseSets", nullable = false )
    private int exerciseSets;
    @Column(name= "breakTime" )
    private int breakTime;
}
