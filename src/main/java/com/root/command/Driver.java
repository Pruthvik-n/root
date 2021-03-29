package com.root.command;

import com.root.TripSummary;

import java.util.Map;

public class Driver implements Command{
    private String name;

    public Driver(String params) {
        if (params != null)
            this.name = params.trim();
    }

    @Override
    public Boolean isExecutable() {
        return this.name != null && !this.name.contains(" ");
    }

    @Override
    public void execute(Map<String, TripSummary> tripSummaryMap) {
        if(!tripSummaryMap.containsKey(this.name))
            tripSummaryMap.put(this.name, new TripSummary(this.name));
    }
}
