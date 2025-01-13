package com.cupfeedeal.domain.User.entity;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of="userId")
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, updatable = false)
    private Long userId;

    @Column(name = "kakaoId", length =100, nullable = false, unique = true)
    private String kakaoId;

    @Column(name = "nickname", nullable = false, length = 50)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cupcat_id", referencedColumnName = "cupcat_id", nullable = true)
    private Cupcat cupcat;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserSubscription> subscriptions = new ArrayList<>();

    @Column(name = "phone_num", nullable = true, length = 20)
    private String phone_num;

    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Column(name = "user_level")
    private Integer user_level; // 0, 1, 2, 3, 4, 5 중 하나

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

}
