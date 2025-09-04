package br.com.gustavo.gym.organizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name= "workoutTypesModel")
@Table(name= "workoutTypesModel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkoutTypesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "workoutTypeId" )
    private Long workoutTypeId;
    @Column(name= "workoutTypeName" )
    private String workoutTypeName;
}
