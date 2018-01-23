import javax.swing.*;

/**
 * Created by Bat on 26/05/2017.
 */
public class Booker
{
        private JPanel panel;
        private JPanel sidePane;
        private JFrame frame1;

        public Booker() {
                panel = new JPanel();
                frame1 = new JFrame();
                frame1.setContentPane(panel);
                frame1.setTitle("Booker");
                frame1.getContentPane().setBackground(GUI.dateGray);
                frame1.setSize(600, 150);
                //pack();
                frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                frame1.setLocationRe
                frame1.setVisible(true);
                System.out.println("What u wanna do?");
        }
}