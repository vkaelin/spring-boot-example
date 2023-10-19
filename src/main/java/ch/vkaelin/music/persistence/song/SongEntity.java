package ch.vkaelin.music.persistence.song;

import ch.vkaelin.music.persistence.AbstractEntity;
import ch.vkaelin.music.persistence.artist.ArtistEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
