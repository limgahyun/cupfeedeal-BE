package com.cupfeedeal.domain.UserCafeLike.domain;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of="user_cafe_like_id")
@Table(name="user_cafe_like")
public class UserCafeLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCafeLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
