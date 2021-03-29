package com.root.command;

import com.root.TripSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Trip implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Trip.class);

    private String[] args;
    private String name;
    private LocalTime tripStartTime;
    private LocalTime tripEndTime;
    private Double miles;

    public Trip(String line) {
        if (line != null)
            this.args = line.split(" ");
    }

    @Override
    public Boolean isExecutable() {
        if (args == null || args.length != 4){
            LOGGER.error("Invalid number of arguments({}). Trip command requires 4 arguments in the following format - " +
                            "{driver start-time end-time miles}, instead {} provided",
                    Arrays.toString(args),
                    args != null ? args.length : 0);
            return false;
        }

        String driverName = args[0];
        LocalTime startTime = parseTime(args[1]);
        LocalTime endTime = parseTime(args[2]);
        Double miles = parseMiles(args[3]);
        double avgSpeed = getAverageSpeed(miles, startTime, endTime);
        boolean isTripValid = avgSpeed >= 5 && avgSpeed <= 100;

        this.name = driverName;
        this.tripStartTime = startTime;
        this.tripEndTime = endTime;
        this.miles = miles;

        return driverName != null && startTime != null && endTime != null && miles != null && isTripValid;
    }

    private double getAverageSpeed(Double miles, LocalTime startTime, LocalTime endTime) {
        if (miles == null || startTime == null || endTime == null) return -1;
        return miles / (MINUTES.between(startTime, endTime)/60f);
    }

    private Double parseMiles(String miles) {
        try {
            return Double.parseDouble(miles);
        }
        catch (Exception e){
            LOGGER.error("Invalid miles format({}). Miles should be a number, instead {} provided",
                    Arrays.toString(args),
                    miles);
            return null;
        }
    }

    private LocalTime parseTime(String time) {
        try{
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        }
        catch (Exception e){
            LOGGER.error("Invalid time format({}) Time should be in the following format - {HH:mm}, instead {} provided",
                    Arrays.toString(args),
                    time);
            return null;
        }
    }

    @Override
    public void execute(Map<String, TripSummary> tripSummaryMap) {
        if (tripSummaryMap.containsKey(this.name)){
            TripSummary tripSummary = tripSummaryMap.get(this.name);
            tripSummary.addTotalMiles(this.miles);
            tripSummary.addTotalTime(MINUTES.between(tripStartTime, tripEndTime));
        }
    }

}
