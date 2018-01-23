import jdk.nashorn.internal.scripts.JO;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class GUI extends JPanel {
    private JPanel panel;
//    private JPanel sidePane;
    private JLabel[] sideBols;
    private JLabel[][] labLabel = new JLabel[31][3];
    private JLabel cDate;
    public JFrame frame;
    public static final Color topBlew = new Color(20, 20, 20);
    public static final Color blew = new Color(0, 255, 255);
    public static final Color dateGray = new Color(30, 30, 30);
    public static final Color panelGray = new Color(25, 25, 25);
    public static Font fnt = new Font("Roboto Thin", Font.PLAIN, 33);
    public static Font nameFnt = new Font("Roboto Thin", Font.PLAIN, 20);
    public static Font actFnt = new Font("Roboto Thin", Font.PLAIN, 16);
    public static Font dateFnt = new Font("Roboto Thin", Font.PLAIN, 20);

    public String[] details;
    public String[] details2;
    public String[][][][] database = new String[31][7][3][login.getPeople()];
    public static String [][] review = new String[3][3];
    public String ref = "";
    public int action = 1;
    public int startingDate = 1;
    public int endingDate = 1;
    public int timeChosen = 0;
    public int device = 1;
    public int deviceS = 1;
    public boolean sPriority;

    /**
     * Creates the GUI object.
     * @param args
     */
    public static void main(String[] args) {
        new GUI();
    }

    /**
     * Creates, initializes and adds the components
     *
     */
    public GUI() {


        //Initializes the main GUI components
        //Creates and sets the layout for the main calendar GUI
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;

        //Creates and initializes the labels that will be displayed on the GUI
        JLabel label1 = new JLabel(login.getName());
        JLabel label2 = new JLabel("History Pane");
        JLabel dateLabel = new JLabel("March 1, 3069");
        JPanel[] days = new JPanel[31]; // one panel for each day of the month
        JLabel[] dates = new JLabel[31]; // one label for every day on the month

        //The basic theme font and color settings for the program
        label1.setBackground(topBlew);
        label1.setForeground(Color.WHITE);
        label1.setOpaque(true);
        dateLabel.setBackground(topBlew);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setOpaque(true);
        label2.setBackground(panelGray);
        label2.setForeground(Color.WHITE);
        label2.setOpaque(true);

        //Creates the 3-bar menu button
        ImageIcon alter = new ImageIcon("menu.png");
        JLabel menu = new JLabel(alter);

        //The Info Bar
        Dimension d1 = new Dimension(-1, 30);
        label1.setPreferredSize(d1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        label1.setForeground(new Color(0, 162, 232));
        label1.setFont(nameFnt);
        panel.add(label1, c);

        //The Date
        dateLabel.setPreferredSize(d1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 100;
        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        dateLabel.setFont(dateFnt);
        dateLabel.setHorizontalAlignment(JLabel.TRAILING);
        panel.add(dateLabel, c);

        //The History side panel
        Dimension d2 = new Dimension(250, -1);
        JPanel sidePane = new JPanel();
        sidePane.setLayout(new BoxLayout(sidePane, BoxLayout.Y_AXIS));
        sidePane.setPreferredSize(d2);
        sidePane.setBackground(panelGray);
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0;
        c.weighty = 100;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 6;
        c.insets = new Insets(0, 0, 0, 10);
        panel.add(sidePane, c);

        //History's date
        cDate = new JLabel(" ");
        cDate.setForeground(new Color(8, 232, 222));
        cDate.setFont(dateFnt);
        sidePane.add(cDate);

        //Add labels that will display the agenda for the day
        sideBols = new JLabel[60];
        for (int bols = 0; bols < sideBols.length; bols++) {
            sideBols[bols] = new JLabel("");
            sideBols[bols].setForeground(new Color(200, 200, 200));
            sideBols[bols].setFont(actFnt);
            sidePane.add(sideBols[bols]);
        }

        //<editor-fold desc="Weekdays">
        //Add the weekdays, (Saturday, Sunday, etc.)
        Dimension d4 = new Dimension(50, 35);
        fnt = new Font("Roboto Thin", Font.PLAIN, 22);
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 100;
        c.weighty = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        for(int i=1; i<=7; i++) {
            JLabel wkd;
            if(i==1) wkd = new JLabel("SUN");
            else if(i==2) wkd = new JLabel("MON");
            else if(i==3) wkd = new JLabel("TUES");
            else if(i==4) wkd = new JLabel("WED");
            else if(i==5) wkd = new JLabel("THURS");
            else if(i==6) wkd = new JLabel("FRI");
            else wkd = new JLabel("SAT");
            dayLabel(wkd, d4);
            c.gridx = i;
            panel.add(wkd, c);
        }
        //</editor-fold>

        //<editor-fold desc="Add the days to the Calendar Panel">
        //Add the days to the Calendar Panel
        Dimension d3 = new Dimension(50, 50);
        int d = 0;
        fnt = new Font("Roboto Thin", Font.PLAIN, 25);
        c.weightx = 100;
        c.weighty = 100;
        for (int week = 2; week <= 6; week++) {

            c.gridy = week;
            for (int i = 1; i <= 7; i++) {
                if (d == days.length)
                    break;
                days[d] = new JPanel();
                days[d].setLayout(new BoxLayout(days[d], BoxLayout.Y_AXIS));
//                days[d].setPreferredSize(new Dimension(50,50));
                dates[d] = new JLabel(Integer.toString(d + 1));
                days[d].setPreferredSize(d3);
                days[d].setOpaque(true);
                days[d].setBackground(dateGray);
                dates[d].setForeground(blew);
                dates[d].setFont(fnt);
                dates[d].setHorizontalAlignment(JLabel.LEADING);
                dates[d].setVerticalAlignment(JLabel.TOP);
                dates[d].setVisible(true);

                c.gridx = i;
                days[d].add(dates[d]);

                for (int colorNum = 0; colorNum < 3; colorNum++) {
                    labLabel[d][colorNum] = new JLabel("                             ");
                    labLabel[d][colorNum].setOpaque(true);
                    labLabel[d][colorNum].setBackground(dateGray);//new Color(80-colorNum*10,80-colorNum*10,80-colorNum*10));
                    days[d].add(labLabel[d][colorNum]);
                }
                panel.add(days[d++], c);
            }
        }
        //</editor-fold>

        //Add the 3-bar menu button to the mai panel
        c.gridx = 7;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        panel.add(menu, c);

        //Set the properties of the main frame
        frame = new JFrame();
        frame.setContentPane(panel);
        frame.setTitle("The Last Last Project");
        frame.getContentPane().setBackground(dateGray);
        frame.setSize(1200, 675);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Read the data file and add the bars to the calendar if needed
        readFile();
        drawBols();

        //<editor-fold desc="Hovering Mechanism">
        //Adds a mouse listener for every day of the month which listens for the mouse to hover over it
        for (int day = 0; day < days.length; day++) {
            int finalDay = day;
            days[day].addMouseListener(new MouseAdapter() {
                /**
                 * Detects the hover event of a mouse and changes the information
                 * displayed on the history side panel according to the day
                 * over which the mouse is hovering
                 * @param e
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    readFile();
                    int labelCount = 0;
                    int q1 = -1;
                    String dayOfWeek = "";
                    if (finalDay % 7 == 0) {
                        dayOfWeek = "SUNDAY";
                    }
                    if (finalDay % 7 == 1) {
                        dayOfWeek = "MONDAY";
                    }
                    if (finalDay % 7 == 2) {
                        dayOfWeek = "TUESDAY";
                    }
                    if (finalDay % 7 == 3) {
                        dayOfWeek = "WEDNESDAY";
                    }
                    if (finalDay % 7 == 4) {
                        dayOfWeek = "THURSDAY";
                    }
                    if (finalDay % 7 == 5) {
                        dayOfWeek = "FRIDAY";
                    }
                    if (finalDay % 7 == 6) {
                        dayOfWeek = "SATURDAY";
                    }
                    dayOfWeek = dayOfWeek + ", MARCH " + (finalDay + 1);
                    cDate.setText(dayOfWeek);
                    for (int q = 0; q < database[finalDay].length; q++) {
                        for (int x = 0; x < database[finalDay][q].length; x++) {
                            for (int y = 0; y < database[finalDay][q][x].length; y++) {
                                if (database[finalDay][q][x][y] != null) {
                                    if (q != q1) {
                                        if (q != 0 || q1 == -1)
                                            sideBols[labelCount++].setText(" ");
                                        if (q == 0) {
                                            sideBols[labelCount].setText("8:30 AM");
                                        }
                                        if (q == 1) {
                                            sideBols[labelCount].setText("9:30 AM");
                                        }
                                        if (q == 2) {
                                            sideBols[labelCount].setText("10:30 AM");
                                        }
                                        if (q == 3) {
                                            sideBols[labelCount].setText("12:30 PM");
                                        }
                                        if (q == 4) {
                                            sideBols[labelCount].setText("1:30 PM");
                                        }
                                        if (q == 5) {
                                            sideBols[labelCount].setText("2:30 PM");
                                        }
                                        labelCount++;
                                        q1 = q;
                                    }
                                    String deviceName;
                                    if (x == 0) {
                                        if (y == 0)
                                            deviceName = "Windows";
                                        else if (y == 1)
                                            deviceName = "iMac Pro";
                                        else
                                            deviceName = "Asus ROG";
                                    } else if (x == 1) {
                                        if (y == 0)
                                            deviceName = "iPads";
                                        else if (y == 1)
                                            deviceName = "Surface";
                                        else
                                            deviceName = "PixelC";
                                    } else {
                                        if (y == 0)
                                            deviceName = "MacBook Air";
                                        else if (y == 1)
                                            deviceName = "Dell XPS";
                                        else
                                            deviceName = "Alienware";
                                    }
                                    String textToWrite = "\t~ " + database[finalDay][q][x][y] + ", " + deviceName;
                                    sideBols[labelCount].setText(textToWrite);
                                    labelCount++;
                                }
                            }
                        }
                    }

//                    System.out.println("Don't Leave!!!");
                }
            });
            days[day].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    for (int bols = 0; bols < sideBols.length; bols++) {
                        sideBols[bols].setText("");
                    }
//                    System.out.println("Told you not leave!");
                    //frame.dispose();
                    //System.exit(0);
                }
            });
        }
        //</editor-fold>

        menu.addMouseListener(new MouseAdapter() {
            /**
             * Calls the bookings function when the menu button is clicked
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                launchMenu();
            }
        });

    }

    /**
     * It displays the main menu
     * Shows 3 buttons which all three lead to three different actions (add, modify and review)
     */
    public void launchMenu() {
        JPanel panel1;
        JFrame frame1;
        panel1 = new JPanel();
        frame1 = new JFrame();

        ImageIcon plus = new ImageIcon("create.png");
        ImageIcon mod = new ImageIcon("modify.png");
        ImageIcon info = new ImageIcon("review.png");

        JLabel create = new JLabel(plus);
        panel1.add(create);
        panel1.add(Box.createHorizontalStrut(80));

        JLabel modify = new JLabel(mod);
        panel1.add(modify);
        panel1.add(Box.createHorizontalStrut(80));

        JLabel review = new JLabel(info);
        panel1.add(review);
        panel1.add(Box.createHorizontalStrut(0));

        frame1.setContentPane(panel1);
        frame1.setTitle("Booker");
        frame1.getContentPane().setBackground(GUI.dateGray);
        frame1.setSize(600, 150);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setLocation(frame.getX() + frame.getWidth() - frame1.getWidth(), frame.getY() + frame.getHeight() - frame1.getHeight());
        frame1.setVisible(true);

        create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                frame1.dispose();
                action = 1;
                addNewBooking();
            }
        });
        modify.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                frame1.dispose();
                action = 2;
                modifyOld();
            }
        });
        review.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                frame1.dispose();
                readReview();
            }
        });
    }

    /**
     * This method is called to add a new booking while ensuring proper data
     * is inputed and data that is not in proper format is filtered out
     * Displays a GUI that asks the user to input date and priority data
     * Once valid information is gathered, it calls the askForTime method
     */
    public void addNewBooking() {
        JPanel panel1;
        JFrame frame1;
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints split = new GridBagConstraints();
        split.anchor = GridBagConstraints.NORTHWEST;
        frame1 = new JFrame();

        fnt = new Font("Roboto Thin", Font.PLAIN, 25);

        JLabel question0 = new JLabel("START DATE:");
        split.gridx = 0;
        split.gridy = 0;
        split.gridwidth = 1;
        split.gridheight = 1;
        split.fill = GridBagConstraints.HORIZONTAL;
        formatLabel(question0, Color.WHITE, fnt);
        panel1.add(question0, split);

        MaskFormatter dateFormat;
        JFormattedTextField startDate = null;
        try {
            dateFormat = new MaskFormatter("##");
            startDate = new JFormattedTextField(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        split.gridx = 1;
        split.gridy = 0;
        startDate.setPreferredSize(new Dimension(50, 24));
        panel1.add(startDate, split);
        JFormattedTextField finalStartDate = startDate;

        JLabel question1 = new JLabel("END DATE:");
        split.gridx = 0;
        split.gridy = 1;
        split.fill = GridBagConstraints.HORIZONTAL;
        formatLabel(question1, Color.WHITE, fnt);
        panel1.add(question1, split);

        JFormattedTextField endDate = null;
        try {
            dateFormat = new MaskFormatter("##");
            endDate = new JFormattedTextField(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        split.gridx = 1;
        split.gridy = 1;
        endDate.setPreferredSize(new Dimension(50, 24));
        panel1.add(endDate, split);
        JFormattedTextField finalEndDate = endDate;

        JLabel question2 = new JLabel("SCHOOL PRIORITY:");
        split.gridx = 0;
        split.gridy = 2;
        split.fill = GridBagConstraints.HORIZONTAL;
        formatLabel(question2, Color.WHITE, fnt);
        panel1.add(question2, split);

        JButton yes = new JButton("YES");
        split.gridx = 1;
        split.gridy = 2;
        booleanButton(yes);
        panel1.add(yes, split);
        yes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //turn red
                yes.setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //go back to original state
                yes.setBackground(dateGray);
                save(true, finalStartDate, finalEndDate);
                askForTime(panel1, frame1);
            }
        });

        JButton no = new JButton("NO");
        split.gridx = 2;
        split.gridy = 2;
        booleanButton(no);
        panel1.add(no, split);
        no.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //turn red
                no.setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //go back to original state
                no.setBackground(dateGray);
                save(false, finalStartDate, finalEndDate);
                askForTime(panel1, frame1);
            }

        });

        frame1.setContentPane(panel1);
        frame1.setTitle("Booker");
        frame1.getContentPane().setBackground(GUI.dateGray);
        frame1.setSize(600, 400);
        //pack();
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
    }

    /**
     * This method is called to delete old bookings
     * It gets the date of the booking wanted to be deleted and
     * calls the askForTIme method to find the time for the booking
     */
    public void modifyOld() {
        JPanel panel1;
        JFrame frame1;
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints split = new GridBagConstraints();
        split.anchor = GridBagConstraints.NORTHWEST;
        frame1 = new JFrame();

        fnt = new Font("Roboto Thin", Font.PLAIN, 25);

        JLabel question0 = new JLabel("DATE:");
        split.gridx = 0;
        split.gridy = 0;
        split.gridwidth = 1;
        split.gridheight = 1;
        split.fill = GridBagConstraints.HORIZONTAL;
        formatLabel(question0, Color.WHITE, fnt);
        panel1.add(question0, split);

        MaskFormatter dateFormat;
        JFormattedTextField startDate = null;
        try {
            dateFormat = new MaskFormatter("##");
            startDate = new JFormattedTextField(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        split.gridx = 1;
        split.gridy = 0;
        startDate.setPreferredSize(new Dimension(50, 24));
        panel1.add(startDate, split);
        JFormattedTextField finalStartDate = startDate;

        JButton next = new JButton("NEXT");
        split.gridx = 2;
        split.gridy = 2;
        booleanButton(next);
        panel1.add(next, split);
        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //turn red
                next.setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //go back to original state
                next.setBackground(dateGray);
                startingDate = Integer.parseInt(finalStartDate.getText());
                askForTime(panel1, frame1);
            }

        });

        frame1.setContentPane(panel1);
        frame1.setTitle("Booker");
        frame1.getContentPane().setBackground(GUI.dateGray);
        frame1.setSize(600, 400);
        //pack();
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
    }

    /**
     * Overloaded method required for the modification feature
     * Calls the askForTime method with the given JPanel and JFrame
     * The adding is assigned a default value of false
     * False indicates that is is a new booking and not a
     * modifcation of an old booking
     * @param panel1
     * @param frame1
     */
    private void askForTime(JPanel panel1, JFrame frame1) {
        askForTime(panel1, frame1, false);
    }

    /**
     * Displays a set of radio buttons
     * Each button has a unique time slot on it
     * Once a button is pressed, it calls the askForDeviceType method
     * @param panel1
     * @param frame1
     * @param adding
     */
    private void askForTime(JPanel panel1, JFrame frame1, boolean adding) {
        panel1.removeAll();
        panel1.revalidate();
        panel1.repaint();

        GridBagConstraints split = new GridBagConstraints();


        JLabel question0 = new JLabel("Choose your preferred timing:");
        split.gridx = 0;
        split.gridy = 0;
        split.gridwidth = 1;
        split.gridheight = 1;
        split.fill = GridBagConstraints.HORIZONTAL;
        formatLabel(question0, Color.WHITE, fnt);
        panel1.add(question0, split);

        //<editor-fold desc="Time Options">
        JRadioButton[] times = new JRadioButton[6];
        times[0] = new JRadioButton("8:30 AM to 9:30 AM");
        split.gridx = 0;
        split.gridy = 1;
        radioFormat(times[0]);
        panel1.add(times[0], split);

        times[1] = new JRadioButton("9:30 AM to 10:30 AM");
        split.gridx = 0;
        split.gridy = 2;
        radioFormat(times[1]);
        panel1.add(times[1], split);

        times[2] = new JRadioButton("10:30 AM to 11:30 AM");
        split.gridx = 0;
        split.gridy = 3;
        radioFormat(times[2]);
        panel1.add(times[2], split);

        times[3] = new JRadioButton("12:30 PM to 1:30 PM");
        split.gridx = 0;
        split.gridy = 4;
        radioFormat(times[3]);
        panel1.add(times[3], split);

        times[4] = new JRadioButton("1:30 PM to 2:30 PM");
        split.gridx = 0;
        split.gridy = 5;
        radioFormat(times[4]);
        panel1.add(times[4], split);

        times[5] = new JRadioButton("2:30 PM to 3:30 PM");
        split.gridx = 0;
        split.gridy = 6;
        radioFormat(times[5]);
        panel1.add(times[5], split);

        for (int timer = 0; timer < 6; timer++)
            times[timer].setFont(new Font("Roboto Thin", Font.PLAIN, 20));
        //</editor-fold>

        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < times.length; i++) {
            group.add(times[i]);
            int finalI = i;
            times[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeChosen = finalI;
                    panel1.removeAll();
                    panel1.revalidate();
                    panel1.repaint();
                    if (adding) {
                        addBooking();
                        frame1.dispose();
                    } else askForDeviceType(panel1, frame1);
                }
            });

        }

    }


    /**
     * Overloaded method required for the modification feature
     * Calls the askForDeviceType method with the given JPanel and JFrame
     * The adding is assigned a default value of false
     * False indicates that is is a new booking and not a
     * modifcation of an old booking
     * @param panel1
     * @param frame1
     */
    private void askForDeviceType(JPanel panel1, JFrame frame1) {
        askForDeviceType(panel1, frame1, true);
    }

    /**
     * This method asks the user to select one of
     * the three given categories
     * Once selected, it proceeds further and calls askPurpose method
     * @param panel1
     * @param frame1
     * @param adding
     */
    private void askForDeviceType(JPanel panel1, JFrame frame1, boolean adding) {
        GridBagConstraints split = new GridBagConstraints();
        split.weightx = 1;
        split.fill = GridBagConstraints.BOTH;

        JLabel question0 = new JLabel("Choose the type of device you want:");
        split.gridx = 0;
        split.gridy = 0;
        split.gridwidth = 1;
        split.gridheight = 1;
        formatLabel(question0, Color.WHITE, fnt);
        panel1.add(question0, split);

        JButton[] cat = new JButton[3];

        cat[0] = new JButton("COMPUTER LAB");
        split.weighty = 1;
        split.gridx = 0;
        split.gridy = 1;
        booleanButton(cat[0]);
        panel1.add(cat[0], split);
        cat[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cat[0].setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cat[0].setBackground(dateGray);
                device = 0;
                askPurpose(panel1, frame1, adding);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cat[0].setBackground(panelGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cat[0].setBackground(dateGray);
            }
        });

        cat[1] = new JButton("TABLET CART");
        split.gridx = 0;
        split.gridy = 2;
        booleanButton(cat[1]);
        panel1.add(cat[1], split);
        cat[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cat[1].setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cat[1].setBackground(dateGray);
                device = 1;
                askPurpose(panel1, frame1, adding);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cat[1].setBackground(panelGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cat[1].setBackground(dateGray);
            }
        });

        cat[2] = new JButton("LAPTOPS");
        split.gridx = 0;
        split.gridy = 3;
        booleanButton(cat[2]);
        panel1.add(cat[2], split);
        cat[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cat[2].setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cat[2].setBackground(dateGray);
                device = 2;
                askPurpose(panel1, frame1, adding);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cat[2].setBackground(panelGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cat[2].setBackground(dateGray);
            }
        });

    }

    /**
     * This method asks the user to select one of
     * the three given purposes
     * Once selected, it proceeds further and calls showReview method if adding == false
     * Else it will call the addBooking method
     * @param panel1
     * @param frame1
     * @param adding
     */
    private void askPurpose(JPanel panel1, JFrame frame1, boolean adding) {
        panel1.removeAll();
        panel1.revalidate();
        panel1.repaint();

        GridBagConstraints split = new GridBagConstraints();
        split.weightx = 1;
        split.fill = GridBagConstraints.BOTH;

        JLabel question0 = new JLabel("Purpose of lab:");
        split.gridx = 0;
        split.gridy = 0;
        split.gridwidth = 1;
        split.gridheight = 1;
        formatLabel(question0, Color.WHITE, fnt);
        panel1.add(question0, split);

        JButton[] cat = new JButton[3];

        cat[0] = new JButton("RESEARCH");
        cat[1] = new JButton("DESIGN PROJECT");
        cat[2] = new JButton("SIMULATION");

        split.weighty = 1;
        split.gridx = 0;
        split.gridy = 1;
        booleanButton(cat[0]);
        panel1.add(cat[0], split);
        cat[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cat[0].setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cat[0].setBackground(dateGray);
                deviceS = 0;
                frame1.dispose();
                if(adding)addBooking();
                else showReview();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cat[0].setBackground(panelGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cat[0].setBackground(dateGray);
            }
        });

        split.gridx = 0;
        split.gridy = 2;
        booleanButton(cat[1]);
        panel1.add(cat[1], split);
        cat[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cat[1].setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cat[1].setBackground(dateGray);
                deviceS = 1;
                frame1.dispose();
                if(adding)addBooking();
                else showReview();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cat[1].setBackground(panelGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cat[1].setBackground(dateGray);
            }
        });

        split.gridx = 0;
        split.gridy = 3;
        booleanButton(cat[2]);
        panel1.add(cat[2], split);
        cat[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cat[2].setBackground(panelGray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cat[2].setBackground(dateGray);
                deviceS = 2;
                frame1.dispose();
                if(adding)addBooking();
                else showReview();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cat[2].setBackground(panelGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cat[2].setBackground(dateGray);
            }
        });

    }

    /**
     * Reads the reviews from a text file into an array and calls the print method
     */
    private void showReview() {
        String fileName = "ReviewD.txt";
        String line = " ";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            while(line != null) {
                if (line.equals("~")) {
                    review[Integer.parseInt(bufferedReader.readLine())][Integer.parseInt(bufferedReader.readLine())] = bufferedReader.readLine();
                }
                line = bufferedReader.readLine();
            }
            print();
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {}
        catch(IOException ex) {}
    }

    /**
     * Creates a simple frame that is formatted and displays a paragraph
     * summarizing the features, pros and cons of a product
     */
    public void print() {

        String text = review[device][deviceS];

        JTextArea textArea = new JTextArea(2, 20);
        textArea.setText(text);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(fnt);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(textArea, BorderLayout.CENTER);
        frame.getContentPane().setBackground(GUI.dateGray);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Calls the methods in a specific order to add a booking to the program
     */
    private void addBooking() {
        readFile();
        booking();
        drawBols();
    }

    /**
     * Draws the color bars to the calendar where necessary
     */
    private void drawBols() {
        for (int day = 0; day < database.length; day++) {
            boolean oneD = false;
            boolean twoD = false;
            boolean threeD = false;
            int labelCount = 0;

            for (int q = 0; q < database[day].length; q++) {
                for (int x = 0; x < database[day][q].length; x++) {
                    for (int y = 0; y < database[day][q][x].length; y++) {
                        if(!(database[day][q][x][y]==null) && (labelCount<3)){
//                            labLabel[day][0].setBackground(new Color(78, 249,0));
//                            labLabel[day][1].setBackground(new Color(78, 0,0));
//                            labLabel[day][2].setBackground(new Color(0, 0,0));
                            if(x==0&&(!oneD)){
                                labLabel[day][x].setBackground(new Color(0, 50, 255));
                                oneD=true;
                            }
                            else if(x==1&&(!twoD)) {
                                labLabel[day][x].setBackground(new Color(255, 242, 0));
                                twoD=true;
                            }
                            else if(x==2&&(!threeD)) {
                                labLabel[day][x].setBackground(new Color(255, 0, 255));
                                threeD=true;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Wipes the colors bars in order to update the changes to the bookings
     */
    private void wipeBols() {
        for (int day = 0; day < labLabel.length; day++) {
            for (int q = 0; q < labLabel[day].length; q++) {
                                labLabel[day][q].setBackground(dateGray);
            }
        }
    }

    /**
     * Reads the bookings into an 4-D array
     */
    private void readFile() {
        String fileName = "D1.txt";
        String line = " ";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            while (line != null) {
                details = line.split("-");
                database[Integer.parseInt(details[1]) - 1][Integer.parseInt(details[2])][Integer.parseInt(details[3]) - 1][Integer.parseInt(details[4]) - 1] = details[0];
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FNFE ex !!!!!!!!!# >>");
        } catch (IOException ex) {
            System.out.println("Exception ex!!!!!!!!!");
        }
    }

    /**
     * This method adds the booking to the data array
     * It also checks for invalid dates inputted by the user and fixes them if possible
     * If there are no available bookings, then it will prompt the user to change their booking time or device/purpose
     */
    public void booking() {
//        Scanner input = new Scanner(System.in);
        if (action == 2) {
            System.out.println("Reference# >>");
            ref = login.getName() + "-" + (startingDate) + "-" + timeChosen + "-" + (device + 1) + "-" + (1 + deviceS);
            details2 = ref.split("-");
            System.out.println(ref);
            if (details2[0].equals(database[Integer.parseInt(details2[1]) - 1][Integer.parseInt(details2[2])][Integer.parseInt(details2[3]) - 1][Integer.parseInt(details2[4]) - 1])) {
                database[Integer.parseInt(details2[1]) - 1][Integer.parseInt(details2[2])][Integer.parseInt(details2[3]) - 1][Integer.parseInt(details2[4]) - 1] = null;
                extract();
                wipeBols();
            }
        }
        if (action == 1) {
            String name = login.getName();
            int start = startingDate;
            if (start < 1) start = 1;
            int end = endingDate;
            if (end > 31) end = 31;
            if (start > end) {
                int temp = end;
                end = start;
                start = temp;
            }

            int time = timeChosen;
            for (int i = start - 1; i < end; i++) {
                if (database[i][time][device][deviceS] == null) {
                    database[i][time][device][deviceS] = name;
                    System.out.println("REF > " + database[i][time][device][deviceS] + "-" + (i + 1) + "-" + time + "-" + (device + 1) + "-" + (deviceS + 1));
                    extract();
                    return;
                }
            }

            if (sPriority) {
                String temp = database[start - 1][time][device][deviceS];
                database[start - 1][time][device][deviceS] = name;
                System.out.println(">>>>>" + database[start - 1][time][device][deviceS] + "-" + (start - 1) + "-" + time + "-" + (device + 1) + "-" + (deviceS + 1));
                for (int i = start - 1; i < database.length - 1; i++) {
                    if (database[i + 1][time][device][deviceS] != null) {
                        String temp2 = database[i + 1][time][device][deviceS];
                        database[i + 1][time][device][deviceS] = temp;
                        temp = temp2;
                    } else {
                        database[i + 1][time][device][deviceS] = temp;
                        break;
                    }
                }
                extract();
            } else {
                //Custom button text
                Object[] options = {"Change the time",
                        "Change the device",
                        "CANCEL"};

                int n = JOptionPane.showOptionDialog(frame,
                        "The time you selected is not available\n" +
                                "for the particular device.\n" +
                                "Try changing your timing or the device.",
                        "Time not Available",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[2]);
                System.out.println(n);

                JPanel panel1;
                JFrame frame1;
                panel1 = new JPanel();
                panel1.setLayout(new GridBagLayout());
                frame1 = new JFrame();
                frame1.setContentPane(panel1);
                frame1.setTitle("Booker");
                frame1.getContentPane().setBackground(GUI.dateGray);
                frame1.setSize(600, 400);
                //pack();
                frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
                switch (n) {
                    case 0:
                        askForTime(panel1, frame1, true);
                        break;
                    case 1:
                        askForDeviceType(panel1, frame1);
                        break;
                }
            }
        }
    }

    /**
     * This method extracts the data from the array and saves it to a text file
     * so even after the termination of the program, the data of the bookings
     * is not lost
     */
    public void extract() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("D1.txt"));

            for (int i = 0; i < database.length; i++) {
                for (int q = 0; q < database[i].length; q++) {
                    for (int x = 0; x < database[i][q].length; x++) {
                        for (int y = 0; y < database[i][q][x].length; y++) {
                            if (database[i][q][x][y] != null) {
                                bw.write("" + database[i][q][x][y] + "-" + (i + 1) + "-" + q + "-" + (x + 1) + "-" + (y + 1));
                                bw.newLine();
                            }
                        }
                    }
                }
            }
            bw.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FNFE ex !!!!!!!!!# >>");
        } catch (IOException ex) {
            System.out.println("Exception ex!!!!!!!!!");
        }
    }

    /**
     * This method saves the user's input to variables to allow the
     * other method to refer to these when changing the data array and GUI
     * @param priority
     * @param startDate
     * @param endDate
     */
    public void save(boolean priority, JFormattedTextField startDate, JFormattedTextField endDate) {
        sPriority = priority;
        startingDate = Integer.parseInt(startDate.getText());
        endingDate = Integer.parseInt(endDate.getText());
    }

    /**
     * Creates the frame for the reviews to be displays and runs
     * the methods required to get the input from the user about the device
     * they would like to learn about
     */
    public void readReview() {
        JPanel panel1;
        JFrame frame1;
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        frame1 = new JFrame();

        askForDeviceType(panel1, frame1, false);

        frame1.setContentPane(panel1);
        frame1.setTitle("Booker");
        frame1.getContentPane().setBackground(GUI.dateGray);
        frame1.setSize(600, 400);
        //pack();
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
    }

    /**
     * Default properties for a on/off button
     * @param button
     */
    public void booleanButton(JButton button) {
        booleanButton(button, Color.WHITE, dateGray);
    }

    /**
     * Customizable properties for a on/off button
     * @param button
     * @param foreColor
     * @param backColor
     */
    public void booleanButton(JButton button, Color foreColor, Color backColor) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(foreColor);
        button.setBackground(backColor);
        button.setFont(fnt);
        button.setEnabled(false);
    }

    /**
     * Default properties for a radio button
     * @param radio
     */
    public void radioFormat(JRadioButton radio) {
        radioFormat(radio, Color.WHITE, dateGray);
    }

    /**
     * Customizable properties for a radio button
     * @param radio
     * @param foreColor
     * @param backColor
     */
    public void radioFormat(JRadioButton radio, Color foreColor, Color backColor) {
        radio.setForeground(foreColor);
        radio.setBackground(backColor);
        radio.setFont(fnt);
        radio.setEnabled(true);
    }

    /**
     * Customizable properties of a numerical character on the calendar
     * @param wkd
     * @param d4
     */
    public void dayLabel(JLabel wkd, Dimension d4){
        wkd.setPreferredSize(d4);
        wkd.setOpaque(true);
        wkd.setBackground(dateGray);
        wkd.setForeground(blew);
        wkd.setFont(fnt);
        wkd.setHorizontalAlignment(JLabel.LEADING);
        wkd.setVerticalAlignment(JLabel.TOP);
        wkd.setVisible(true);
    }

    /**
     * Customizable properties of a generic label
     * @param label
     * @param textColor
     * @param font
     */
    public void formatLabel(JLabel label, Color textColor, Font font) {
        label.setFont(font);
        label.setForeground(textColor);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setVisible(true);
    }
}