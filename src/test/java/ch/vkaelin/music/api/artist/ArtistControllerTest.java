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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistRepository artistRepository;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

    @BeforeEach
    public void init() {
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
        MvcResult result = mockMvc.perform(get("/api/v1/artists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<SearchedArtistDto> artists = mapper.readerForListOf(SearchedArtistDto.class).readValue(responseBody);

        assertNotNull(artists);
        assertEquals(artists.size(), artistRepository.findAll().size());
        assertNotEquals(artists.size(), 0);
    }
}
