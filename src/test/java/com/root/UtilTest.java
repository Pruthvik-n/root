package com.root;

import com.root.command.Driver;
import com.root.command.Trip;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    private static final String FAKE_DRIVER_CMD = "Driver DarthVader";
    private static final String FAKE_TRIP_CMD = "Trip DarthVader 07:15 07:45 17.3";

    @Test
    public void ifNoExistingFile_returnEmptyStream() {
        Stream<String> lines = Util.getLinesFromFile("This does not exist");
        assertEquals(0, lines.count());
    }

    @Test
    public void ifLineStartsWithDriver_returnDriverCommand() {
        assertTrue(Util.extract(FAKE_DRIVER_CMD) instanceof Driver);
    }

    @Test
    public void ifLineStartsWithTrip_returnTripCommand() {
        assertTrue(Util.extract(FAKE_TRIP_CMD) instanceof Trip);
    }

    @Test
    public void ifLineStartsWithNeitherTripNorDriver_returnTripCommand() {
        assertNull(Util.extract(java.util.UUID.randomUUID().toString() + " args"));
    }

}
