package com.mygroup.backendReslide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_refresh_token")
    @SequenceGenerator(name="generator_refresh_token", sequenceName = "sequence_refresh_token")
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String token;
    @NotNull
    private Instant createdDate;
}
