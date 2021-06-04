package com.mygroup.backendReslide.model;

import com.mygroup.backendReslide.model.status.DatabaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class MeasurementType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Status is required")
    private DatabaseStatus status;

    @Column(nullable = false)
    private String notes;
}