package statify.users;

import statify.models.League;
import statify.models.Player;
import statify.models.Team;
import statify.models.StatRecord;

public class Admin extends AppUser {
    public Admin(String username) {
        super(username);
    }

    public League createLeague(String leagueName) {
        System.out.println("ADMIN: Created league: " + leagueName);
        return new League(leagueName);
    }

    public Team createTeam(League league, String teamName) {
        Team t = new Team(teamName);
        league.addTeam(t);
        System.out.println("ADMIN: Created team: " + teamName);
        return t;
    }


    public void addStatToPlayer(Player p, int year, String stat, double val) {
        p.addStat(year, stat, val);
        System.out.println("ADMIN: Added stats to " + p.getName());
    }
}