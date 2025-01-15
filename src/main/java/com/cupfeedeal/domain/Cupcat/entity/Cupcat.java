package com.cupfeedeal.domain.Cupcat.entity;

import jakarta.persistence.*;
import lombok.*;
import com.cupfeedeal.domain.Cupcat.entity.CupcatLevelEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of="cupcatId")
@Table(name = "cupcat", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cupcat_level", "cupcat_type"})
})
public class Cupcat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cupcatId;

    @OneToMany(mappedBy = "cupcat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserCupcat> userCupcats = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "cupcat_level", nullable = false, length = 10)
    private CupcatLevelEnum cupcat_level;

    @Enumerated(EnumType.STRING)
    @Column(name = "cupcat_type")
    private CupcatTypeEnum cupcat_type;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

}
