package com.mygroup.backendReslide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_address")
    @SequenceGenerator(name="generator_address", sequenceName = "sequence_address")
    private Long id;

    @Column(nullable = false)
    private String description;

    @NotBlank(message = "An address can't be empty.")
    private String value;

    @Column(nullable = false)
    private boolean enabled;
}
