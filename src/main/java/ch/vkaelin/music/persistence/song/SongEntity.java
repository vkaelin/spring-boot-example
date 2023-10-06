package ch.vkaelin.music.persistence.song;

import ch.vkaelin.music.domain.song.Song;
import ch.vkaelin.music.persistence.AbstractEntity;
import ch.vkaelin.music.persistence.artist.ArtistEntity;
import jakarta.persistence.*;
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
@Table(name = "songs")
public class SongEntity extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String file;

    @Column(nullable = false)
    private String genre;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    public static SongEntity from(Song song) {
        return SongEntity.builder()
                .id(song.getId())
                .name(song.getName())
                .file(song.getFile())
                .genre(song.getGenre())
                .artist(ArtistEntity.from(song.getArtist()))
                .build();
    }

    public Song fromThis(boolean withArtist) {
        var song = Song.builder()
                .id(getId())
                .name(getName())
                .file(getFile())
                .genre(getGenre());

        if (withArtist) {
            song.artist(getArtist().fromThis());
        }

        return song.build();
    }
}
