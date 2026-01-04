package statify.gui;

import statify.database.DataStore;
import statify.models.League;
import statify.models.Team;
import statify.models.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UserDashboard extends JFrame {

    // --- COLOR PALETTE ---
    private final Color appBackground = Color.BLACK;
    private final Color headerBlue = new Color(0, 80, 200);
    private final Color accentYellow = new Color(255, 220, 0);
    private final Color inputBg = new Color(40, 40, 40);
    private final Color textColor = Color.WHITE;

    // Components
    private JComboBox<String> leagueDropdown;
    private JComboBox<String> teamDropdown;
    private JComboBox<String> playerDropdown;
    private JTextArea statsDisplay;

    public UserDashboard() {
        super("Statify - Player Stats Viewer");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(appBackground);

        mainPanel.add(createHeader());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createBrowserSection());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createStatsSection());

        add(mainPanel);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(headerBlue);
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        header.setMaximumSize(new Dimension(900, 80));

        JLabel title = new JLabel("Player Stats Viewer");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(accentYellow);

        header.add(title);
        return header;
    }

    private JPanel createBrowserSection() {
        JPanel panel = createSectionPanel("Browse Players");

        leagueDropdown = createStyledComboBox();
        teamDropdown = createStyledComboBox();
        playerDropdown = createStyledComboBox();

        // Populate leagues
        for (League l : DataStore.getLeagues()) {
            leagueDropdown.addItem(l.getName());
        }

        leagueDropdown.addActionListener(e -> updateTeams());
        teamDropdown.addActionListener(e -> updatePlayers());
        playerDropdown.addActionListener(e -> updateStats());

        panel.add(createLabel("Select Sport:"));
        panel.add(leagueDropdown);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabel("Select Team:"));
        panel.add(teamDropdown);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createLabel("Select Player:"));
        panel.add(playerDropdown);

        return panel;
    }

    private JPanel createStatsSection() {
        JPanel panel = createSectionPanel("Player Statistics");

        statsDisplay = new JTextArea(15, 40);
        statsDisplay.setEditable(false);
        statsDisplay.setBackground(inputBg);
        statsDisplay.setForeground(accentYellow);
        statsDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statsDisplay.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(statsDisplay);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        panel.add(scrollPane);
        return panel;
    }

    private void updateTeams() {
        teamDropdown.removeAllItems();
        String selectedLeague = (String) leagueDropdown.getSelectedItem();

        if (selectedLeague != null) {
            League league = DataStore.findLeague(selectedLeague);
            if (league != null) {
                // Access teams via reflection or getter
                try {
                    java.lang.reflect.Field teamsField = League.class.getDeclaredField("teams");
                    teamsField.setAccessible(true);
                    java.util.List<Team> teams = (java.util.List<Team>) teamsField.get(league);

                    for (Team t : teams) {
                        teamDropdown.addItem(t.getTeamName());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        playerDropdown.removeAllItems();
    }

    private void updatePlayers() {
        playerDropdown.removeAllItems();
        String selectedLeague = (String) leagueDropdown.getSelectedItem();
        String selectedTeam = (String) teamDropdown.getSelectedItem();

        if (selectedLeague != null && selectedTeam != null) {
            League league = DataStore.findLeague(selectedLeague);
            if (league != null) {
                Team team = league.getTeam(selectedTeam);
                if (team != null) {
                    try {
                        java.lang.reflect.Field rosterField = Team.class.getDeclaredField("roster");
                        rosterField.setAccessible(true);
                        java.util.List<Player> roster = (java.util.List<Player>) rosterField.get(team);

                        for (Player p : roster) {
                            playerDropdown.addItem(p.getName());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private void updateStats() {
        String selectedPlayer = (String) playerDropdown.getSelectedItem();

        if (selectedPlayer != null) {
            String selectedLeague = (String) leagueDropdown.getSelectedItem();
            String selectedTeam = (String) teamDropdown.getSelectedItem();

            League league = DataStore.findLeague(selectedLeague);
            if (league != null) {
                Team team = league.getTeam(selectedTeam);
                if (team != null) {
                    try {
                        java.lang.reflect.Field rosterField = Team.class.getDeclaredField("roster");
                        rosterField.setAccessible(true);
                        java.util.List<Player> roster = (java.util.List<Player>) rosterField.get(team);

                        for (Player p : roster) {
                            if (p.getName().equals(selectedPlayer)) {
                                statsDisplay.setText("Player: " + p.getName() + "\nTeam: " + selectedTeam + "\nSport: " + selectedLeague + "\n\n");
                                p.showStats();
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private JPanel createSectionPanel(String titleText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(appBackground);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(0, 20, 0, 20),
                new LineBorder(Color.DARK_GRAY, 1)
        ));

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(headerBlue);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(title);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(accentYellow);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setBackground(inputBg);
        combo.setForeground(Color.WHITE);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return combo;
    }
}
//         return field;
