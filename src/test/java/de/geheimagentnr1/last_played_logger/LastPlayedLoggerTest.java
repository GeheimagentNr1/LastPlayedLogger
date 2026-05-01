package de.geheimagentnr1.last_played_logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LastPlayedLoggerTest {

    @Test
    void modIdIsValid() {

        String modId = "last_played_logger";
        assertTrue( modId.matches( "[a-z][a-z0-9_]{1,63}" ) );
    }
}
