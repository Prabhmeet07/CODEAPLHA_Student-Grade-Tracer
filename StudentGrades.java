import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class StudentGrades {

    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<String> rollNos = new ArrayList<>();
    static ArrayList<Integer> marks = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Grade Tracker");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 8));

        ImageIcon img = new ImageIcon("s.jpg");
        JLabel label = new JLabel("STUDENT GRADE TRACER", img, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addButton = new JButton("➕ Add Student Details");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setBackground(new Color(210, 180, 140));
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.addActionListener(e -> openAddDetailsWindow());

        frame.add(Box.createVerticalStrut(10));
        frame.add(label);
        frame.add(Box.createVerticalStrut(20));
        frame.add(addButton);
        frame.setVisible(true);
    }

    public static void openAddDetailsWindow() {
        JFrame inputFrame = new JFrame("Add Student Details");
        inputFrame.setSize(500, 400);
        inputFrame.setLayout(new BoxLayout(inputFrame.getContentPane(), BoxLayout.Y_AXIS));
        inputFrame.setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();
        JTextField marksField = new JTextField();

        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Roll Number:"));
        formPanel.add(rollField);
        formPanel.add(new JLabel("Marks (0–100):"));
        formPanel.add(marksField);
        formPanel.setBorder(BorderFactory.createTitledBorder("Enter Details"));

        JTextArea displayArea = new JTextArea(7, 35);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JButton saveButton = new JButton("✅ Save");
        JButton doneButton = new JButton("📊 Done / Show Summary");
        JLabel statusLabel = new JLabel("");

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String markStr = marksField.getText().trim();

            if (name.isEmpty() || roll.isEmpty() || markStr.isEmpty()) {
                statusLabel.setText("⚠️ All fields are required.");
                return;
            }

            try {
                int mark = Integer.parseInt(markStr);
                if (mark < 0 || mark > 100) {
                    statusLabel.setText("❌ Marks should be between 0 and 100.");
                    return;
                }

                names.add(name);
                rollNos.add(roll);
                marks.add(mark);

                displayArea.append((names.size()) + ") " + name + " | Roll: " + roll + " | Marks: " + mark + "\n");

                nameField.setText("");
                rollField.setText("");
                marksField.setText("");
                statusLabel.setText("✅ Student added.");
            } catch (NumberFormatException ex) {
                statusLabel.setText("❗ Please enter a valid number for marks.");
            }
        });

        doneButton.addActionListener(e -> showSummaryWindow());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(doneButton);

        inputFrame.add(formPanel);
        inputFrame.add(buttonPanel);
        inputFrame.add(statusLabel);
        inputFrame.add(scrollPane);
        inputFrame.setVisible(true);
    }

    public static void showSummaryWindow() {
        JFrame summaryFrame = new JFrame("Student Summary Report");
        summaryFrame.setSize(500, 500);
        summaryFrame.setLayout(new BorderLayout());
        summaryFrame.setLocationRelativeTo(null);

        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(summaryArea);

        StringBuilder sb = new StringBuilder();
        sb.append("STUDENT SUMMARY\n\n");

        int total = 0, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        for (int i = 0; i < names.size(); i++) {
            int m = marks.get(i);
            total += m;
            if (m < min) min = m;
            if (m > max) max = m;
            String grade = getGrade(m);
            sb.append((i + 1) + ") " + names.get(i) + " | Roll: " + rollNos.get(i)
                    + " | Marks: " + m + " | Grade: " + grade + "\n");
        }

        double avg = names.size() > 0 ? (double) total / names.size() : 0;
        sb.append("\n-------------------------\n");
        sb.append("Average Marks: " + String.format("%.2f", avg) + "\n");
        sb.append("Highest Marks: " + max + "\n");
        sb.append("Lowest Marks : " + min + "\n");

        summaryArea.setText(sb.toString());
        summaryFrame.add(scroll, BorderLayout.CENTER);
        summaryFrame.setVisible(true);
    }

    public static String getGrade(int marks) {
        if (marks >= 90) return "O";
        else if (marks >= 80) return "A+";
        else if (marks >= 70) return "A";
        else if (marks >= 60) return "B+";
        else if (marks >= 50) return "B";
        else if (marks >= 40) return "C";
        else return "F";
    }
}
