package statify.models;

import java.util.ArrayList;
import java.util.List;

public class League {
    private String leagueName; //"NBA" example
    private List<Team> teams;

    public League(String leagueName) {
        this.leagueName = leagueName;
        this.teams = new ArrayList<>();
    }

    public void addTeam(Team t) {
        teams.add(t);
    }

    public void showAllTeams() {
        System.out.println("=== LEAGUE: " + leagueName + " ===");
        for (Team t : teams) {
            t.showRoster();
            System.out.println();
        }
    }

    public Team getTeam(String teamNameSearch) {
        for (Team t : teams) {
            if (t.getTeamName().equalsIgnoreCase(teamNameSearch)) {
                return t;
            }
        }
        return null; // Team not found
    }

    public String getName() {
        return leagueName; // Ensure you have this getter!
    }
}