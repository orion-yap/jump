/*
Orion Yap
Stick Jump! The game
Ik putting a lot of code on one line is bad but I wanted to save space since I have to print it out
*/
import java.awt.*; import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter; import java.io.PrintWriter;
import java.io.File; import java.io.FileNotFoundException;
import java.io.IOException; import java.util.Scanner;
//// TODO: 5/7/2022 make home screen portal gifs, and animate character
//// TODO: 5/7/2022 clean up code; rename (naming conventions), organize
public class Jump3 extends JFrame{ //starter class: makes frame and calls panel class
    Scanner scan; JFrame frame;
    public static void main(String[] args){new Jump3();}
    public Jump3(){
        scan = new Scanner(System.in);
        frame = new JFrame("Stick Jump! v0.3");
        frame.setSize(1500, 1602);
        frame.setResizable(false);
        Pan pan = new Pan();
        frame.setContentPane(pan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    class Pan extends JPanel{ //main class: makes main panel and initializes most of the game
        boolean bcolor, burning, color,  first;
        int difficulty, health, maxh, score;
        String username;
        String[][] lead = new String[10][2];
        Scanner FReader;
        Font f1;
        File leadFile;
        Image back = new ImageIcon("src/window_background.png").getImage();
        ImageIcon c21 = new ImageIcon("src/cityback11.png");
        ImageIcon c22 = new ImageIcon("src/cityback21.png");
        ImageIcon c23 = new ImageIcon("src/cityback31.png");
        CardLayout cL;
        Timer scoretime;
        JLabel c1, c2, c3, mine, val;
        JTextField name;
        JTextArea nameMessage, settingsMessage;
        JButton again, helpB, lvl1, lvl2, lvl3, playB, settingsB, storyB;
        JButton[] homeB;
        JPanel butP, display, end, esnav, homeP, items, lvlSelect, playP, settings;
        PrintWriter out;
        Board board; HelpP helpP; Home home; Stats stats; StoryP storyP; EndScreen es;
        //sets panels and buttons and calls runner
        public Pan(){
            //initialize panels and buttons
            homeB = new JButton[5];
            for(int i = 0; i < 5; i++){
                homeB[i] = new JButton("Home");
                homeB[i].setBackground(Color.BLACK);
                homeB[i].setForeground(Color.WHITE);
                homeB[i].addActionListener(e -> cL.show(display, "link1"));
                homeB[i].setFont(new Font("Serif", Font.PLAIN, 80));
            }
            again = new JButton("Try again?");
            ImageIcon play = new ImageIcon("src/play.png");
            playB = new JButton("Play!", play);
            ImageIcon gear = new ImageIcon("src/gear.png");
            helpB = new JButton("Help");
            storyB = new JButton(" Story");
            settingsB = new JButton(gear);
            helpP = new HelpP(); stats = new Stats(); home = new Home();
            storyP = new StoryP();
            display = new JPanel(); playP = new JPanel(); items = new JPanel(); butP = new JPanel(); end = new JPanel(); homeP = new JPanel();
            settings = new JPanel(); lvlSelect = new JPanel();
            val = new JLabel(new ImageIcon("src/valorant.png"));
            mine = new JLabel(new ImageIcon("src/minecraft2.png"));
            maxh = 100;
            health = maxh; score = 0;
            first = bcolor = false;
            Score scorekeep = new Score();
            scoretime = new Timer(1000, scorekeep);
            leadFile = new File("src/leaderboard.txt");
            name = new JTextField("Username");
            username = "User0001";
            settingsMessage = new JTextArea("Username: "+username);
            try {out = new PrintWriter("leaderboard.txt");}
            catch (FileNotFoundException e){
                System.err.println("Error: file not created");
                System.exit(1);
            }
            try {FReader = new Scanner(leadFile);}
            catch (FileNotFoundException e){
                System.err.println("Error: file not found");
                System.exit(1);
            }
            nameMessage = new JTextArea("Enter username below (max 10 characters)");
            nameMessage.setEditable(false);
            lvl1 = new JButton();
            lvl2 = new JButton();
            lvl3 = new JButton();
            f1 = new Font("Serif", Font.PLAIN, 50);
            c1 = new JLabel(c21);
            c2 = new JLabel(c22);
            c3 = new JLabel(c23);
            runner();
        }
        class Score implements ActionListener{
            public void actionPerformed(ActionEvent e){
                score++;
                repaint();
                grabFocus();
            }}
        public void runner(){
            //Layout and background color setup
            playP.setLayout(new BorderLayout());
            playP.setPreferredSize(new Dimension(1500, 1547));
            homeP.setLayout(new BorderLayout(0, 0));
            homeP.setPreferredSize(new Dimension(1500, 1547)); cL = new CardLayout();
            display.setLayout(cL);
            butP.setLayout(null);
            butP.setPreferredSize(new Dimension(1500, 100));
            butP.setBackground(Color.BLACK);
            end.setLayout(new BorderLayout());
            end.setBackground(Color.BLACK);
            settings.setBackground(Color.WHITE);
            //Button setup
            again.setBackground(Color.BLACK); again.setForeground(Color.WHITE); color = false;
            settingsB.setBackground(Color.BLACK);
            Again alistener = new Again();
            Timer atime = new Timer(1000, alistener);
            atime.start();
            playB.setBackground(Color.BLACK); playB.setForeground(Color.WHITE); playB.setFont(new Font("Serif", Font.PLAIN, 80));
            helpB.setBackground(Color.BLACK); helpB.setForeground(Color.WHITE); helpB.setFont(new Font("Serif", Font.PLAIN, 80));
            storyB.setBackground(Color.BLACK); storyB.setForeground(Color.WHITE); storyB.setFont(new Font("Serif", Font.PLAIN, 80));
            again.addActionListener(e -> cL.show(display, "link2"));
            playB.addActionListener(e -> cL.show(display, "link2"));
            helpB.addActionListener(e -> cL.show(display, "link3"));
            storyB.addActionListener(e -> cL.show(display, "link4"));
            settingsB.addActionListener(e -> cL.show(display, "link6"));
            //settings panel setup
            settings.setLayout(null);
            name.addActionListener(new Name());
            settingsMessage.setFont(f1);
            settingsMessage.setEditable(false);
            name.setFont(f1);
            settings.add(settingsMessage);
            settingsMessage.setBounds(50, 50, 500, 150);
            settings.add(name);
            name.setBounds(50, 310, 1000, 100);
            nameMessage.setFont(f1);
            nameMessage.setBackground(Color.WHITE);
            settings.add(nameMessage);
            nameMessage.setBounds(50, 240, 900, 70);
            settings.add(homeB[2]);
            homeB[2].setBounds(0, 1447, 1500, 100);
            //level select panel setup
            lvl1.addActionListener(new PlayAct1());
            lvl2.addActionListener(new PlayAct2());
            lvl3.addActionListener(new PlayAct3());
            lvl1.setOpaque(true);
            lvl2.setOpaque(true);
            lvl3.setOpaque(true);
            lvl1.setContentAreaFilled(false);
            lvl2.setContentAreaFilled(false);
            lvl3.setContentAreaFilled(false);
            lvl1.setFont(f1);
            lvl2.setFont(f1);
            lvl3.setFont(f1);
            lvlSelect.setLayout(null);
            lvlSelect.add(c1);
            c1.setBounds(50, 25, 1400, 420);
            lvlSelect.add(c2);
            c2.setBounds(50, 495, 1400, 420);
            lvlSelect.add(c3);
            c3.setBounds(50, 965, 1400, 420);
            lvlSelect.add(lvl1);
            lvl1.setBounds(50, 25, 1400, 420);
            lvlSelect.add(lvl2);
            lvl2.setBounds(50, 495, 1400, 420);
            lvlSelect.add(lvl3);
            lvl3.setBounds(50, 965, 1400, 420);
            lvlSelect.add(homeB[4]);
            homeB[4].setBounds(0, 1447, 1500, 100);
            //nav panel (buttons) setup
            butP.add(helpB);
            helpB.setBounds(100, 0, 100*100/30-100,  100);
            butP.add(playB);
            playB.setBounds(100*100/30, 0, 110*100/30, 100);
            butP.add(storyB);
            storyB.setBounds(100*100/30+110*100/30, 0, 70*100/30, 100);
            butP.add(settingsB);
            settingsB.setBounds(0, 0, 100, 100);
            //filler
            butP.add(val);
            val.setBounds(100*100/30+110*100/30+70*100/30+100-100, 0, 100, 100);
            butP.add(mine);
            mine.setBounds(100*100/30+110*100/30+70*100/30+200-100, 0, 100, 100);
            //home panel setup
            homeP.add(butP, BorderLayout.SOUTH);
            homeP.add(home);
            //display panel setup
            display.add(homeP, "link1");
            display.add(lvlSelect, "link2");
            display.add(helpP, "link3");
            display.add(storyP, "link4");
            display.add(end, "link5");
            display.add(settings, "link6");
            display.add(playP, "link7");
            //end screen setup
            esnav = new JPanel();
            esnav.setLayout(null);
            esnav.setPreferredSize(new Dimension(0, 100));
            again.setFont(new Font("Serif", Font.BOLD, 80));
            esnav.add(homeB[3]);
            homeB[3].setBounds(0, 0, 1500/2, 100);
            esnav.add(again);
            again.setBounds(1500/2, 0, 1500/2, 100);
            es = new EndScreen();
            end.add(esnav, BorderLayout.SOUTH);
            end.add(es);
            //main panel setup
            add(display);
        }
        class Name implements ActionListener{
            public void actionPerformed(ActionEvent e){
                if(name.getText().length() <= 10 || name.getText().equals("riptide30125")) {
                    username = name.getText();
                    name.setText("");
                    settingsMessage.setText("Username: "+username);
                }
                else name.setText("Too long");
            }}
        class PlayAct1 implements ActionListener{
            public void actionPerformed(ActionEvent e){
                //play panel setup
                end = new EndScreen();
                if(first) board.reset(0);
                else {board = new Board(0); first = true;}
                stats = new Stats();
                items.setPreferredSize(new Dimension(110*1500/690, 1547));
                stats.setPreferredSize(new Dimension(123*1500/690, 1547));
                playP.add(items, BorderLayout.WEST);
                playP.add(stats, BorderLayout.EAST);
                playP.add(board, BorderLayout.CENTER);
                //reset stats
                maxh = 100; health = maxh; score = 0;
                cL.show(display, "link7");
        }}
        class PlayAct2 implements ActionListener{
            public void actionPerformed(ActionEvent e){
                //play panel setup
                if(score >= 50){
                    end = new EndScreen();
                    if(first) board.reset(1);
                    else {board = new Board(1); first = true;}
                    stats = new Stats();
                    items.setPreferredSize(new Dimension(110*1500/690, 1547));
                    stats.setPreferredSize(new Dimension(123*1500/690, 1547));
                    playP.add(items, BorderLayout.WEST);
                    playP.add(stats, BorderLayout.EAST);
                    playP.add(board, BorderLayout.CENTER);
                    //reset stats
                    maxh = 100; health = maxh; score = 0;
                    cL.show(display, "link7");
            }}}
        class PlayAct3 implements ActionListener{
            public void actionPerformed(ActionEvent e){
                //play panel setup
                if(score >= 200){
                    end = new EndScreen();
                    if(first) board.reset(2);
                    else {board = new Board(2); first = true;}
                    stats = new Stats();
                    items.setPreferredSize(new Dimension(110*1500/690, 1547));
                    stats.setPreferredSize(new Dimension(123*1500/690, 1547));
                    playP.add(items, BorderLayout.WEST);
                    playP.add(stats, BorderLayout.EAST);
                    playP.add(board, BorderLayout.CENTER);
                    //reset stats
                    maxh = 100; health = maxh; score = 0;
                    cL.show(display, "link7");
            }}}
        class Again implements ActionListener{
            public void actionPerformed(ActionEvent e){
                color = !color;
                if(color) again.setForeground(Color.GREEN);
                else again.setForeground(Color.WHITE);
                es.repaint();
            }}
        class BurnText implements ActionListener{public void actionPerformed(ActionEvent e){bcolor = !bcolor;}}
        class Home extends JPanel{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
                g.setFont(new Font("Serif", Font.PLAIN, 100));
                g.setColor(Color.BLACK);
                g.drawString("Stick Jump!", 100, 1000);
                g.drawImage(back, -10*1500/690, -70*1500/690, 800*1500/690, 610*1500/690+300, null);
                //first portal
                Image p1 = new ImageIcon("src/portal1.png").getImage();
                g2.drawImage(p1, 600*1500/690-10, 100*1500/690+100, 100, 152, null);
                //second portal
                Image p2 = new ImageIcon("src/portal2.png").getImage();
                g2.drawImage(p2, 65*1500/690-90, 350*1500/690+100, 100, 152, null);
                Image pgun = new ImageIcon("src/portal_gun.png").getImage();
                //Climbing figure
                g2.drawLine(0, 70*1500/690+100, 28*1500/690, 50*1500/690+100);
                g2.drawLine(0, 50*1500/690+100, 18*1500/690, 40*1500/690+100);
                g2.drawLine(18*1500/690, 40*1500/690+100, 28*1500/690, 50*1500/690+100);
                g2.drawLine(28*1500/690, 50*1500/690+100, 26*1500/690, 30*1500/690+100);
                g2.drawLine(26*1500/690, 30*1500/690+100, 0, 35*1500/690+100);
                g2.drawLine(26*1500/690, 30*1500/690+100, 0, 40*1500/690+100);
                g2.drawOval(14*1500/690, 10*1500/690+100, 20*1500/690, 20*1500/690);
                //Jumping figure
                g2.drawOval(45*1500/690, 250*1500/690+100, 20*1500/690, 20*1500/690);
                g2.drawLine(50*1500/690, 269*1500/690+100, 25*1500/690, 275*1500/690+100);
                g2.drawLine(50*1500/690, 269*1500/690+100, 60*1500/690, 280*1500/690+100);
                g2.drawLine(50*1500/690, 269*1500/690+100, 55*1500/690, 285*1500/690+100);
                g2.drawLine(25*1500/690, 275*1500/690+100, 10*1500/690, 285*1500/690+100);
                g2.drawLine(25*1500/690, 275*1500/690+100, 30*1500/690, 285*1500/690+100);
                g2.drawLine(30*1500/690, 285*1500/690+100, 20*1500/690, 290*1500/690+100);
                g2.drawImage(pgun, 10*1500/690, 170*1500/690+100, 50*1500/690, 30*1500/690, null);
                //portal figure
                g2.drawOval(42*1500/690, 362*1500/690+100, 20*1500/690, 20*1500/690);
                g2.drawLine(65*1500/690, 385*1500/690+100, 60*1500/690, 380*1500/690+100);
                g2.drawLine(60*1500/690, 380*1500/690+100, 50*1500/690, 390*1500/690+100);
                g2.drawLine(60*1500/690, 380*1500/690+100, 50*1500/690, 400*1500/690+100);
                Image mc = new ImageIcon("src/minecraft_logo.png").getImage();
                g2.setColor(Color.BLACK);
                //portal figure
                g2.drawLine(600*1500/690, 135*1500/690+100, 610*1500/690, 145*1500/690+100);
                g2.drawLine(610*1500/690, 145*1500/690+100, 618*1500/690, 160*1500/690+100);
                g2.drawLine(610*1500/690, 145*1500/690+100, 615*1500/690, 170*1500/690+100);
                //TCO AVM
                g2.drawLine(615*1500/690, 360*1500/690+100, 630*1500/690, 340*1500/690+100);
                g2.drawOval(620*1500/690, 320*1500/690+100, 20*1500/690, 20*1500/690);
                g2.drawLine(630*1500/690, 340*1500/690+100, 630*1500/690, 365*1500/690+100);
                g2.drawLine(630*1500/690, 365*1500/690+100, 625*1500/690, 380*1500/690+100);
                g2.drawLine(630*1500/690, 365*1500/690+100, 635*1500/690, 380*1500/690+100);
                g2.drawLine(630*1500/690, 340*1500/690+100, 635*1500/690, 360*1500/690+100);
                g2.drawImage(mc, 600*1500/690, 350*1500/690+100, 20*1500/690, 20*1500/690, null);
                //The Chosen One
                g2.setStroke(new BasicStroke(20));
                g2.drawOval((690/2-100/2)*1500/690, 50*1500/690+100, 100*1500/690, 100*1500/690);
                g2.setStroke(new BasicStroke(10));
                g.fillRect((690/2-5)*1500/690, (60+80+10)*1500/690+100, 10*1500/690, 150*1500/690-20);
                int[] leg1x = {690/2*1500/690, (690/2-5)*1500/690, (690/2-38)*1500/690, (690/2-30)*1500/690}; int[] leg2x = {690/2*1500/690, (690/2+5)*1500/690, (690/2+38)*1500/690, (690/2+30)*1500/690};
                int[] legy = {(60+80+150)*1500/690+100, (60+80+150-15)*1500/690+100, (60+80+150+95)*1500/690+100, (60+80+150+100-1)*1500/690+100};
                g.fillPolygon(leg1x, legy, 4);
                g.fillPolygon(leg2x, legy, 4);
                int[] arm1x = {690/2*1500/690, (690/2-5)*1500/690, (690/2-37)*1500/690, (690/2-30)*1500/690}; int[] arm2x = {690/2*1500/690, (690/2+5)*1500/690, (690/2+37)*1500/690, (690/2+30)*1500/690};
                int[] army = {(60+80+10)*1500/690+100, (60+80-1+5)*1500/690+100, (60+80+98+5)*1500/690+100, (60+80+100+1+5)*1500/690+100};
                g.fillPolygon(arm1x, army, 4);
                g.fillPolygon(arm2x, army, 4);
                Image fire = new ImageIcon("src/fire.png").getImage();
                g.drawImage(fire, 285*1500/690, 210*1500/690+100, 50*1500/690, 50*1500/690, null);
                g.drawImage(fire, 355*1500/690, 210*1500/690+100, 50*1500/690, 50*1500/690, null);
            }}
        class Stats extends JPanel{
            BurnText bt = new BurnText();
            Timer bttime = new Timer(500, bt);
            public void paintComponent(Graphics g) {
                scoretime.start();
                super.paintComponent(g);
                if(score < 0) setBackground(Color.RED);
                else if(difficulty == 0) setBackground(Color.decode("#28f928"));
                else if(difficulty == 1) setBackground(Color.decode("#00c8ff"));
                else if(difficulty == 2)setBackground(Color.decode("#8d00cf"));
                //health label
                g.setColor(Color.BLACK);
                g.setFont(f1);
                g.drawString("Health", 5, 55);
                //base healthbar
                g.setColor(Color.WHITE);
                g.fillRoundRect(5, 60, 200, 20, 10, 10);
                //health
                g.setColor(Color.RED);
                g.fillRoundRect(5, 60, health*200/maxh, 20, 10, 10);
                g.setColor(Color.BLACK);
                g.drawRoundRect(5, 60, 200, 20, 10, 10);
                g.drawRoundRect(5, 60, health*200/maxh, 20, 10, 10);
                //health labeling
                g.setFont(new Font("Serif", Font.ITALIC, 50));
                g.drawString(health+" / "+maxh, 5, 135);
                //score label
                g.setFont(f1);
                g.drawString("Score", 5, 205);
                //score
                g.setFont(new Font("Serif", Font.BOLD, 60));
                if(difficulty == 0) g.drawString(score+" / 50", 5, 260);
                else if(difficulty == 1) g.drawString(score+" / 200", 5, 260);
                else if(difficulty == 2) g.drawString(score+" / 400", 5, 260);
                //burning text
                if(burning){
                    bttime.start();
                    if(bcolor) g.setColor(Color.RED);
                    else g.setColor(Color.BLACK);
                    g.setFont(new Font("Serif", Font.ITALIC, 50));
                    g.drawString("! BURNING !", 5, 315);
                }
                else bttime.stop();
            }}
        class EndScreen extends JPanel{
            int fay = 1;
            public EndScreen(){
                setBackground(Color.BLACK);
                FallAnimate fa = new FallAnimate();
                Timer fatime = new Timer(5, fa);
                fatime.start();
            }
            public void paintComponent(Graphics g){
                g.setColor(Color.WHITE);
                g.setFont(new Font("Serif", Font.PLAIN, 100));
                g.drawString("Your score was", 30, 100);
                if(score < 0) g.setColor(Color.RED);
                else if(score <= 50) g.setColor(Color.decode("#28f928"));
                else if(score <= 200) g.setColor(Color.decode("#00c8ff"));
                else if(score <= 400) g.setColor(Color.decode("#8d00cf"));
                else if(color) g.setColor(Color.decode("#947f06"));
                else g.setColor(Color.decode("#8d00cf"));
                g.drawString(""+score, 100, 205);
                //Graphics2D g2 = new (Graphics2D) g;
                //g2.setStroke(new BasicStroke(3));
                //g2.drawOval
            }
            class FallAnimate implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    fay*=5;
                    if(fay > 600) fay = 1;
                    repaint();
            }}}
        class HelpP extends JPanel{
            public HelpP() {
                setLayout(new BorderLayout());
                Help help = new Help();
                add(help);
                homeB[1].setPreferredSize(new Dimension(0, 100));
                add(homeB[1], BorderLayout.SOUTH);
            }}
        class Help extends JPanel{
            boolean cur = true;
            public Help(){
                Cursor cursor = new Cursor();
                Timer ctime = new Timer(500, cursor);
                ctime.start();
            }
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                setBackground(Color.WHITE);
                Image bar = new ImageIcon("src/txt.png").getImage();
                Image bar2 = new ImageIcon("src/txt2.png").getImage();
                if(cur) g.drawImage(bar, 0, 0, 2900, 2000, null);
                else g.drawImage(bar2, 0, 0, 2900, 2000, null);
            }
            class Cursor implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    cur = !cur;
                    repaint();
                }}}
        class StoryP extends JPanel{
            public StoryP(){
                setLayout(new BorderLayout());
                setBackground(Color.WHITE); //story text block below
                JTextArea text = new JTextArea("\n  Oh no! That blasted cursor is after you again. Remember\n  what happened last time it caught up... no. Don't get put in\n  chains again. Jump on those platforms to get away!");
                text.setFont(new Font("Serif", Font.PLAIN, 60));
                text.setEditable(false);
                text.setPreferredSize(new Dimension(1500, 300*1500/690));
                add(text, BorderLayout.NORTH);
                Story story = new Story();
                story.setPreferredSize(new Dimension(690, 300));
                add(story);
                homeB[0].setPreferredSize(new Dimension(0, 100));
                add(homeB[0], BorderLayout.SOUTH);
            }
            class Story extends JPanel{
                public void paintComponent(Graphics g){
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(5));
                    //stick figure
                    g2.drawOval(100*1500/690, 0, 50*1500/690, 50*1500/690);
                    g2.drawLine(135*1500/690, 47*1500/690, 180*1500/690, 160*1500/690);
                    g2.drawLine(159*1500/690, 105*1500/690, 151*1500/690, 125*1500/690);
                    g2.drawLine(151*1500/690, 125*1500/690, 160*1500/690, 145*1500/690);
                    g2.drawLine(135*1500/690, 47*1500/690, 130*1500/690, 65*1500/690);
                    g2.drawLine(105*1500/690, 75*1500/690, 130*1500/690, 65*1500/690);
                    g2.drawLine(135*1500/690, 47*1500/690, 150*1500/690, 55*1500/690);
                    g2.drawLine(150*1500/690, 55*1500/690, 127*1500/690, 80*1500/690);
                    //mouse
                    Image cursor = new ImageIcon("src/cursor.png").getImage();
                    g.drawImage(cursor, 380*1500/690, 50*1500/690, 20*1500/690, 25*1500/690, null);
                    //wind lines
                    g.drawLine(200*1500/690, 30*1500/690, 250*1500/690, 30*1500/690);
                    g.drawLine(200*1500/690, 60*1500/690, 250*1500/690, 60*1500/690);
                    g.drawLine(200*1500/690, 90*1500/690, 250*1500/690, 90*1500/690);
                    g.drawLine(200*1500/690, 120*1500/690, 250*1500/690, 120*1500/690);
                    g.drawLine(400*1500/690, 60*1500/690, 420*1500/690, 60*1500/690);
                    g.drawLine(400*1500/690, 65*1500/690, 420*1500/690, 65*1500/690);
                }}}
        //Actual game
        class Board extends JPanel implements KeyListener{
            int px, py, pxforce, pyforce, tlength, burnMaxTime, curBurnTime;
            double speedmultiplier;
            boolean wpress, apress, dpressed, wallowpress, onGround, loseHealth, godmode;
            PlatMover platmove; PlayerMover pmove; TurretMover turmove; ProjectileMover projmove; ProjSpawner pspawn; BurnApp burn;
            Timer ptime, platime, turtime, projtime, pstime, btime;
            Image blood, fireball;
            Image city = new ImageIcon("src/cityback2.png").getImage();
            Image city2 = new ImageIcon("src/cityback.png").getImage();
            Image city3 = new ImageIcon("src/cityback3.png").getImage();
            Platform[] plats = new Platform[20]; PowerUp[] pows = new PowerUp[20]; Turret[] turr = new Turret[39];
            ArrayList<Projectile> projectile = new ArrayList();
            Graphics2D g2;
            public Board(int diff){
                reset(diff);
                addKeyListener(this);
                platmove = new PlatMover();
                platime = new Timer(3, platmove);
                platime.start();
                pmove = new PlayerMover();
                ptime = new Timer(3, pmove);
                ptime.start();
                turmove = new TurretMover();
                turtime = new Timer(5, turmove);
                turtime.start();
                projmove = new ProjectileMover();
                projtime = new Timer(3, projmove);
                projtime.start();
                pspawn = new ProjSpawner();
                pstime = new Timer(2000, pspawn);
                pstime.start();
                burn = new BurnApp();
                btime = new Timer(1000, burn);
                godmode = false;
                tlength = 40;
                blood = new ImageIcon("src/blood.png").getImage();
                fireball = new ImageIcon("src/fireball.png").getImage();
            }
            public void reset(int difficult){
                difficulty = difficult;
                burning = false;
                speedmultiplier = 0.5;
                px = 995/2; py = 50;
                pxforce = 0; pyforce = 0;
                wpress = apress = dpressed = onGround = false;
                wallowpress = loseHealth = true;
                for(int i = 0; i < 20; i++){
                    plats[i] = new Platform(); plats[i].y = 1547-i*80;
                    pows[i] = new PowerUp();
                }
                for(int i = 0; i < 39; i++) turr[i] = new Turret();
                plats[10].x = 995/2;
            }
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                if(difficulty == 0) g.drawImage(city, 0, 0, 995, 1547, null);
                else if(difficulty == 1) g.drawImage(city2, 0, 0, 995, 1547, null);
                else if(difficulty == 2) g.drawImage(city3, 0, 0, 995, 1547, null);
                g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(5));
                g2.drawOval(px-10*1500/690, py-60*1500/690, 20*1500/690, 20*1500/690); //rendering Player
                g2.drawLine(px, py-40*1500/690, px, py-20*1500/690);
                g2.drawLine(px, py-20*1500/690, px-5*1500/690, py);
                g2.drawLine(px, py-20*1500/690, px+5*1500/690, py);
                g2.drawLine(px, py-40*1500/690, px-7*1500/690, py-20*1500/690);
                g2.drawLine(px, py-40*1500/690, px+7*1500/690, py-20*1500/690);
                g2.setStroke(new BasicStroke(5));
                for(int i = 0; i < 20; i++) g2.drawLine(plats[i].x-30*1500/690, plats[i].y, plats[i].x+30*1500/690, plats[i].y);//rendering Platforms
                g2.setColor(Color.GRAY);
                int x3, y3;
                for(int i = 0; i < 39; i++){ //rendering turrets
                    if(turr[i].inplay) {
                        g2.fillOval(turr[i].x, turr[i].y, 40, 40);
                        g2.setStroke(new BasicStroke(20));
                        //failure case: player is "inside" turret
                        //calculates where turret will end while still pointing to (middle of) player
                        turr[i].x3 = x3 = (int)(((px - turr[i].x + 20) * tlength) / Math.sqrt(Math.pow(px - turr[i].x + 20, 2) + Math.pow(py - turr[i].y, 2)));
                        turr[i].y3 = y3 = (int)(((py - 130 - turr[i].y + 20) * tlength) / Math.sqrt(Math.pow(px - turr[i].x + 20, 2) + Math.pow(py - 130 - turr[i].y, 2)));
                        g2.drawLine(turr[i].x + 20, turr[i].y + 20, turr[i].x+20+x3, turr[i].y+20+y3);
                    }}
                for(int i = 0; i < projectile.size(); i++) { //rendering projectiles
                    if(projectile.get(i).type==0||projectile.get(i).type==3||projectile.get(i).type==4||projectile.get(i).type==5){
                        g.setColor(Color.BLACK);
                        g.fillOval(projectile.get(i).x - 20, projectile.get(i).y - 20, 40, 40);
                    }
                    else g.drawImage(fireball, projectile.get(i).x-20, projectile.get(i).y-20, 40, 40, null);
                }
                for(int i = 0; i < 20; i++){ //rendering power ups
                    if(pows[i].inplay){
                        if(pows[i].type == 0) ;
                    }}}
            class ProjSpawner implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < 39; i++){
                        if(turr[i].inplay){
                            if(turr[i].type == 0) projectile.add(new Projectile(0, turr[i].x, turr[i].y, turr[i].x3, turr[i].y3));
                            else if(turr[i].type == 1) projectile.add(new Projectile(1, turr[i].x, turr[i].y, turr[i].x3, turr[i].y3));
                            else if(turr[i].type == 3) projectile.add(new Projectile(2, turr[i].x, turr[i].y, turr[i].x3, turr[i].y3));
                            else {
                                projectile.add(new Projectile(0, turr[i].x, turr[i].y, turr[i].x3, turr[i].y3/2));
                                projectile.add(new Projectile(0, turr[i].x, turr[i].y, turr[i].x3, turr[i].y3));
                                projectile.add(new Projectile(0, turr[i].x, turr[i].y, turr[i].x3, turr[i].y3*2));
                            }}}}}
            class PlayerMover implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    if (pyforce >= 0) for(int i = 0; i < 20; i++) if((py < plats[i].y+6 && py > plats[i].y-6) && (px-7*1500/690 < plats[i].x+30*1500/690 && px+7*1500/690 > plats[i].x-30*1500/690)) {pyforce = 0; py = plats[i].y-6; onGround = true;}
                    if(py<=1547 && pyforce<11) pyforce+=1; //gravity and terminal velocity
                    if(py>1547){pyforce=0; py=1547; onGround = true; healthLoss(10);} //upward force countering gravity when standing on bottom of screen and health reduction
                    if(py < 0) healthLoss(1); //deters going too high
                    if(wpress && onGround) {pyforce-=22; onGround = false;}
                    if(apress && pxforce>-40) pxforce-=2; //horizontal movement force application
                    if(dpressed && pxforce<40) pxforce+=2;
                    px+=pxforce; //movement
                    py+=pyforce;
                    pxforce *= 0.9;
                    if(px>970) {pxforce=0; px = 970;} //side barriers
                    if(px<25) {pxforce=0; px = 25;}
                    repaint();
                    grabFocus();
                }}
            public void healthLoss(int reduction){
                if(loseHealth && !godmode){
                    health -= reduction;
                    g2.drawImage(blood, px-10*1500/690, py-60*1500/690, 43, 130, null);
                    stats.repaint();
                }
                if(health<=0){
                    loseHealth = false;
                    cL.show(display, "link5");
                    scoretime.stop();
                }}
            class PlatMover implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < 20; i++){
                        plats[i].y += 2*speedmultiplier;
                        if(plats[i].y > 1547) plats[i] = new Platform();
                    }
                    repaint();
                    grabFocus();
                }}
            class PowerMover implements ActionListener{
                public void actionPerformed(ActionEvent e) {
                    for(int i = 0; i < 20; i++){
                        if(pows[i].inplay){
                            pows[i].y += 2*speedmultiplier;
                            if(pows[i].y > 1527) pows[i] = new PowerUp();
                            repaint();
                            grabFocus();
                        }
                        else {
                            if((int)(Math.random()*100000+1)==1){
                                turr[i].inplay = true;
                                turr[i].y = plats[20].y+10;
                                turr[i].x = plats[20].x;
                        }}}}}
            class TurretMover implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < 39; i++){
                        if(turr[i].inplay){
                            turr[i].y += 2*speedmultiplier;
                            if(turr[i].y > 1527) turr[i] = new Turret();
                            repaint();
                            grabFocus();
                        }
                        else if(difficulty == 0) turr[i].inplay = (int)(Math.random()*20000+1)==1;
                        else if(difficulty == 1) turr[i].inplay = (int)(Math.random()*18000+1)==1;
                        else if(difficulty == 2) turr[i].inplay = (int)(Math.random()*10000+1)==1;
                    }}}
            class ProjectileMover implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < projectile.size(); i++){
                        projectile.get(i).x += projectile.get(i).changex*speedmultiplier;
                        projectile.get(i).y += projectile.get(i).changey*speedmultiplier;
                        if(projectile.get(i).x < 20 || projectile.get(i).x > 975) projectile.remove(i);
                        else if(projectile.get(i).y < 20 || projectile.get(i).y > 1527) projectile.remove(i);
                        else if(projectile.get(i).x-20 < px+21 && projectile.get(i).x+20 > px-21 && projectile.get(i).y+20 > py-130 && projectile.get(i).y-20 < py) {
                            healthLoss(projectile.get(i).hreduct);
                            if(projectile.get(i).burner) {burning = true; burnMaxTime = projectile.get(i).btime; btime.start();}
                            projectile.remove(i);
                        }}}}
            class BurnApp implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    if(curBurnTime == burnMaxTime) {
                        btime.stop();
                        curBurnTime = 0;
                        burning = false;
                    }
                    else {
                        curBurnTime++;
                        health--;
                    }}}
            class Platform{
                int x, y;
                public Platform(){
                    x = (int)(Math.random()*920);
                    y = 1547-20*80;
                }}
            class PowerUp{
                int x, y, type;
                boolean inplay;
                public PowerUp(){
                    type = (int)(Math.random()*4);
                    x = -50; y = -50;
                    inplay = false;
                }}
            class Turret{
                int x, y, type, shoot, x3, y3;
                boolean inplay;
                public Turret(){
                    shoot = 0;
                    if((int)(Math.random()*2)==0) x = -20;
                    else x = 975;
                    y = -50;
                    int ran;
                    if(difficulty == 0) type = 0;
                    else if(difficulty == 1){
                        ran = (int)(Math.random()*3+1);
                        if(ran == 1) type = 0;
                        else if(ran == 2) type = 1;
                        else type = 3;
                    }
                    else if(difficulty == 2){
                        ran = (int)(Math.random()*5+1);
                        if(ran == 1) type = 0;
                        else if(ran == 2) type = 1;
                        else if(ran == 3) type = 3;
                        else if(ran == 4) type = 4;
                        else if(ran == 5) type = 2;
                    }
                    inplay = false;
                }}
            class Projectile{
                int type, x, y, changex, changey, hreduct, btime;
                boolean burner;
                public Projectile(int tp, int tx, int ty, int x3, int y3){
                    x = tx+x3+20; y = ty+y3+20;
                    changex = x3; changey = y3;
                    type = tp;
                    hreduct = 5;
                    burner = false; btime = 0;
                }
                public Projectile(int tp, int tx, int ty, int x3, int y3, int burntime){
                    x = tx+x3+20; y = ty+y3+20;
                    changex = x3; changey = y3;
                    type = tp;
                    hreduct = 10;
                    burner = true; btime = burntime;
                }}
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '/'){
                    String command = scan.next();
                    if(command.equals("godmode_on")) godmode = true;
                    if(command.equals("godmode_off")) godmode = false;
                    if(command.equals("set_score")) score = scan.nextInt();
                    if(command.equals("stop_score")) scoretime.stop();
                    if(command.equals("start_score")) scoretime.start();
                    if(command.equals("kill")) health = 0;
                    if(command.equals("heal")) health = 1000;
                }
                else if(e.getKeyChar() == 'h') health += 10;
            }
            public void keyPressed(KeyEvent e){
                if(e.getKeyChar() == 'a') apress = true;
                if(e.getKeyChar() == 'd') dpressed = true;
                if(e.getKeyChar() == 'w' && wallowpress) wpress = true;
                if(e.getKeyCode() == KeyEvent.VK_LEFT) apress = true;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) dpressed = true;
                if(e.getKeyCode() == KeyEvent.VK_UP && wallowpress) wpress = true;
            }
            public void keyReleased(KeyEvent e){
                if(e.getKeyChar() == 'a') apress = false;
                if(e.getKeyChar() == 'd') dpressed = false;
                if(e.getKeyChar() == 'w') {wpress = false; wallowpress = true;}
                if(e.getKeyCode() == KeyEvent.VK_LEFT) apress = false;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) dpressed = false;
                if(e.getKeyCode() == KeyEvent.VK_UP) {wpress = false; wallowpress = true;}
            }}}}