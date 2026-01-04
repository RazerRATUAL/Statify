package statify.models;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamName;
    private List<Player> roster;

    public Team(String teamName) {
        this.teamName = teamName;
        this.roster = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void addPlayer(Player p) {
        roster.add(p);
    }

    public void showRoster() {
        System.out.println(" Team: " + teamName);
        for (Player p : roster) {
            System.out.println("  > Player: " + p.getName());
            p.showStats();
        }
    }
}