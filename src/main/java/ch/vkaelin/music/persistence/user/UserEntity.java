package ch.vkaelin.music.persistence.user;

import ch.vkaelin.music.domain.user.Role;
import ch.vkaelin.music.domain.user.User;
import ch.vkaelin.music.persistence.AbstractEntity;
import ch.vkaelin.music.persistence.artist.ArtistEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class UserEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ArtistEntity artist;

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public User fromThis() {
        return User.builder()
                .id(getId())
                .username(getUsername())
                .password(getPassword())
                .role(getRole())
                .build();
    }
}
