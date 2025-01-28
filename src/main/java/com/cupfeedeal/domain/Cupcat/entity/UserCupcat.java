package com.cupfeedeal.domain.Cupcat.entity;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Table(name = "user_cupcat")
public class UserCupcat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cupcatId", nullable = false)
    private Cupcat cupcat;

    private String cafeName;

    @Builder
    public UserCupcat(User user, Cupcat cupcat, String cafeName) {
        this.user = user;
        this.cupcat = cupcat;
        this.cafeName = cafeName;
    }

}
