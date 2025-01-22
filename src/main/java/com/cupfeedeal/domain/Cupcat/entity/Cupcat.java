package com.cupfeedeal.domain.Cupcat.entity;

import com.cupfeedeal.domain.Cupcat.enumerate.CupcatTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of="cupcatId")
@Table(name = "cupcat", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cupcat_level", "cupcat_type"})
})
public class Cupcat{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cupcatId;

    @Column(name = "cupcat_level", nullable = false, length = 10)
    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(name = "cupcat_type")
    private CupcatTypeEnum type;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

}
