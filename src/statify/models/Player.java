package statify.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private String name;
    private List<StatRecord> stats; // A list of the stats we made above

    public Player(String name) {
        this.name = name;
        this.stats = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addStat(int year, String statName, double value) {
        stats.add(new StatRecord(year, statName, value));
    }

    public void showStats() {
        System.out.println("   Stats for " + name + ":");
        for (StatRecord s : stats) {
            System.out.println("   - " + s);
        }
    }
}