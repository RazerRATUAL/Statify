package statify.gui;

import statify.database.DataStore;
import statify.models.League;
import statify.models.Team;
import statify.models.BasketballPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AdminDashboard extends JFrame {

    // --- YOUR CUSTOM COLOR PALETTE ---
    // Background is Black
    private final Color appBackground = Color.BLACK;

    // Header is a strong Blue
    private final Color headerBlue = new Color(0, 80, 200);

    // Accents/Highlights are Yellow
    private final Color accentYellow = new Color(255, 220, 0);

    // Input boxes are Dark Grey (so you can see them against the black)
    private final Color inputBg = new Color(40, 40, 40);
    private final Color textColor = Color.WHITE;

    // Input Fields
    private JTextField leagueInput;
    private JTextField teamNameInput, teamLeagueInput;
    private JTextField playerNameInput, playerTeamInput;

    public AdminDashboard() {
        super("Statify Admin - Content Manager");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(appBackground);

        // 1. ADD HEADER (Blue)
        mainPanel.add(createHeader());

        // 2. ADD SECTIONS
        mainPanel.add(createLeagueSection());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        mainPanel.add(createTeamSection());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        mainPanel.add(createPlayerSection());

        add(mainPanel);
    }

    // --- HELPER: Create the Blue Header ---
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(headerBlue); // <--- Using Blue here
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        header.setMaximumSize(new Dimension(900, 80));

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(accentYellow); // <--- Yellow Text

        header.add(title);
        return header;
    }

    // --- SECTION 1: ADD SPORT ---
    private JPanel createLeagueSection() {
        JPanel panel = createSectionPanel("1. Create New Sport");

        leagueInput = createStyledTextField();
        JButton addBtn = createStyledButton("Create Sport");

        addBtn.addActionListener(e -> {
            String name = leagueInput.getText();
            if (!name.isEmpty()) {
                DataStore.addLeague(new League(name));
                JOptionPane.showMessageDialog(this, "Success: Added " + name);
                leagueInput.setText("");
            }
        });

        panel.add(createLabel("Sport Name (e.g., 'NBA'):"));
        panel.add(leagueInput);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(addBtn);

        return panel;
    }

    // --- SECTION 2: ADD TEAM ---
    private JPanel createTeamSection() {
        JPanel panel = createSectionPanel("2. Add Team to Sport");

        teamNameInput = createStyledTextField();
        teamLeagueInput = createStyledTextField();
        JButton addBtn = createStyledButton("Add Team");

        addBtn.addActionListener(e -> {
            String tName = teamNameInput.getText();
            String lName = teamLeagueInput.getText();

            League league = DataStore.findLeague(lName);
            if (league != null) {
                league.addTeam(new Team(tName));
                JOptionPane.showMessageDialog(this, "Success: Added " + tName + " to " + lName);
                teamNameInput.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Sport '" + lName + "' not found. Create it first!");
            }
        });

        panel.add(createLabel("Team Name (e.g., 'Lakers'):"));
        panel.add(teamNameInput);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(createLabel("Sport (e.g., 'NBA'):"));
        panel.add(teamLeagueInput);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(addBtn);

        return panel;
    }

    // --- SECTION 3: ADD PLAYER ---
    private JPanel createPlayerSection() {
        JPanel panel = createSectionPanel("3. Add Player to Team");

        playerNameInput = createStyledTextField();
        playerTeamInput = createStyledTextField();
        JButton addBtn = createStyledButton("Add Player");

        addBtn.addActionListener(e -> {
            String pName = playerNameInput.getText();
            String tName = playerTeamInput.getText();

            Team foundTeam = null;
            for (League l : DataStore.getLeagues()) {
                foundTeam = l.getTeam(tName);
                if (foundTeam != null) break;
            }

            if (foundTeam != null) {
                foundTeam.addPlayer(new BasketballPlayer(pName));
                JOptionPane.showMessageDialog(this, "Success: Added " + pName + " to " + tName);
                playerNameInput.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Team '" + tName + "' not found!");
            }
        });

        panel.add(createLabel("Player Name:"));
        panel.add(playerNameInput);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(createLabel("Team Name:"));
        panel.add(playerTeamInput);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(addBtn);

        return panel;
    }

    // --- STYLING HELPERS ---

    private JPanel createSectionPanel(String titleText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(appBackground); // Black
        // Yellow border around the sections
        panel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(0, 20, 0, 20),
                new LineBorder(Color.DARK_GRAY, 1)
        ));

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(headerBlue); // Blue Titles
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(title);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(accentYellow); // Yellow Labels
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(inputBg);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(headerBlue); // Blue Buttons
        btn.setForeground(Color.WHITE); // White Text on buttons
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }
}