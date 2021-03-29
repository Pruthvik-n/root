package com.root.command;

import com.root.TripSummary;

import java.util.Map;

public interface Command {

    /**
     *  Validate all the required parameters to execute the command
     *
     * @return returns if Command is executable
     */
    Boolean isExecutable();

    /**
     * Execute the command on the provided trip summary
     *
     * @param tripSummaryMap map that includes the trip summary associated with each Driver
     */
    void execute(Map<String, TripSummary> tripSummaryMap);

}
