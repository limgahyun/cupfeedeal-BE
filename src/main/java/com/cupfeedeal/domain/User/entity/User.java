package com.cupfeedeal.domain.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private Long userId;

    @Column(name = "nickname", nullable = false, length = 50)
    private String username;

    @Column(name = "phone_num", nullable = true, length = 20)
    private String phone_num;

    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Column(name = "user_level")
    private Integer user_level; // 0, 1, 2, 3, 4, 5 중 하나

    @Column(name = "paw_count")
    private Integer pawCount = 0; // 0, 1, 2, 3 중 하나

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @PrePersist
    public void prePersist() {
        if (pawCount == null) {
            pawCount = 0;
        }
    }

    @Override
    public String getPassword() {
        return null;
    }

}
