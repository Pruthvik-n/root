package com.root.command;

import com.root.TripSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripTest {

    @Test
    public void ifNull_returnFalseOnValidate(){
        Trip tripCommand = new Trip(null);
        assertFalse(tripCommand.isExecutable());
    }

    @Test
    public void ifArgumentsNotEqualToFour_returnFalseOnValidate(){
        Trip tripCommand = new Trip("DarthVader 06:12 06:00 21.0 extraArg");
        assertFalse(tripCommand.isExecutable());
    }

    @Test
    public void ifStartTimeWithInvalidFormat_returnFalseOnValidate(){
        Trip tripCommand = new Trip("DarthVader 06:12:123 08:00 21.0");
        assertFalse(tripCommand.isExecutable());
    }

    @Test
    public void ifEndTimeWithInvalidFormat_returnFalseOnValidate(){
        Trip tripCommand = new Trip("DarthVader 06:12 ABC 21.0");
        assertFalse(tripCommand.isExecutable());
    }

    @Test
    public void ifMilesInvalid_returnFalseOnValidate(){
        Trip tripCommand = new Trip("DarthVader 06:12 08:00 abc");
        assertFalse(tripCommand.isExecutable());
    }

    @Test
    public void ifAvgSpeedLessThan5_returnFalseOnValidate(){
        Trip tripCommand = new Trip("DarthVader 06:00 08:00 5");
        assertFalse(tripCommand.isExecutable());
    }

    @Test
    public void ifAvgSpeedGreaterThan100_returnFalseOnValidate(){
        Trip tripCommand = new Trip("DarthVader 06:00 08:00 201");
        assertFalse(tripCommand.isExecutable());
    }

    @Nested
    class whenValidTripCommand{

        private static final String FAKE_TRIP_COMMAND = "DarthVader 06:00 08:00 10";
        private static final String FAKE_DRIVER_NAME = "DarthVader";

        private Map<String, TripSummary> tripSummaryMap;
        private Trip tripCommand;

        @BeforeEach
        public void setUp(){
            this.tripSummaryMap = new HashMap<>();
            this.tripCommand = new Trip(FAKE_TRIP_COMMAND);
            tripCommand.isExecutable();
        }

        @Test
        public void ifArgumentsValid_returnTrue(){
            assertTrue(tripCommand.isExecutable());
        }

        @Test
        public void ifNameDoesNotExist_doNothing() {
            tripCommand.execute(tripSummaryMap);
            assertTrue(tripSummaryMap.isEmpty());
        }

        @Test
        public void ifNameExists_addToExistingSummary() {
            tripSummaryMap.put(FAKE_DRIVER_NAME, new TripSummary(FAKE_DRIVER_NAME));
            tripCommand.execute(tripSummaryMap);

            TripSummary summary = tripSummaryMap.get(FAKE_DRIVER_NAME);

            assertEquals(FAKE_DRIVER_NAME, summary.getDriver());
            assertEquals(120, summary.getTotalMinutes());
            assertEquals(10, summary.getTotalMiles());
        }

    }


}
