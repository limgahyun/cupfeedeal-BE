package com.cupfeedeal.domain.User.entity;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of="user_id")
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cupcat_id", referencedColumnName = "cupcat_id", nullable = false)
    private Cupcat cupcat;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "phone_num", nullable = false, length = 20)
    private String phone_num;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "user_level", nullable = false)
    private Integer user_level; // 0, 1, 2, 3, 4, 5 중 하나

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;


}
