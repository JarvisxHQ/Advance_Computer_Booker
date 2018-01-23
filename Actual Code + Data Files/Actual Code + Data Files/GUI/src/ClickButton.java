import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClickButton extends JFrame {
    private JButton btnClick, btnExit;

    public ClickButton() {
        //
        JLabel label = new JLabel("Hello JLabel");
        label.setOpaque(true);
        label.setBackground(Color.RED);
        label.setVisible(true);
        //label.addMouseListener();
        // Create button
        btnClick = new JButton("Click Me");
        btnClick.setMargin(new Insets(0,0,0,0));
        btnClick.setBorder(BorderFactory.createEmptyBorder());
        btnClick.setContentAreaFilled(false);
        btnClick.addActionListener(new ButtonListener());
        btnExit = new JButton("Exit");
        btnExit.addActionListener(new ButtonListener());
        // Create content panel
        JPanel panel = new JPanel();
        panel.add(btnClick);
        panel.add(btnExit);
        panel.add(label);

        // Set up frame
        addWindowListener(new CloseListener());
        setContentPane(panel);
        setSize(800, 600);
        setTitle("My First JPanel");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            btnExit.doClick();
        }
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnClick) {
                //JOptionPane.showMessageDialog(null, "It works!", "JButton", JOptionPane.INFORMATION_MESSAGE);
                int age = Integer.parseInt((String) JOptionPane.showInputDialog(null,"How old are you?", "Input Dialog", JOptionPane.QUESTION_MESSAGE));
                btnClick.setText(Integer.toString(age));
            } else if (e.getSource() == btnExit) {
                JOptionPane.showMessageDialog(null, "Thank you for using my program!", "Goodbye!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }
}
