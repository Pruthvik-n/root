package com.root.command;

import com.root.TripSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {

    private static final String FAKE_DRIVER_NAME = "DarthVader";

    @Test
    public void whenDriverNameNull_returnFalseOnIsExecutable(){
        Driver driverCommand = new Driver(null);
        assertFalse(driverCommand.isExecutable());
    }

    @Test
    public void whenDriverNameIncludesSpace_returnFalseOnIsExecutable(){
        Driver driverCommand = new Driver("Spaced Name");
        assertFalse(driverCommand.isExecutable());
    }

    @Test
    public void whenDriverNameIncludesTrailingSpace_TrimOnExecute(){
        String spacedName = "   StartingAndEndingWithSpaces   ";
        Driver driverCommand = new Driver(spacedName);
        Map<String, TripSummary> tripSummaryMap = new HashMap<>();
        driverCommand.execute(tripSummaryMap);

        assertFalse(tripSummaryMap.containsKey(spacedName));
        assertTrue(tripSummaryMap.containsKey("StartingAndEndingWithSpaces"));
    }

    @Nested
    class whenValidDriverCommand{

        private Map<String, TripSummary> tripSummaryMap;
        private Driver driverCommand;

        @BeforeEach
        public void setUp(){
            this.tripSummaryMap = new HashMap<>();
            this.driverCommand = new Driver(FAKE_DRIVER_NAME);
        }

        @Test
        public void validateDriverCommand_returnTrue(){
            assertTrue(driverCommand.isExecutable());
        }


        @Test
        public void ifNameDoesNotExist_addToTripSummaryOnExecute(){
            driverCommand.execute(tripSummaryMap);

            assertFalse(tripSummaryMap.isEmpty());
            assertTrue(tripSummaryMap.containsKey(FAKE_DRIVER_NAME));
            assertEquals(tripSummaryMap.get(FAKE_DRIVER_NAME).getDriver(), FAKE_DRIVER_NAME);
        }


        @Test
        public void ifNameAlreadyExists_doNothingOnExecute(){
            tripSummaryMap.put(FAKE_DRIVER_NAME, new TripSummary(FAKE_DRIVER_NAME));
            int sizeBeforeExecute = tripSummaryMap.size();

            driverCommand.execute(tripSummaryMap);

            assertEquals(tripSummaryMap.size(), sizeBeforeExecute, "Trip Summary Map size has changed");

        }
    }

}
