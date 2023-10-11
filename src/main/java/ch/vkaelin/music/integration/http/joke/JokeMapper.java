package ch.vkaelin.music.integration.http.joke;

import ch.vkaelin.music.integration.http.joke.response.JokeSingle;
import ch.vkaelin.music.integration.http.joke.response.JokeTwoPart;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JokeMapper {
    public String fromRaw(JokeTwoPart joke) {
        return joke.getSetup() + "      " + joke.getDelivery();
    }

    public String fromRaw(JokeSingle joke) {
        return joke.getJoke();
    }
}
