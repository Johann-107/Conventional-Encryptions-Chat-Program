package ui;

import cipher.Cipher;
import cipher.CaesarCipher;
import cipher.VigenereCipher;
import cipher.RailFenceCipher;
import model.ChatMessage;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends JFrame {

    private static final Color BG_DARK      = new Color(10, 14, 26);
    private static final Color BG_PANEL     = new Color(13, 21, 32);
    private static final Color BG_INPUT     = new Color(6, 13, 18);
    private static final Color GREEN_BRIGHT = new Color(57, 255, 106);
    private static final Color GREEN_DIM    = new Color(90, 138, 90);
    private static final Color ORANGE_ENC   = new Color(255, 170, 68);
    private static final Color BLUE_DEC     = new Color(68, 170, 255);
    private static final Color BORDER_COLOR = new Color(26, 58, 42);

    private static final Font MONO_FONT  = new Font("Monospaced", Font.PLAIN, 13);
    private static final Font LABEL_FONT = new Font("Monospaced", Font.PLAIN, 11);
    private static final Font TITLE_FONT = new Font("Monospaced", Font.BOLD, 16);

    private final Cipher[] ciphers = {
        new CaesarCipher(),
        new VigenereCipher(),
        new RailFenceCipher()
    };
    private Cipher selectedCipher = ciphers[0];

    private JTextArea chatArea;
    private JTextField keyField;
    private JTextField senderField;
    private JTextArea messageArea;
    private JLabel encResultLabel;
    private JLabel decResultLabel;
    private JButton[] cipherButtons;

    private final List<ChatMessage> chatHistory = new ArrayList<>();

    public ChatWindow() {
        setTitle("Conventional Encryption Chat Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setMinimumSize(new Dimension(750, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(0, 0));

        add(buildTitleBar(), BorderLayout.NORTH);
        add(buildMainPanel(), BorderLayout.CENTER);

        keyField.setText("3");
        senderField.setText("User A");
        highlightCipherButton(0);

        setVisible(true);
    }

    private JPanel buildTitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_DARK);
        bar.setBorder(new EmptyBorder(12, 20, 10, 20));

        JLabel title = new JLabel("// CONVENTIONAL ENCRYPTION CHAT PROGRAM //");
        title.setFont(TITLE_FONT);
        title.setForeground(GREEN_BRIGHT);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        bar.add(title, BorderLayout.CENTER);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        bar.add(sep, BorderLayout.SOUTH);

        return bar;
    }

    private JSplitPane buildMainPanel() {
        JSplitPane split = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            buildLeftPanel(),
            buildRightPanel()
        );
        split.setDividerLocation(290);
        split.setDividerSize(4);
        split.setBorder(null);
        split.setBackground(BORDER_COLOR);
        return split;
    }

    private JPanel buildLeftPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BG_PANEL);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 0, 1, BORDER_COLOR),
            new EmptyBorder(14, 14, 14, 14)
        ));

        panel.add(makeSectionLabel("> CONFIG"));
        panel.add(Box.createVerticalStrut(10));

        panel.add(makeSmallLabel("CIPHER ALGORITHM"));
        panel.add(Box.createVerticalStrut(5));
        cipherButtons = new JButton[ciphers.length];
        String[] nums = {"[1]", "[2]", "[3]", "[4]"};
        for (int i = 0; i < ciphers.length; i++) {
            final int idx = i;
            JButton btn = makeCipherButton(nums[i] + " " + ciphers[i].getName());
            btn.addActionListener(e -> {
                selectedCipher = ciphers[idx];
                highlightCipherButton(idx);
                updateKeyHint(idx);
                clearResults();
            });
            cipherButtons[i] = btn;
            panel.add(btn);
            panel.add(Box.createVerticalStrut(4));
        }

        panel.add(Box.createVerticalStrut(10));

        panel.add(makeSmallLabel("ENCRYPTION KEY"));
        keyField = makeTextField("e.g. 3");
        panel.add(keyField);
        panel.add(Box.createVerticalStrut(6));

        panel.add(makeSmallLabel("SENDER NAME"));
        senderField = makeTextField("e.g. User A");
        panel.add(senderField);
        panel.add(Box.createVerticalStrut(6));

        panel.add(makeSmallLabel("MESSAGE"));
        messageArea = new JTextArea(3, 20);
        messageArea.setFont(MONO_FONT);
        messageArea.setBackground(BG_INPUT);
        messageArea.setForeground(new Color(200, 255, 200));
        messageArea.setCaretColor(GREEN_BRIGHT);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR),
            new EmptyBorder(5, 8, 5, 8)
        ));
        JScrollPane msgScroll = new JScrollPane(messageArea);
        msgScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        msgScroll.setBorder(null);
        panel.add(msgScroll);
        panel.add(Box.createVerticalStrut(10));

        JButton sendBtn = new JButton("▶  ENCRYPT & SEND");
        sendBtn.setFont(new Font("Monospaced", Font.BOLD, 12));
        sendBtn.setBackground(new Color(10, 58, 26));
        sendBtn.setForeground(GREEN_BRIGHT);
        sendBtn.setBorder(new CompoundBorder(
            new LineBorder(GREEN_BRIGHT),
            new EmptyBorder(8, 12, 8, 12)
        ));
        sendBtn.setFocusPainted(false);
        sendBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        sendBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        sendBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sendBtn.addActionListener(e -> handleSend());
        sendBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { sendBtn.setBackground(new Color(13, 90, 34)); }
            public void mouseExited(MouseEvent e)  { sendBtn.setBackground(new Color(10, 58, 26)); }
        });
        panel.add(sendBtn);

        panel.add(Box.createVerticalStrut(12));
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(10));

        panel.add(makeSmallLabel("CIPHERTEXT"));
        encResultLabel = makeResultLabel(ORANGE_ENC, "—");
        panel.add(encResultLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(makeSmallLabel("DECRYPTED"));
        decResultLabel = makeResultLabel(BLUE_DEC, "—");
        panel.add(decResultLabel);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(BG_PANEL);
        panel.setBorder(new EmptyBorder(14, 14, 14, 14));

        panel.add(makeSectionLabel("> CHAT HISTORY"), BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setFont(MONO_FONT);
        chatArea.setBackground(BG_INPUT);
        chatArea.setForeground(new Color(200, 255, 200));
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBorder(new EmptyBorder(10, 12, 10, 12));
        chatArea.setText("  [ No messages yet ]\n  Choose a cipher and send a message.\n");
        chatArea.setCaretColor(GREEN_BRIGHT);

        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setBorder(new LineBorder(BORDER_COLOR));
        scroll.getVerticalScrollBar().setBackground(BG_INPUT);
        panel.add(scroll, BorderLayout.CENTER);

        JButton clearBtn = new JButton("Clear History");
        clearBtn.setFont(LABEL_FONT);
        clearBtn.setBackground(BG_PANEL);
        clearBtn.setForeground(GREEN_DIM);
        clearBtn.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR),
            new EmptyBorder(5, 12, 5, 12)
        ));
        clearBtn.setFocusPainted(false);
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(e -> clearHistory());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottom.setBackground(BG_PANEL);
        bottom.add(clearBtn);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void handleSend() {
        String message = messageArea.getText().trim();
        String key     = keyField.getText().trim();
        String sender  = senderField.getText().trim();

        if (message.isEmpty()) {
            showError("Please enter a message.");
            return;
        }
        if (key.isEmpty()) {
            showError("Please enter an encryption key.");
            return;
        }
        if (sender.isEmpty()) sender = "User A";

        if ((selectedCipher instanceof CaesarCipher ||
             selectedCipher instanceof RailFenceCipher)) {
            try {
                Integer.parseInt(key);
            } catch (NumberFormatException ex) {
                showError(selectedCipher.getName() + " requires a numeric key.");
                return;
            }
        }

        try {
            String ciphertext = selectedCipher.encrypt(message, key);
            String decrypted  = selectedCipher.decrypt(ciphertext, key);
            String receiver   = sender.equalsIgnoreCase("User A") ? "User B" : "User A";

            ChatMessage msg = new ChatMessage(
                sender, receiver, message,
                ciphertext, decrypted,
                selectedCipher.getName(), key
            );

            chatHistory.add(msg);
            appendToChat(msg);

            encResultLabel.setText("<html><body style='width:200px;color:#FFAA44'>"
                + ciphertext + "</body></html>");
            decResultLabel.setText("<html><body style='width:200px;color:#44AAFF'>"
                + decrypted + "</body></html>");

            messageArea.setText("");

        } catch (Exception ex) {
            showError("Encryption error: " + ex.getMessage());
        }
    }

    private void appendToChat(ChatMessage msg) {
        if (chatHistory.size() == 1) {
            chatArea.setText("");
        }
        chatArea.append(msg.toDisplayString());
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void clearHistory() {
        chatHistory.clear();
        chatArea.setText("  [ No messages yet ]\n  Choose a cipher and send a message.\n");
        clearResults();
    }

    private void clearResults() {
        encResultLabel.setText("—");
        decResultLabel.setText("—");
    }

    private void highlightCipherButton(int activeIdx) {
        for (int i = 0; i < cipherButtons.length; i++) {
            if (i == activeIdx) {
                cipherButtons[i].setBackground(new Color(50, 28, 5));
                cipherButtons[i].setForeground(new Color(255, 160, 40));
                cipherButtons[i].setBorder(new CompoundBorder(
                    new LineBorder(new Color(255, 160, 40)),
                    new EmptyBorder(6, 10, 6, 10)
                ));
            } else {
                cipherButtons[i].setBackground(new Color(30, 18, 8));
                cipherButtons[i].setForeground(new Color(180, 100, 30));
                cipherButtons[i].setBorder(new CompoundBorder(
                    new LineBorder(new Color(80, 45, 10)),
                    new EmptyBorder(6, 10, 6, 10)
                ));
            }
        }
    }

    private void updateKeyHint(int idx) {
        String[] defaults = {"3", "KEY", "3", "MONARCHY"};
        keyField.setText(defaults[idx]);
    }

    private JLabel makeSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Monospaced", Font.BOLD, 11));
        lbl.setForeground(GREEN_BRIGHT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JLabel makeSmallLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(GREEN_DIM);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField makeTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(MONO_FONT);
        field.setBackground(BG_INPUT);
        field.setForeground(new Color(200, 255, 200));
        field.setCaretColor(GREEN_BRIGHT);
        field.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR),
            new EmptyBorder(5, 8, 5, 8)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JButton makeCipherButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(MONO_FONT);
        btn.setBackground(new Color(30, 18, 8));
        btn.setForeground(new Color(180, 100, 30));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(
            new LineBorder(new Color(80, 45, 10)),
            new EmptyBorder(6, 10, 6, 10)
        ));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel makeResultLabel(Color color, String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(MONO_FONT);
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR),
            new EmptyBorder(5, 8, 5, 8)
        ));
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        lbl.setOpaque(true);
        lbl.setBackground(BG_INPUT);
        return lbl;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error",
            JOptionPane.ERROR_MESSAGE);
    }
}