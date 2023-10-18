package ch.vkaelin.music.integration.http.joke;

import ch.vkaelin.music.integration.http.joke.response.JokeSingle;
import ch.vkaelin.music.integration.http.joke.response.JokeTwoPart;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class JokeGsonDecoder implements Decoder {
    private final Gson gson = new Gson();

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.body() == null) {
            return null;
        }

        try (InputStreamReader reader = new InputStreamReader(response.body().asInputStream())) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            if (!jsonObject.has("type")) {
                return new GsonDecoder(gson).decode(response, type);
            }

            boolean isTwoPart = jsonObject.get("type").getAsString().equals("twopart");
            var targetClass = isTwoPart ? JokeTwoPart.class : JokeSingle.class;

            return gson.fromJson(jsonObject, targetClass);
        }
    }
}
