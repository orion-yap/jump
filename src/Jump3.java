/*
Orion Yap
Stick Jump! The game
Ik putting a lot of code on one line is bad but I wanted to save space since I have to print it out
*/
import java.awt.*; import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
public class Jump3 extends JFrame{
    public static void main(String[] args){Jump3 jj = new Jump3();}
    public Jump3(){
        JFrame frame = new JFrame("Stick Jump! v0.3");
        frame.setSize(690, 690);
        frame.setResizable(false);
        Pan pan = new Pan();
        frame.setContentPane(pan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    class Pan extends JPanel{
        HelpP helpP; Board board; Stats stats; Home home; HomeP homeP; StoryP storyP;
        JPanel playP, display, items, butP;
        JButton homeB, homeB2, playB, helpB, storyB;
        int health, score;
        Image back = new ImageIcon("src/window_background.png").getImage();
        //sets panels and buttons and calls runner
        public Pan(){
            //initialize panels and buttons
            homeB = new JButton ("Home"); homeB2 = new JButton("Home");
            playB = new JButton("Play!");
            helpB = new JButton("Help");
            storyB = new JButton("Story");
            helpP = new HelpP(); board = new Board(); stats = new Stats(); home = new Home();
            storyP = new StoryP(); homeP = new HomeP();
            display = new JPanel(); playP = new JPanel(); items = new JPanel(); butP = new JPanel();
            health = 100; score = 0;
            runner();
        }
        public void runner(){
            //Layout and background color setup
            playP.setLayout(new BorderLayout());
            playP.setPreferredSize(new Dimension(690, 647));
            homeP.setLayout(new BorderLayout(0, 0));
            homeP.setPreferredSize(new Dimension(690, 647));
            CardLayout cL = new CardLayout();
            display.setLayout(cL);
            butP.setLayout(new GridLayout(1, 3, 0, 0));
            butP.setBackground(Color.BLACK);
            //Button setup
            homeB.setPreferredSize(new Dimension(0, 30));
            homeB2.setPreferredSize(new Dimension(0, 30));
            playB.setPreferredSize(new Dimension(90, 30));
            helpB.setPreferredSize(new Dimension(90, 30));
            storyB.setPreferredSize(new Dimension(90, 30));
            homeB.setBackground(Color.BLACK); homeB.setForeground(Color.WHITE);
            homeB2.setBackground(Color.BLACK); homeB2.setForeground(Color.WHITE);
            playB.setBackground(Color.BLACK); playB.setForeground(Color.WHITE); playB.setHorizontalAlignment(SwingConstants.RIGHT);
            helpB.setBackground(Color.BLACK); helpB.setForeground(Color.WHITE); helpB.setHorizontalAlignment(SwingConstants.RIGHT);
            storyB.setBackground(Color.BLACK); storyB.setForeground(Color.WHITE); storyB.setHorizontalAlignment(SwingConstants.RIGHT);
            homeB.addActionListener(e -> cL.show(display, "link1"));
            homeB2.addActionListener(e -> cL.show(display, "link1"));
            playB.addActionListener(e -> cL.show(display, "link2"));
            helpB.addActionListener(e -> cL.show(display, "link3"));
            storyB.addActionListener(e -> cL.show(display, "link4"));
            //navigation panel (buttons) setup
            butP.add(helpB);
            butP.add(playB);
            butP.add(storyB);
            //home panel setup
            JLabel title = new JLabel("Stick Jump!");
            title.setFont(new Font("Serif", Font.PLAIN, 60));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            homeP.add(title, BorderLayout.NORTH);
            homeP.add(butP, BorderLayout.SOUTH);
            homeP.add(home);
            //display panel setup
            display.add(homeP, "link1");
            display.add(playP, "link2");
            display.add(helpP, "link3");
            display.add(storyP, "link4");
            //play panel setup
            items.setPreferredSize(new Dimension(110, 647));
            stats.setPreferredSize(new Dimension(110, 647));
            playP.add(items, BorderLayout.WEST);
            playP.add(stats, BorderLayout.EAST);
            playP.add(board, BorderLayout.CENTER);
            //main panel setup
            add(display);
            System.out.print("setup done");
        }
        //drawing for home panel
        class Home extends JPanel{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(back, -10, -70, 800, 610, null);
                Image pgun = new ImageIcon("src/portal_gun.png").getImage();
                //Climbing figure
                g.drawLine(8, 70, 28, 50);
                g.drawLine(8, 50, 18, 40);
                g.drawLine(18, 40, 28, 50);
                g.drawLine(28, 50, 26, 30);
                g.drawLine(26, 30, 8, 35);
                g.drawLine(26, 30, 8, 40);
                g.drawOval(14, 10, 20, 20);
                //Jumping figure
                g.drawOval(45, 250, 20, 20);
                g.drawLine(50, 269, 25, 275);
                g.drawLine(50, 269, 60, 280);
                g.drawLine(50, 269, 55, 285);
                g.drawLine(25, 275, 10, 285);
                g.drawLine(25, 275, 30, 285);
                g.drawLine(30, 285, 20, 290);
                g.drawImage(pgun, 10, 170, 50, 30, null);
                //portal figure
                g.drawOval(42, 362, 20, 20);
                g.drawLine(65, 385, 60, 380);
                g.drawLine(60, 380, 50, 390);
                g.drawLine(60, 380, 50, 400);
                //second portal
                g.setColor(Color.decode("#ebab34"));
                g.fillRect(65, 350, 5, 70);
                Image mc = new ImageIcon("src/minecraft_logo.png").getImage();
                g.setColor(Color.BLACK);
                //portal figure
                g.drawLine(600, 135, 610, 145);
                g.drawLine(610, 145, 618, 160);
                g.drawLine(610, 145, 615, 170);
                //TSC AVM
                g.setColor(Color.decode("#ebab34"));
                g.drawLine(615, 360, 630, 340);
                g.drawOval(620, 320, 20, 20);
                g.drawLine(630, 340, 630, 365);
                g.drawLine(630, 365, 625, 380);
                g.drawLine(630, 365, 635, 380);
                g.drawLine(630, 340, 635, 360);
                g.drawImage(mc, 600, 350, 20, 20, null);
                //first portal
                g.setColor(Color.decode("#34d2eb"));
                g.fillRect(600, 100, 5, 70);
                g.setColor(Color.BLACK);
                //The Chosen One (I forgot how to set stroke in graphics lmao)
                g.fillOval(690/2-100/2, 50, 100, 100);
                g.setColor(Color.WHITE);
                g.fillOval(690/2-80/2, 60, 80, 80);
                g.setColor(Color.BLACK);
                g.fillRect(690/2-5, 60+80, 10, 150);
                int[] leg1x = {690/2, 690/2-5, 690/2-38, 690/2-30}; int[] leg2x = {690/2, 690/2+5, 690/2+38, 690/2+30};
                int[] legy = {60+80+150, 60+80+150-15, 60+80+150+95, 60+80+150+100-1};
                g.fillPolygon(leg1x, legy, 4);
                g.fillPolygon(leg2x, legy, 4);
                int[] arm1x = {690/2, 690/2-5, 690/2-37, 690/2-30}; int[] arm2x = {690/2, 690/2+5, 690/2+37, 690/2+30};
                int[] army = {60+80+10, 60+80-1+5, 60+80+98+5, 60+80+100+1+5};
                g.fillPolygon(arm1x, army, 4);
                g.fillPolygon(arm2x, army, 4);
                Image fire = new ImageIcon("src/fire.png").getImage();
                g.drawImage(fire, 285, 210, 50, 50, null);
                g.drawImage(fire, 355, 210, 50, 50, null);
                Image eye = new ImageIcon("src/laser_eye.png").getImage();
                g.drawImage(eye, 100, 100, 50, 50, null);
            }}
        //Home panel
        class HomeP extends JPanel{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(back, -25, 0, 800, 610, null);
            }}
        //Board panel
        class Board extends JPanel{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                setBackground(Color.BLUE);
            }}
        //Stats panel
        class Stats extends JPanel{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                setBackground(Color.decode("#8d00cf"));
                g.setColor(Color.WHITE);
                g.fillRoundRect(5, 5, 100, 10, 5, 5);
                g.setColor(Color.RED);
                g.fillRoundRect(5, 5, health, 10, 5, 5);
                g.setColor(Color.BLACK);
                g.drawRoundRect(5, 5, 100, 10, 5, 5);
                g.drawRoundRect(5, 5, health, 10, 5, 5);
            }}
        //Help panel
        class HelpP extends JPanel{
            public HelpP() {
                components();
            }
            public void components(){
                setLayout(new BorderLayout());
                setBackground(Color.WHITE);
                JTextArea text = new JTextArea("  1. Use keys W, A, S, and D to move\n  2. Jump from black platform to black platform to avoid\n     falling into the void\n  3. Get as high as you can!");
                text.setFont(new Font("Serif", Font.PLAIN, 20));
                text.setEditable(false);
                text.setPreferredSize(new Dimension(690, 200));
                add(text, BorderLayout.NORTH);
                add(homeB2, BorderLayout.SOUTH);
            }}
        class StoryP extends JPanel{
            public StoryP(){
                setLayout(new BorderLayout());
                setBackground(Color.WHITE);
                JTextArea text = new JTextArea("\n  Oh no! That blasted cursor is after you again. Remember what happened last time it\n  caught up... no. Don't get put in chains again. Jump on those platforms to get away!\n\n  *This game is heavily inspired by Alan Becker's Animation vs Animator");
                text.setFont(new Font("Serif", Font.PLAIN, 20));
                text.setEditable(false);
                text.setPreferredSize(new Dimension(690, 300));
                add(text, BorderLayout.NORTH);
                Story story = new Story();
                story.setPreferredSize(new Dimension(690, 300));
                add(story);
                add(homeB, BorderLayout.SOUTH);
            }
            class Story extends JPanel{
                public void paintComponent(Graphics g){
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                    //stick figure
                    g.drawOval(100, 0, 50, 50);
                    g.drawLine(135, 47, 180, 160);
                    g.drawLine(159, 105, 151, 125);
                    g.drawLine(151, 125, 160, 145);
                    g.drawLine(135, 47, 130, 65);
                    g.drawLine(105, 75, 130, 65);
                    g.drawLine(135, 47, 150, 55);
                    g.drawLine(150, 55, 127, 80);
                    //mouse
                    Image cursor = new ImageIcon("src/cursor.png").getImage();
                    g.drawImage(cursor, 100, 100, 20, 20, null);
                    //wind lines
                    g.drawLine(200, 30, 250, 30);
                    g.drawLine(200, 60, 250, 60);
                    g.drawLine(200, 90, 250, 90);
                    g.drawLine(200, 120, 250, 120);
                    g.drawLine(400, 60, 420, 60);
                    g.drawLine(400, 65, 420, 65);
                }}}}}