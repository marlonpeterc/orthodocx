package nz.co.orthodocx.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static nz.co.orthodocx.constants.Paths.*;

public enum Routes {

    PROFILE(WEBFLUX_PROFILE.value()),

    PROFILE_ALL(WEBFLUX_PROFILE.value() + ALL.value()),

    PROFILE_BY_FIRSTNAME(WEBFLUX_PROFILE.value() + FIRSTNAME.value() + SEPARATOR.value() + FIRSTNAME_PATH_PARAM.value()),

    PROFILE_BY_LASTNAME(WEBFLUX_PROFILE.value() + LASTNAME.value() + SEPARATOR.value() + LASTNAME_PATH_PARAM.value()),

    PROFILE_BY_FIRSTNAME_OR_LASTNAME(WEBFLUX_PROFILE.value() +
            FIRSTNAME.value() + SEPARATOR.value() + FIRSTNAME_PATH_PARAM.value() + SEPARATOR.value() +
            OR.value() + SEPARATOR.value() +
            LASTNAME.value() + SEPARATOR.value() + LASTNAME_PATH_PARAM.value()),

    PROFILE_BY_FIRSTNAME_AND_LASTNAME(WEBFLUX_PROFILE.value() +
            FIRSTNAME.value() + SEPARATOR.value() + FIRSTNAME_PATH_PARAM.value() + SEPARATOR.value() +
            LASTNAME.value() + SEPARATOR.value() + LASTNAME_PATH_PARAM.value()),

    PROFILE_BY_ID_FNAME_LNAME(WEBFLUX_PROFILE.value() +
            ID.value() + SEPARATOR.value() + ID_PATH_PARAM.value() + SEPARATOR.value() +
            FIRSTNAME.value() + SEPARATOR.value() + FIRSTNAME_PATH_PARAM.value() + SEPARATOR.value() +
            LASTNAME.value() + SEPARATOR.value() + LASTNAME_PATH_PARAM.value()),

    PROFILE_BY_ID(WEBFLUX_PROFILE.value() + ID.value() + SEPARATOR.value() + ID_PATH_PARAM.value()),

    PROFILE_CREATE_BY_FNAME_LNAME(WEBFLUX_PROFILE.value() + "create/" +
            FIRSTNAME.value() + SEPARATOR.value() + FIRSTNAME_PATH_PARAM.value() + SEPARATOR.value() +
            LASTNAME.value() + SEPARATOR.value() + LASTNAME_PATH_PARAM.value());

    Logger log = LoggerFactory.getLogger(Routes.class);
    private String _pattern;

    Routes(String pattern) {
        _pattern = pattern;
        log.info(String.format("Creating Route: %s", _pattern));
    }

    public String pattern() {
        return _pattern;
    }
}
