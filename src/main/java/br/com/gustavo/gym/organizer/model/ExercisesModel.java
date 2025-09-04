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
    @Column(name= "sessionId" )
    private Long sessionId;
    @Column(name= "exerciseName" )
    private String exerciseName;
    @Column(name= "exerciseWeight" )
    private Double exerciseWeight;
    @Column(name= "exerciseRepetition" )
    private int exerciseRepetition;
    @Column(name= "exerciseSets" )
    private int exerciseSets;
    @Column(name= "breakTime" )
    private int breakTime;
}
