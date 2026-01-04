package statify.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StatifyGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JLabel nameLabel;
    private JLabel teamLabel;
    private JTable statsTable;
    private DefaultTableModel tableModel; // to add/remove rows

    // Colors
    Color musePurple = new Color(86, 39, 149);
    Color darkBg = new Color(25, 25, 25);
    Color lakersYellow = new Color(253, 185, 39);
    Color   mavsBlue = new Color(0, 83, 188); // Dallas color

    public StatifyGUI() {
        super("Statify");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //  Create the "cards"
        JPanel homeCard = createHomePanel();
        JPanel statsCard = createStatsPanel();

        // Add them to main panel
        mainPanel.add(homeCard, "HOME");
        mainPanel.add(statsCard, "STATS");

        add(mainPanel);
    }

    // home screen is card 1
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(darkBg);

        JPanel buttonContainer = new JPanel(new GridLayout(3, 1, 10, 20)); // 3 rows, 1 col
        buttonContainer.setBackground(darkBg);

        JButton btnLeBron = createPlayerButton("LeBron James");
        JButton btnLuka   = createPlayerButton("Luka Doncic");
        JButton btnReaves = createPlayerButton("Austin Reaves");

        btnLeBron.addActionListener(e -> showPlayerStats("LeBron"));
        btnLuka.addActionListener(e -> showPlayerStats("Luka"));
        btnReaves.addActionListener(e -> showPlayerStats("Reaves"));

        buttonContainer.add(btnLeBron);
        buttonContainer.add(btnLuka);
        buttonContainer.add(btnReaves);

        panel.add(buttonContainer);
        return panel;
    }

    private JButton createPlayerButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(musePurple);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(250, 50));
        return btn;
    }

    // template for stats panel
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(darkBg);

        // header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(musePurple);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // back button to return to home
        JButton backBtn = new JButton("Back to Search");
        backBtn.setBackground(musePurple.darker());
        backBtn.setForeground(Color.WHITE);
        backBtn.setBorder(null);
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        // the labels for player name and team
        nameLabel = new JLabel("Player Name");
        nameLabel.setFont(new Font("Helvetica", Font.BOLD, 32));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        teamLabel = new JLabel(" Team Name ");
        teamLabel.setOpaque(true); // for background color
        teamLabel.setBackground(Color.GRAY);
        teamLabel.setForeground(Color.BLACK);
        teamLabel.setFont(new Font("Helvetica", Font.BOLD, 12));
        teamLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(backBtn);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(nameLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(teamLabel);

        //table for stats
        String[] columns = {"Season", "TM", "GP", "MIN", "PTS", "REB", "AST"};

        // default table model to add/remove rows
        tableModel = new DefaultTableModel(columns, 0);
        statsTable = new JTable(tableModel);

        styleTable(statsTable);

        JScrollPane scrollPane = new JScrollPane(statsTable);
        scrollPane.getViewport().setBackground(darkBg);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // data update method
    private void showPlayerStats(String playerKey) {
        // reset table
        tableModel.setRowCount(0);

        // load based on players name
        if (playerKey.equals("LeBron")) {
            nameLabel.setText("LeBron James");
            teamLabel.setText(" Los Angeles Lakers ");
            teamLabel.setBackground(lakersYellow);
            nameLabel.setForeground(lakersYellow);

            tableModel.addRow(new Object[]{"2003-04", "CLE", "79", "39.5", "20.9", "5.5", "5.9"});
            tableModel.addRow(new Object[]{"2015-16", "CLE", "76", "35.6", "25.3", "7.4", "6.8"});
            tableModel.addRow(new Object[]{"2023-24", "LAL", "71", "35.3", "25.7", "7.3", "8.3"});

        } else if (playerKey.equals("Luka")) {
            nameLabel.setText("Luka Doncic");
            teamLabel.setText(" Dallas Mavericks ");
            teamLabel.setBackground(mavsBlue);
            nameLabel.setForeground(Color.WHITE);

            tableModel.addRow(new Object[]{"2018-19", "DAL", "72", "32.2", "21.2", "7.8", "6.0"});
            tableModel.addRow(new Object[]{"2021-22", "DAL", "65", "35.4", "28.4", "9.1", "8.7"});
            tableModel.addRow(new Object[]{"2023-24", "DAL", "70", "37.5", "33.9", "9.2", "9.8"});

        } else if (playerKey.equals("Reaves")) {
            nameLabel.setText("Austin Reaves");
            teamLabel.setText(" Los Angeles Lakers ");
            teamLabel.setBackground(lakersYellow);
            nameLabel.setForeground(lakersYellow);

            tableModel.addRow(new Object[]{"2021-22", "LAL", "61", "23.2", "7.3", "3.2", "1.8"});
            tableModel.addRow(new Object[]{"2022-23", "LAL", "64", "28.8", "13.0", "3.0", "3.4"});
            tableModel.addRow(new Object[]{"2023-24", "LAL", "82", "32.1", "15.9", "4.3", "5.5"});
        }

        // to show the stats card
        cardLayout.show(mainPanel, "STATS");
    }

    private void styleTable(JTable table) {
        table.setBackground(darkBg);
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(50, 50, 50));
        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();
        header.setBackground(darkBg);
        header.setForeground(Color.GRAY);
        header.setFont(new Font("Helvetica", Font.BOLD, 12));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(darkBg);
        centerRenderer.setForeground(Color.WHITE);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}