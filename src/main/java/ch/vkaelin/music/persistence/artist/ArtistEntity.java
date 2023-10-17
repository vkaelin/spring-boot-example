package ch.vkaelin.music.persistence.artist;

import ch.vkaelin.music.domain.artist.Artist;
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

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

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

    public static ArtistEntity from(Artist artist) {
        return ArtistEntity.builder()
                .id(artist.getId())
                .artistName(artist.getArtistName())
                .user(UserEntity.from(artist.getUser()))
//                .songs(emptyIfNull(artist.getSongs())
//                        .stream()
//                        .map(SongEntity::from)
//                        .toList())
                .build();
    }

    public Artist fromThis() {
        return Artist.builder()
                .id(getId())
                .artistName(getArtistName())
                .user(getUser().fromThis())
                .songs(emptyIfNull(getSongs())
                        .stream()
                        .map(s -> s.fromThis(false))
                        .toList())
                .build();
    }
}
