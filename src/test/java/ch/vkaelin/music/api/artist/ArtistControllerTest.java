package ch.vkaelin.music.api.artist;

import ch.vkaelin.music.domain.user.Role;
import ch.vkaelin.music.persistence.artist.ArtistEntity;
import ch.vkaelin.music.persistence.artist.ArtistRepository;
import ch.vkaelin.music.persistence.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ArtistControllerTest {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        
        UserEntity user = UserEntity.builder()
                .username("bob_user")
                .password("1234")
                .role(Role.ARTIST)
                .build();
        artistRepository.save(
                ArtistEntity.builder()
                        .artistName("Bob")
                        .user(user)
                        .build()
        );
    }

    @AfterEach
    public void cleanUp() {
        artistRepository.deleteAll();
    }

    @Test
    public void shouldGetArtists() throws Exception {
        MvcResult result = mvc.perform(get("/api/v1/artists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<SearchedArtistDto> artists = mapper.readerForListOf(SearchedArtistDto.class).readValue(responseBody);

        assertNotNull(artists);
        assertEquals(artists.size(), artistRepository.findAll().size());
        assertNotEquals(artists.size(), 0);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void shouldDeleteArtist() throws Exception {
        List<ArtistEntity> artists = artistRepository.findAll();
        assertEquals(artists.size(), 1);

        Integer artistId = artists.get(0).getId();
        mvc.perform(delete("/api/v1/artists/" + artistId))
                .andExpect(status().isOk())
                .andReturn();

        artists = artistRepository.findAll();
        assertEquals(artists.size(), 0);
    }

    @Test
    @WithMockUser(username = "bob", authorities = {"ARTIST"})
    public void shouldNotDeleteArtist() throws Exception {
        List<ArtistEntity> artists = artistRepository.findAll();
        assertEquals(artists.size(), 1);

        Integer artistId = artists.get(0).getId();
        mvc.perform(delete("/api/v1/artists/" + artistId))
                .andExpect(status().isForbidden())
                .andReturn();

        artists = artistRepository.findAll();
        assertEquals(artists.size(), 1);
    }
}
