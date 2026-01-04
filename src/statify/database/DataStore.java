package statify.database;

import statify.models.League;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    // This LIST acts as our temporary database
    private static List<League> leagues = new ArrayList<>();

    // This block runs once when the app starts to give you some starter data
    static {
        leagues.add(new League("NBA"));
        leagues.add(new League("NFL"));
    }

    // METHOD: Add a new League (Sport)
    public static void addLeague(League league) {
        leagues.add(league);
        System.out.println("DATASTORE: Saved new league: " + league.getName());
    }

    // METHOD: Get all leagues
    public static List<League> getLeagues() {
        return leagues;
    }

    // METHOD: Find a league by name (Helper)
    public static League findLeague(String name) {
        for (League l : leagues) {
            if (l.getName().equalsIgnoreCase(name)) { // Ignore capitalization
                return l;
            }
        }
        return null; // Not found
    }
}