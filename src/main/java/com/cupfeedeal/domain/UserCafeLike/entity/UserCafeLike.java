package com.cupfeedeal.domain.UserCafeLike.entity;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of="user_cafe_like_id")
@Table(name="user_cafe_like")
public class UserCafeLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCafeLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

}
