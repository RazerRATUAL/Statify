package statify.models;

public class StatRecord {
    private int year;
    private String statName;
    private double value;

    public StatRecord(int year, String statName, double value) {
        this.year = year;
        this.statName = statName;
        this.value = value;
    }

    @Override
    public String toString() {
        return year + " " + statName + ": " + value;
    }
}