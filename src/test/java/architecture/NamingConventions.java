package architecture;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NamingConventions {
    static final String BASE_PACKAGE = "ch.vkaelin.music";
    static final String DOMAIN_PACKAGE = BASE_PACKAGE + ".domain..";
    static final String API_PACKAGE = BASE_PACKAGE + ".api..";
    static final String PERSISTENCE_PACKAGE = BASE_PACKAGE + ".persistence..";
}
