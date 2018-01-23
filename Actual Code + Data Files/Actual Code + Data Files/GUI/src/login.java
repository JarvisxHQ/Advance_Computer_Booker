import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class login {
    JFrame frame = new JFrame("Welcome");
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JLabel putYourPass;
    private String code = "";
    private byte codeLength = (byte) code.length();
    private boolean access = false;
    private static String [] passes = new String[0];
    private static String [] names = new String[passes.length];
    private static String name;

    /**
     * Create the GUI for the user to input their ID
     * Checks the inputted ID with the user log and if any match is found
     * then the program proceeds else, the user is asked again
     */
    public login() {
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new
                    File("Roboto-Thin.ttf")));
        } catch (FontFormatException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader("UserName.txt"));

            String line = br.readLine();
            int i = 0;
            while (line != null) {
                System.out.println(line);
                if(line == null)
                    break;
                passes = Arrays.copyOf(passes, passes.length+1);
                names = Arrays.copyOf(names, names.length+1);
                passes[i] = line;
                names[i++]= br.readLine();
                line = br.readLine();
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        panel1 = new JPanel();

        passwordField1.setPreferredSize(new Dimension(100, 21));

        putYourPass = new JLabel("4-Digit Authentication Code:");
        putYourPass.setFont(new Font("Roboto Thin", Font.PLAIN, 30));
        putYourPass.setForeground(Color.WHITE);
        putYourPass.setHorizontalAlignment(JLabel.CENTER);
        putYourPass.setVerticalAlignment(JLabel.CENTER);
        putYourPass.setVisible(true);

        panel1.add(putYourPass);
        panel1.add(passwordField1);

        frame.setContentPane(panel1);
        frame.getContentPane().setBackground(GUI.dateGray);
        frame.setSize(500,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        passwordField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                codeLength++;
                code = code + e.getKeyChar();
                if (codeLength == 4) {
                    for(int i=0; i<passes.length; i++){
                        if(passes[i].equals(code)) {
                            access = true;
                            name = names[i];
                        }
                    }
                    if (access) {
                        code = "";
                        codeLength = (byte) code.length();
                        System.out.println(name);
                        frame.dispose();
                        new GUI();
                    }
                    else {
                        System.out.println("Reset");
                        System.out.println(code);
                        code = "";
                        codeLength = 0;
                        passwordField1.setText("");
                    }
                }
            }
        });
    }

    /**
     * Creates the login, starts the program
     * @param args
     */
    public static void main(String[] args) {
        new login();
    }

    /**
     * Returns the name of the person logged in
     * @return
     */
    public static String getName(){
        return name;
    }

    /**
     * Returns the number of accounts made so far
     * @return
     */
    public static int getPeople(){
        return passes.length;
    }
}
