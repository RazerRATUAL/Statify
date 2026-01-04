import statify.gui.AdminDashboard;
import statify.gui.UserDashboard;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main extends JFrame {
    private static Main mainWindow;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private final Color appBackground = Color.BLACK;
    private final Color headerBlue = new Color(0, 80, 200);
    private final Color accentYellow = new Color(255, 251, 0);
    private final Color buttonBg = new Color(40, 100, 200);
    private final Color buttonHover = new Color(0, 80, 200);

    public Main() {
        super("Statify - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(appBackground);

        mainPanel.add(createSelectorPanel(), "SELECTOR");
        mainPanel.add(createAdminWrapper(), "ADMIN");
        mainPanel.add(createUserWrapper(), "USER");

        add(mainPanel);
    }

    private JPanel createSelectorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(appBackground);

        // Header
        JPanel header = new JPanel();
        header.setBackground(headerBlue);
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel title = new JLabel("Statify");
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setForeground(accentYellow);
        header.add(title);

        panel.add(header);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons
        JButton adminBtn = createStyledButton("Admin Dashboard");
        JButton userBtn = createStyledButton("User Dashboard");

        adminBtn.addActionListener(e -> cardLayout.show(mainPanel, "ADMIN"));
        userBtn.addActionListener(e -> cardLayout.show(mainPanel, "USER"));

        panel.add(Box.createVerticalGlue());
        panel.add(adminBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(userBtn);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createAdminWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(appBackground);

        JButton backBtn = createBackButton();
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "SELECTOR"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(appBackground);
        backPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        backPanel.add(backBtn);

        AdminDashboard adminDash = new AdminDashboard();
        JPanel adminContent = adminDash.getContentPane().getComponents().length > 0 ?
                (JPanel) adminDash.getContentPane().getComponent(0) : new JPanel();

        wrapper.add(backPanel, BorderLayout.NORTH);
        wrapper.add(adminDash.getContentPane(), BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel createUserWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(appBackground);

        JButton backBtn = createBackButton();
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "SELECTOR"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(appBackground);
        backPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        backPanel.add(backBtn);

        UserDashboard userDash = new UserDashboard();
        JPanel userContent = userDash.getContentPane().getComponents().length > 0 ?
                (JPanel) userDash.getContentPane().getComponent(0) : new JPanel();

        wrapper.add(backPanel, BorderLayout.NORTH);
        wrapper.add(userDash.getContentPane(), BorderLayout.CENTER);

        return wrapper;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(buttonBg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(15, 40, 15, 40));
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(buttonHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(buttonBg);
            }
        });

        return btn;
    }

    private JButton createBackButton() {
        JButton btn = new JButton("← Back to Main Menu");
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setBackground(headerBlue);
        btn.setForeground(accentYellow);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(buttonHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(headerBlue);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mainWindow = new Main();
            mainWindow.setVisible(true);
        });
    }
}
