package com.root;

public class TripSummary implements Comparable<TripSummary> {

    private final String driver;
    private Double totalMiles;
    private Long totalMinutes;

    public TripSummary(String name) {
        this.driver = name;
        this.totalMiles = (double) 0;
        this.totalMinutes = 0L;
    }

    public String getDriver() {
        return driver;
    }

    public double getTotalMiles() {
        return totalMiles;
    }

    public void addTotalMiles(double miles) {
        this.totalMiles += miles;
    }

    public void addTotalTime(long minutes) {
        this.totalMinutes += minutes;
    }

    public long getTotalMinutes() {
        return totalMinutes;
    }

    private Double getAvgSpeed() {
        return this.totalMiles / (this.totalMinutes /60f);
    }

    @Override
    public String toString() {
        if (this.totalMiles > 0 ) {
            return String.format("%s: %d miles @ %d mph\n",
                    this.driver,
                    Math.round(this.totalMiles),
                    Math.round(getAvgSpeed()));
        }
        return String.format("%s: %d miles\n", this.driver, Math.round(this.totalMiles));
    }

    @Override
    public int compareTo(TripSummary o) {
        return Double.compare(o.getTotalMiles(), totalMiles);
    }
}
