package com.cupfeedeal.domain.Cupcat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of="cupcat_id")
@Table(name="cupcat")
public class Cupcat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cupcat_id", nullable = false)
    private Long cupcat_id;
}
