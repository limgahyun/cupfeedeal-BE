package com.cupfeedeal.domain.User.entity;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.global.BaseEntity;
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
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "nickname", nullable = false, length = 50)
    private String username;

    @Column(name = "phone_num", nullable = true, length = 20)
    private String phone_num;

    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Column(name = "user_level", columnDefinition = "Integer default 0")
    private Integer user_level; // 0, 1, 2, 3, 4, 5 중 하나

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

}
