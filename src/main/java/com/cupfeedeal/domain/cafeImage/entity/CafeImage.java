package com.cupfeedeal.domain.cafeImage.entity;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CafeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column
    private String imageUrl;

    @Column
    private Boolean isMainImage = false;

    @Builder
    public CafeImage(Cafe cafe, String imageUrl, Boolean isMainImage) {
        this.cafe = cafe;
        this.imageUrl = imageUrl;
        this.isMainImage = isMainImage;
    }
}
