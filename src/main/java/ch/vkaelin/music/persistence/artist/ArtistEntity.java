package ch.vkaelin.music.persistence.artist;

import ch.vkaelin.music.persistence.AbstractEntity;
import ch.vkaelin.music.persistence.song.SongEntity;
import ch.vkaelin.music.persistence.user.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "artists")
public class ArtistEntity extends AbstractEntity {
    @Column(nullable = false)
    private String artistName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "artist", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    private List<SongEntity> songs;
}
