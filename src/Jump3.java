//Orion Yap Stick Jump! The game
import java.awt.*; import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter; import java.io.PrintWriter;
import java.io.File; import java.io.FileNotFoundException;
import java.io.IOException; import java.util.Scanner;
public class Jump3 extends JFrame{ //starter class: makes frame and calls panel class
    Scanner scan; JFrame frame; String prefix = "src/";
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
        int difficulty, HAFrame, health, maxh, score, gAllow, rAllow;
        int powsPicked, damageTaken, healedFor, godUsed, revUsed;
        String character, username;
        String[][] lead = new String[10][2];
        Scanner FReader; Font f1; File leadFile;
        Image back; ImageIcon c21, c22, c23; Image city, city2, city3;
        Image[] bloods;
        CardLayout cL; Timer scoretime;
        JLabel c1, c2, c3, l2label, l3label, mine, val; JTextField name;
        JTextArea charMessage, nameMessage, settingsMessage;
        JButton again, helpB, leader, lvl1, lvl2, lvl3, manB, playB, settingsB, stickB, storyB, womanB;
        JButton[] homeB;
        JPanel butP, display, end, esnav, homeP, leaderP, lvlSelect, playP, settings;
        PrintWriter out;
        Board board; HelpP helpP; Home home; Stats stats; Items items; StoryP storyP; EndScreen es; Leaderboard lb;
        //sets panels and buttons and calls runner
        public Pan(){
            //initialize panels and buttons
            homeB = new JButton[6];
            for(int i = 0; i < 6; i++){
                homeB[i] = new JButton("Home");
                homeB[i].setBackground(Color.BLACK);
                homeB[i].setForeground(Color.WHITE);
                homeB[i].addActionListener(e -> cL.show(display, "link1"));
                homeB[i].setFont(new Font("Serif", Font.PLAIN, 80));
            }
            again = new JButton("Try again?");
            ImageIcon play = new ImageIcon(prefix + "play.png");
            playB = new JButton("Play!", play);
            ImageIcon gear = new ImageIcon(prefix + "gear.png");
            helpB = new JButton("Help");
            storyB = new JButton(" Story");
            settingsB = new JButton(gear);
            ImageIcon leaderIcon = new ImageIcon(prefix + "leaderboard.png");
            leader = new JButton(leaderIcon);
            helpP = new HelpP(); stats = new Stats(); home = new Home();
            storyP = new StoryP();
            display = new JPanel(); playP = new JPanel(); items = new Items(); butP = new JPanel(); end = new JPanel(); homeP = new JPanel();
            settings = new JPanel(); lvlSelect = new JPanel(); leaderP = new JPanel();
            val = new JLabel(new ImageIcon(prefix + "valorant.png"));
            mine = new JLabel(new ImageIcon(prefix + "minecraft2.png"));
            maxh = 100;
            health = maxh; score = 0;
            first = bcolor = false;
            Score scorekeep = new Score();
            scoretime = new Timer(1000, scorekeep);
            leadFile = new File(prefix + "leaderboard.txt");
            name = new JTextField("Username");
            username = "User0001";
            character = "Stick Figure";
            settingsMessage = new JTextArea("Username: "+username+"\nCharacter: "+character);
            try {FReader = new Scanner(leadFile);}
            catch (FileNotFoundException e){
                System.err.println("Error: file not found");
                System.exit(1);
            }
            nameMessage = new JTextArea("Enter username below (max 10 characters)");
            nameMessage.setEditable(false);
            charMessage = new JTextArea("Choose character type below");
            charMessage.setEditable(false);
            lvl1 = new JButton();
            lvl2 = new JButton();
            lvl3 = new JButton();
            f1 = new Font("Serif", Font.PLAIN, 50);
            back = new ImageIcon(prefix + "window_background.png").getImage();
            c21 = new ImageIcon(prefix + "cityback11.png");
            c22 = new ImageIcon(prefix + "cityback21.png");
            c23 = new ImageIcon(prefix + "cityback31.png");
            c1 = new JLabel(c21); c2 = new JLabel(c22); c3 = new JLabel(c23);
            city = new ImageIcon(prefix + "cityback2.png").getImage();
            city2 = new ImageIcon(prefix + "cityBlue.png").getImage();
            city3 = new ImageIcon(prefix + "cityPurple.png").getImage();
            l2label = new JLabel(); l3label = new JLabel();
            stickB = new JButton("Stick Figure");
            manB = new JButton("Man");
            womanB = new JButton("Woman");
            HAFrame = 1;
            bloods = new Image[15];
            bloods[0] = new ImageIcon(prefix + "blood/tile000.png").getImage();
            bloods[1] = new ImageIcon(prefix + "blood/tile001.png").getImage();
            bloods[2] = new ImageIcon(prefix + "blood/tile002.png").getImage();
            bloods[3] = new ImageIcon(prefix + "blood/tile003.png").getImage();
            bloods[4] = new ImageIcon(prefix + "blood/tile005.png").getImage();
            bloods[5] = new ImageIcon(prefix + "blood/tile006.png").getImage();
            bloods[6] = new ImageIcon(prefix + "blood/tile007.png").getImage();
            bloods[7] = new ImageIcon(prefix + "blood/tile008.png").getImage();
            bloods[8] = new ImageIcon(prefix + "blood/tile009.png").getImage();
            bloods[9] = new ImageIcon(prefix + "blood/tile010.png").getImage();
            bloods[10] = new ImageIcon(prefix + "blood/tile011.png").getImage();
            bloods[11] = new ImageIcon(prefix + "blood/tile012.png").getImage();
            bloods[12] = new ImageIcon(prefix + "blood/tile013.png").getImage();
            bloods[13] = new ImageIcon(prefix + "blood/tile014.png").getImage();
            bloods[14] = new ImageIcon(prefix + "blood/tile015.png").getImage();
            runner();
        }
        class Score implements ActionListener{public void actionPerformed(ActionEvent e){ //changes score in game
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
            leaderP.setLayout(null);
            leaderP.setBackground(Color.BLACK);
            //Button setup
            again.setBackground(Color.BLACK); again.setForeground(Color.WHITE); color = false;
            settingsB.setBackground(Color.BLACK);
            playB.setBackground(Color.BLACK); playB.setForeground(Color.WHITE); playB.setFont(new Font("Serif", Font.PLAIN, 80));
            helpB.setBackground(Color.BLACK); helpB.setForeground(Color.WHITE); helpB.setFont(new Font("Serif", Font.PLAIN, 80));
            storyB.setBackground(Color.BLACK); storyB.setForeground(Color.WHITE); storyB.setFont(new Font("Serif", Font.PLAIN, 80));
            leader.setBackground(Color.BLACK); leader.setForeground(Color.WHITE); leader.setFont(new Font("Serif", Font.PLAIN, 80));
            LevelAct la = new LevelAct();
            again.addActionListener(la);
            playB.addActionListener(la);
            helpB.addActionListener(e -> cL.show(display, "link3"));
            storyB.addActionListener(e -> cL.show(display, "link4"));
            settingsB.addActionListener(e -> cL.show(display, "link6"));
            LAction laction = new LAction();
            leader.addActionListener(laction);
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
            charMessage.setFont(f1);
            charMessage.setBackground(Color.WHITE);
            settings.add(charMessage);
            charMessage.setBounds(50, 420, 900, 60);
            stickB.addActionListener(new Stick());
            manB.addActionListener(new Man());
            womanB.addActionListener(new Woman());
            stickB.setFont(f1);
            manB.setFont(f1);
            womanB.setFont(f1);
            settings.add(stickB);
            stickB.setBounds(50, 500, 300, 60);
            settings.add(manB);
            manB.setBounds(400, 500, 150, 60);
            settings.add(womanB);
            womanB.setBounds(600, 500, 200, 60);
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
            c1.setBounds(50, 5, 1400, 420);
            lvlSelect.add(c2);
            c2.setBounds(50, 495, 1400, 420);
            lvlSelect.add(c3);
            c3.setBounds(50, 985, 1400, 420);
            lvlSelect.add(lvl1);
            lvl1.setBounds(50, 5, 1400, 420);
            lvlSelect.add(lvl2);
            lvl2.setBounds(50, 495, 1400, 420);
            lvlSelect.add(lvl3);
            lvl3.setBounds(50, 985, 1400, 420);
            l2label.setFont(new Font("Serif", Font.BOLD, 60));
            l3label.setFont(new Font("Serif", Font.BOLD, 60));
            l2label.setHorizontalAlignment(SwingConstants.CENTER);
            l3label.setHorizontalAlignment(SwingConstants.CENTER);
            lvlSelect.add(l2label);
            l2label.setBounds(50, 425, 1400, 65);
            lvlSelect.add(l3label);
            l3label.setBounds(50, 915, 1400, 65);
            lvlSelect.add(homeB[4]);
            homeB[4].setBounds(0, 1447, 1500, 100);
            //leaderboard panel setup
            lb = new Leaderboard();
            leaderP.add(lb);
            lb.setBounds(0, 0, 1500, 1447);
            leaderP.add(homeB[5]);
            homeB[5].setBounds(0, 1447, 1500, 100);
            //nav panel (buttons) setup
            butP.add(helpB);
            helpB.setBounds(100, 0, 100*100/30-100,  100);
            butP.add(playB);
            playB.setBounds(100*100/30, 0, 110*100/30, 100);
            butP.add(storyB);
            storyB.setBounds(100*100/30+110*100/30, 0, 70*100/30, 100);
            butP.add(settingsB);
            settingsB.setBounds(0, 0, 100, 100);
            butP.add(leader);
            leader.setBounds(100*100/30+110*100/30+70*100/30, 0, 100, 100);
            //filler
            butP.add(val);
            val.setBounds(100*100/30+110*100/30+70*100/30+200-100, 0, 100, 100);
            butP.add(mine);
            mine.setBounds(100*100/30+110*100/30+70*100/30+300-100, 0, 100, 100);
            //home panel setup
            homeP.add(butP, BorderLayout.SOUTH);
            homeP.add(home);
            HomeAni ha = new HomeAni();
            Timer hatime = new Timer(100, ha);
            hatime.start();
            //display panel setup
            display.add(homeP, "link1");
            display.add(lvlSelect, "link2");
            display.add(helpP, "link3");
            display.add(storyP, "link4");
            display.add(end, "link5");
            display.add(settings, "link6");
            display.add(playP, "link7");
            display.add(leaderP, "link8");
            //also end screen setup
            esnav = new JPanel();
            esnav.setLayout(null);
            esnav.setPreferredSize(new Dimension(0, 100));
            again.setFont(new Font("Serif", Font.BOLD, 80));
            esnav.add(homeB[3]);
            homeB[3].setBounds(0, 0, 1500/2, 100);
            esnav.add(again);
            again.setBounds(1500/2, 0, 1500/2, 100);
            Again alistener = new Again();
            Timer atime = new Timer(1000, alistener);
            atime.start();
            //main panel setup
            add(display);
        }
        class LAction implements ActionListener{public void actionPerformed(ActionEvent e){ //updates leaderboard
            lb = new Leaderboard();
            cL.show(display, "link8");
        }}
        class Leaderboard extends JPanel{ //leaderboard panel
            public void paintComponent(Graphics g){
                try {FReader = new Scanner(leadFile);}
                catch (FileNotFoundException e){
                    System.err.println("Error: file not found");
                    System.exit(1);
                }
                String player; String score;
                g.setFont(new Font("Serif", Font.PLAIN, 50));
                g.setColor(Color.WHITE);
                g.drawString("Pos", 110, 110);
                g.drawString("Player", 400, 110);
                g.drawString("Score", 900, 110);
                for(int i = 0; i < 10; i++){
                    if(i == 0) g.setColor(Color.decode("#8d00cf"));
                    else if(i == 1) g.setColor(Color.decode("#947f06"));
                    else if(i == 2) g.setColor(Color.decode("#00c8ff"));
                    else if(i == 3) g.setColor(Color.decode("#28f928"));
                    else g.setColor(Color.WHITE);
                    score = FReader.next();
                    player = FReader.next();
                    g.drawString(i+1+"", 110, 170+i*60);
                    g.drawString(player, 400, 170+i*60);
                    g.drawString(score, 900, 170+i*60);
                }}}
        class Name implements ActionListener{public void actionPerformed(ActionEvent e){ //action listener for username
                if(name.getText().length() <= 10 || name.getText().equals("riptide30125")) {
                    username = name.getText();
                    name.setText("");
                    settingsMessage.setText("Username: "+username+"\nCharacter: "+character);
                }
                else name.setText("Too long");
            }}
        class Stick implements ActionListener{public void actionPerformed(ActionEvent e){ //changes character to stick figure
            character = "Stick Figure";
            settingsMessage.setText("Username: "+username+"\nCharacter: "+character);
        }}
        class Man implements ActionListener{public void actionPerformed(ActionEvent e){ //char to man
            character = "Man";
            settingsMessage.setText("Username: "+username+"\nCharacter: "+character);
        }}
        class Woman implements ActionListener{public void actionPerformed(ActionEvent e){ //char to woman
            character = "Woman";
            settingsMessage.setText("Username: "+username+"\nCharacter: "+character);
        }}
        class LevelAct implements ActionListener{public void actionPerformed(ActionEvent e){ //updates score/unlocks
            if(score < 50) l2label.setText("Unlock level 2: "+score+" / 50");
            else l2label.setText("Level 2 unlocked: "+score+" / 50");
            if(score < 200) l3label.setText("Unlock level 3: "+score+" / 200");
            else l3label.setText("Level 3 unlocked: "+score+" / 200");
            cL.show(display, "link2");
        }}
        class PlayAct1 implements ActionListener{public void actionPerformed(ActionEvent e){ //level 1 button
                if(first) board.reset(0);
                else {board = new Board(0); first = true;}
                playActStandard();
        }}
        class PlayAct2 implements ActionListener{public void actionPerformed(ActionEvent e){ //level 2 button
                if(score >= 50){
                    if(first) board.reset(1);
                    else {board = new Board(1); first = true;}
                    playActStandard();
            }}}
        class PlayAct3 implements ActionListener{public void actionPerformed(ActionEvent e){ //level 3 button
                if(score >= 200){
                    if(first) board.reset(2);
                    else {board = new Board(2); first = true;}
                    playActStandard();
            }}}
        public void playActStandard(){
            //play panel setup
            score = 0;
            godUsed = 0;
            revUsed = 0;
            damageTaken = 0;
            powsPicked = 0;
            healedFor = 0;
            stats = new Stats();
            items.setPreferredSize(new Dimension(110*1500/690, 1547));
            stats.setPreferredSize(new Dimension(123*1500/690, 1547));
            playP.add(items, BorderLayout.WEST);
            playP.add(stats, BorderLayout.EAST);
            playP.add(board, BorderLayout.CENTER);
            cL.show(display, "link7");
        }
        class BurnText implements ActionListener{public void actionPerformed(ActionEvent e){bcolor = !bcolor;}}
        class Home extends JPanel{ //home panel (drawings)
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
                g.setFont(new Font("Serif", Font.PLAIN, 100));
                g.setColor(Color.BLACK);
                g.drawString("Stick Jump!", 100, 1000);
                g.drawImage(back, -10*1500/690, -70*1500/690, 800*1500/690, 610*1500/690+300, null);
                //first portal
                Image p1 = new ImageIcon(prefix + "portal1.png").getImage();
                g2.drawImage(p1, 600*1500/690-10, 100*1500/690+100, 100, 152, null);
                //second portal
                Image p2 = new ImageIcon(prefix + "portal2.png").getImage();
                g2.drawImage(p2, 65*1500/690-90, 350*1500/690+100, 100, 152, null);
                Image pgun = new ImageIcon(prefix + "portal_gun.png").getImage();
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
                Image mc = new ImageIcon(prefix + "minecraft_logo.png").getImage();
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
                Image fire = null;
                if(HAFrame == 1) fire = new ImageIcon(prefix + "fireF1.png").getImage();
                else if(HAFrame == 2) fire = new ImageIcon(prefix + "fireF3.png").getImage();
                else if(HAFrame == 3) fire = new ImageIcon(prefix + "fireF4.png").getImage();
                else if(HAFrame == 4) fire = new ImageIcon(prefix + "fireF5.png").getImage();
                g.drawImage(fire, 285*1500/690, 210*1500/690+100, 50*1500/690, 50*1500/690, null);
                g.drawImage(fire, 355*1500/690, 210*1500/690+100, 50*1500/690, 50*1500/690, null);
            }}
        class HomeAni implements ActionListener{public void actionPerformed(ActionEvent e){ //fire gif
            if(HAFrame < 4) HAFrame++;
            else HAFrame = 1;
            homeP.repaint();
        }}
        class Stats extends JPanel{ //stats panel
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
        class Items extends JPanel{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                if(score < 0) setBackground(Color.RED);
                else if(difficulty == 0) setBackground(Color.decode("#28f928"));
                else if(difficulty == 1) setBackground(Color.decode("#00c8ff"));
                else if(difficulty == 2)setBackground(Color.decode("#8d00cf"));
                //godmode label
                g.setFont(new Font("Serif", Font.PLAIN, 40));
                g.drawString("Godmode (g)", 5, 70);
                //godmode cooldown
                g.setColor(Color.WHITE);
                g.fillRoundRect(5, 80, 200, 20, 10, 10);
                g.setColor(Color.BLACK);
                g.drawRoundRect(5, 80, 200, 20, 10, 10);
                g.fillRoundRect(5, 80, gAllow/25, 20, 10, 10);
                //reverse label
                g.drawString("Reverse (r)", 5, 140);
                //reverse cooldown
                g.setColor(Color.WHITE);
                g.fillRoundRect(5, 150, 200, 20, 10, 10);
                g.setColor(Color.BLACK);
                g.drawRoundRect(5, 150, 200, 20, 10, 10);
                g.fillRoundRect(5, 150, rAllow/25, 20, 10, 10);
            }}
        class EndScreen extends JPanel{ //death panel
            public EndScreen(){setBackground(Color.BLACK);}
            public void paintComponent(Graphics g){
                g.setColor(Color.WHITE);
                g.setFont(new Font("Serif", Font.PLAIN, 100));
                g.drawString("Your score was:", 30, 100);
                if(score < 0) g.setColor(Color.RED);
                else if(score <= 50) g.setColor(Color.decode("#28f928"));
                else if(score <= 200) g.setColor(Color.decode("#00c8ff"));
                else if(score <= 400) g.setColor(Color.decode("#8d00cf"));
                else if(color) g.setColor(Color.decode("#947f06"));
                else g.setColor(Color.decode("#8d00cf"));
                g.drawString(""+score, 100, 205);
                g.setColor(Color.decode("#940000"));
                g.drawString("Damage taken: "+damageTaken, 30, 325);
                g.setColor(Color.decode("#ff0000"));
                g.drawString("Health regained: "+healedFor, 30, 450);
                g.setColor(Color.decode("#c2aa30"));
                g.drawString("Times godmoded: "+godUsed, 30, 575);
                g.drawString("Times reversed: "+revUsed, 30, 700);
                g.setColor(Color.decode("#8d00cf"));
                g.drawString("Powers picked up: "+powsPicked, 30, 825);
            }}
        class HelpP extends JPanel{
            public HelpP() {
                setLayout(new BorderLayout());
                Help help = new Help();
                add(help);
                homeB[1].setPreferredSize(new Dimension(0, 100));
                add(homeB[1], BorderLayout.SOUTH);
            }}
        class Help extends JPanel{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                setBackground(Color.WHITE);
                g.setFont(new Font("Serif", Font.PLAIN, 50));
                ArrayList<String> txt = new ArrayList();
                txt.add("The Game");
                txt.add("1. Use keys W, A, and D to move");
                txt.add("2. Use key \"g\" to enable godmode for 0.5 seconds (no damage taken)");
                txt.add("   You are able to use godmode every 10 seconds, indicated by if the bar is fully white");
                txt.add("3. Use key \"r\" to reverse projectiles, indicated by if the bar is fully white");
                txt.add("   You are able to use reverse every 5 seconds");
                txt.add("4. Jump from platform to platform to avoid falling into the void");
                txt.add("5. Every second you survive you get one point");
                txt.add("");
                txt.add("Power Ups");
                txt.add("1. Point Gamble: + or - 40 points");
                txt.add("2. Health Regen 20: +20 health");
                txt.add("3. Extra Max Health: +10 max health, either with no effect or sets health to 10");
                txt.add("4. Health Regen Max: player health is set to max");
                txt.add("");
                txt.add("Special feature: sticky keys");
                txt.add("The cursor has a special power that makes the controls get stuck: ");
                txt.add("simply just press the key again to get it unstuck");
                for(int i = 0; i < txt.size(); i++){
                    g.drawString(txt.get(i), 10, 110+i*60);
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
                    Image cursor = new ImageIcon(prefix + "cursor.png").getImage();
                    g.drawImage(cursor, 380*1500/690, 50*1500/690, 20*1500/690, 25*1500/690, null);
                    //wind lines
                    g.drawLine(200*1500/690, 30*1500/690, 250*1500/690, 30*1500/690);
                    g.drawLine(200*1500/690, 60*1500/690, 250*1500/690, 60*1500/690);
                    g.drawLine(200*1500/690, 90*1500/690, 250*1500/690, 90*1500/690);
                    g.drawLine(200*1500/690, 120*1500/690, 250*1500/690, 120*1500/690);
                    g.drawLine(400*1500/690, 60*1500/690, 420*1500/690, 60*1500/690);
                    g.drawLine(400*1500/690, 65*1500/690, 420*1500/690, 65*1500/690);
                }}}
        public void gameOver(){ //method for when game ends
            int k = 9;
            //end screen setup
            es = new EndScreen();
            end.add(esnav, BorderLayout.SOUTH);
            end.add(es, BorderLayout.CENTER);
            cL.show(display, "link5");
            maxh = 100; health = maxh;
            scoretime.stop();
            try {FReader = new Scanner(leadFile);}
            catch (FileNotFoundException e){
                System.err.println("Error: file not found");
                System.exit(1);
            }
            for(int i = 0; i < 10; i++) for(int j = 0; j < 2; j++) lead[i][j] = FReader.next();
            for(int i = 0; i < 10; i++) if(Integer.parseInt(lead[i][0]) < score){
                while(k > i){
                    for(int j = 0; j < 2; j++) lead[k][j] = lead[k-1][j]; //what's System.arraycopy lol
                    k--;
                }
                k = 9;
                lead[i][0] = score+"";
                lead[i][1] = username;
                i = 10;
            }
            try {out = new PrintWriter(prefix + "leaderboard.txt");}
            catch (FileNotFoundException e){
                System.err.println("Error: file not created");
                System.exit(1);
            }
            for(int i = 0; i < 10; i++) out.println(lead[i][0]+" "+lead[i][1]);
            out.close();
        }
        class Again implements ActionListener{public void actionPerformed(ActionEvent e){ //changes color of again button
            color = !color;
            if(color) again.setForeground(Color.GREEN);
            else again.setForeground(Color.WHITE);
            esnav.repaint();
        }}
        //Actual game
        class Board extends JPanel implements KeyListener{
            int px, py, pxforce, pyforce, tlength, burnMaxTime, curBurnTime, tvelocity, pheight, highPlat;
            int janiframe, baniframe;
            double speedmultiplier;
            boolean wpress, apress, dpressed, wallowpress, onGround, loseHealth, godmode, up, bleeding, rmode;
            PlatMover platmove; PlayerMover pmove; TurretMover turmove; ProjectileMover projmove; ProjSpawner pspawn; BurnApp burn;
            JumpAni ja; PowerMover powmove; GMTimer gmt; GMCool gmc; RCool rc; BloodAni bani;
            Timer ptime, platime, turtime, projtime, pstime, btime, jtime, powtime, gmtime, gmctime, rctime, banitime;
            Image blood, fireball, man, woman;
            Image scoreMod, maxhMod, health20;
            Platform[] plats = new Platform[20]; PowerUp[] pows = new PowerUp[20]; Turret[] turr = new Turret[39];
            ArrayList<Projectile> projectile = new ArrayList();
            ArrayList<Laser> laser = new ArrayList();
            Graphics2D g2;
            public Board(int diff){
                reset(diff);
                baniframe = -1;
                bleeding = false;
                pheight = 6;
                tvelocity = 2*pheight;
                addKeyListener(this);
                powmove = new PowerMover();
                powtime = new Timer(3, powmove);
                powtime.start();
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
                ja = new JumpAni();
                jtime = new Timer(10, ja);
                gmt = new GMTimer();
                gmtime = new Timer(500, gmt);
                gmc = new GMCool();
                gmctime = new Timer(1, gmc);
                rc = new RCool();
                rctime = new Timer(1, rc);
                bani = new BloodAni();
                banitime = new Timer(100, bani);
                godmode = false;
                tlength = 40;
                blood = new ImageIcon(prefix + "blood.png").getImage();
                fireball = new ImageIcon(prefix + "fireball.png").getImage();
                scoreMod = new ImageIcon(prefix + "power_scoremodi.png").getImage();
                health20 = new ImageIcon(prefix + "20sign.png").getImage();
                maxhMod = new ImageIcon(prefix + "power_maxh.png").getImage();
                man = new ImageIcon(prefix + "man.png").getImage();
                woman = new ImageIcon(prefix + "woman.png").getImage();
            }
            public void reset(int difficult){
                gAllow = 0;
                rAllow = 0;
                highPlat = 19;
                janiframe = -1;
                up = true;
                difficulty = difficult;
                burning = false;
                if(difficulty == 0) speedmultiplier = 0.5;
                else if(difficulty == 1) speedmultiplier = 1;
                else if(difficulty == 2) speedmultiplier = 2;
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
                if(difficulty == 0) g2.setColor(Color.BLACK);
                else if(difficulty == 1) g2.setColor(Color.WHITE);
                else if(difficulty == 2) g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(pheight));
                for(int i = 0; i < 20; i++) g2.drawLine(plats[i].x-30*1500/690, plats[i].y, plats[i].x+30*1500/690, plats[i].y);//rendering Platforms
                for(int i = 0; i < 39; i++){ //rendering turrets
                    if(turr[i].inplay) {
                        if(turr[i].type == 1) g2.setColor(Color.decode("#940a00"));
                        else g2.setColor(Color.GRAY);
                        g2.fillOval(turr[i].x, turr[i].y, 40, 40);
                        g2.setStroke(new BasicStroke(20));
                        g2.setColor(Color.GRAY);
                        //failure case: player is "inside" turret
                        //calculates where turret will end while still pointing to (middle of) player
                        if(!turr[i].shooting) {
                            turr[i].x3 = (int) (((px - turr[i].x + 20) * tlength) / Math.sqrt(Math.pow(px - turr[i].x + 20, 2) + Math.pow(py - turr[i].y, 2)));
                            turr[i].y3 = (int) (((py - 130 - turr[i].y + 20) * tlength) / Math.sqrt(Math.pow(px - turr[i].x + 20, 2) + Math.pow(py - 130 - turr[i].y, 2)));
                        }
                        g2.drawLine(turr[i].x + 20, turr[i].y + 20, turr[i].x+20+turr[i].x3, turr[i].y+20+turr[i].y3);
                        if(turr[i].type == 2 && turr[i].shoot == 1) {g2.setColor(Color.decode("#ccb21d")); g2.fillOval(turr[i].x+5, turr[i].y+5, 30, 30);}
                        if(turr[i].type == 2 && turr[i].shoot == 2) {g2.setColor(Color.decode("#cc8f1d")); g2.fillOval(turr[i].x+5, turr[i].y+5, 30, 30);}
                        if(turr[i].type == 2 && turr[i].shoot == 3) {g2.setColor(Color.decode("#cc3a1d")); g2.fillOval(turr[i].x+5, turr[i].y+5, 30, 30);}
                    }}
                if(difficulty == 0) g2.setColor(Color.BLACK);
                else if(difficulty == 1) g2.setColor(Color.WHITE);
                else if(difficulty == 2) g2.setColor(Color.BLACK);
                for(int i = 0; i < projectile.size(); i++) { //rendering projectiles
                    if(projectile.get(i).type==0||projectile.get(i).type==3||projectile.get(i).type==4||projectile.get(i).type==5){
                        g.setColor(Color.BLACK);
                        g.fillOval(projectile.get(i).x - 20, projectile.get(i).y - 20, 40, 40);
                    }
                    else g.drawImage(fireball, projectile.get(i).x-20, projectile.get(i).y-20, 40, 40, null);
                }
                for(int i = 0; i < laser.size(); i++){ //rendering lasers
                    g2.setColor(new Color(255, 255, 255, 0.2f));
                    g2.setStroke(new BasicStroke(5));
                    g2.drawLine(laser.get(i).startx, laser.get(i).starty, laser.get(i).endx, laser.get(i).endy);
                    g2.setColor(laser.get(i).LColor[laser.get(i).laniframe]);
                    g2.setStroke(new BasicStroke(3));
                    g2.drawLine(laser.get(i).startx, laser.get(i).starty, laser.get(i).endx, laser.get(i).endy);
                }
                for(int i = 0; i < 20; i++){ //rendering power ups
                    if(pows[i].inplay){
                        if(pows[i].type == 0) g.drawImage(scoreMod, pows[i].x-50, pows[i].y-100, 100, 100, null);
                        else if(pows[i].type == 1) g.drawImage(health20, pows[i].x-50, pows[i].y-100, 100, 100, null);
                        else if(pows[i].type == 2) g.drawImage(maxhMod, pows[i].x-50, pows[i].y-100, 100, 100, null);
                        else if(pows[i].type == 3) {g.setColor(Color.RED); g.fillRect(pows[i].x-40, pows[i].y-100, 80, 80);}
                    }}
                switch (character) {
                    case "Stick Figure":
                        if (difficulty == 0) g2.setColor(Color.BLACK);
                        else if (difficulty == 1) g2.setColor(Color.WHITE);
                        else if (difficulty == 2) g2.setColor(Color.BLACK); //rendering player
                        g2.setStroke(new BasicStroke(5));
                        g2.drawOval(px - 10 * 1500 / 690, py - 60 * 1500 / 690 + janiframe, 20 * 1500 / 690, 20 * 1500 / 690);
                        g2.drawLine(px, py - 40 * 1500 / 690 + janiframe, px, py - 20 * 1500 / 690);
                        g2.drawLine(px, py - 40 * 1500 / 690 + janiframe, px - 7 * 1500 / 690, py - 20 * 1500 / 690 + janiframe);
                        g2.drawLine(px, py - 40 * 1500 / 690 + janiframe, px + 7 * 1500 / 690, py - 20 * 1500 / 690 + janiframe);
                        g2.drawLine(px, py - 20 * 1500 / 690 + janiframe, px - 2 * 1500 / 690 - janiframe, py - 10 * 1500 / 690);
                        g2.drawLine(px, py - 20 * 1500 / 690 + janiframe, px + 2 * 1500 / 690 + janiframe, py - 10 * 1500 / 690);
                        g2.drawLine(px - 2 * 1500 / 690 - janiframe, py - 10 * 1500 / 690, px - 5 * 1500 / 690, py);
                        g2.drawLine(px + 2 * 1500 / 690 + janiframe, py - 10 * 1500 / 690, px + 5 * 1500 / 690, py);
                        break;
                    case "Man":
                        g2.drawImage(man, px - 20 * 1500 / 690, py - 139, 43, 139, null);
                        break;
                    case "Woman":
                        g2.drawImage(woman, px - 20 * 1500 / 690, py - 113, 43, 113, null);
                        break;
                }
                if(godmode){
                    g2.setColor(new Color(153, 204, 255, 127));
                    g2.fillOval(px-30, py-145, 60, 150);
                }
                //drawing blood
                if(baniframe != -1) g2.drawImage(bloods[baniframe], px-250, py-280, 512, 512, null);
            }
            class JumpAni implements ActionListener{public void actionPerformed(ActionEvent e){
                janiframe--;
                if(janiframe < 0) jtime.stop();
            }}
            class BloodAni implements ActionListener{public void actionPerformed(ActionEvent e){
                bleeding = true;
                if(baniframe < 14) baniframe++;
                else {baniframe = -1; banitime.stop(); bleeding = false;}
            }}
            class GMTimer implements ActionListener{public void actionPerformed(ActionEvent e){
                godmode = false;
                gmtime.stop();
                gmctime.start();
            }}
            class GMCool implements ActionListener{public void actionPerformed(ActionEvent e){
                if(gAllow > 0) {gAllow--; items.repaint();}
                else gmctime.stop();
            }}
            class RCool implements ActionListener{public void actionPerformed(ActionEvent e){
                if(rAllow > 0) {rAllow--; items.repaint();}
                else rctime.stop();
            }}
            class ProjSpawner implements ActionListener{public void actionPerformed(ActionEvent e){
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
            class PlayerMover implements ActionListener{public void actionPerformed(ActionEvent e){
                    if (pyforce >= 0) for(int i = 0; i < 20; i++) if((py < plats[i].y+pheight && py > plats[i].y-pheight) && (px-7*1500/690 < plats[i].x+30*1500/690 && px+7*1500/690 > plats[i].x-30*1500/690)) {pyforce = 0; py = plats[i].y-pheight; onGround = true;}
                    if(py<=1547 && pyforce<tvelocity) pyforce+=1; //gravity and terminal velocity
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
                    if(wpress){
                        up = true; janiframe = 5;
                        jtime.start();
                    }
                    repaint();
                    grabFocus();
                }}
            public void healthLoss(int reduction){
                if(loseHealth && !godmode){
                    health -= reduction;
                    g2.drawImage(blood, px-10*1500/690, py-60*1500/690, 43, 130, null);
                    stats.repaint();
                    if(!bleeding) banitime.start();
                    damageTaken+=reduction;
                }
                if(health<=0){loseHealth = false; gameOver();}}
            class PlatMover implements ActionListener{public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < 20; i++){
                        plats[i].y += 2*speedmultiplier;
                        if(plats[i].y > 1547) {plats[i] = new Platform(); highPlat = i;}
                    }
                    repaint();
                    grabFocus();
                }}
            class PowerMover implements ActionListener{public void actionPerformed(ActionEvent e) {
                    for(int i = 0; i < 20; i++){
                        if(pows[i].inplay){
                            pows[i].y += 2*speedmultiplier;
                            if(pows[i].y > 1527) pows[i] = new PowerUp();
                            if(pows[i].x+50 > px-20*1500/690 && pows[i].x-50 < px+20*1500/690 && pows[i].y-50 < py-10 && pows[i].y+50 > py){
                                pows[i].inplay = false;
                                powsPicked++;
                                if(pows[i].type == 0){
                                    if((int)(Math.random()*100+1) <= 50) score+=40;
                                    else if(score > 40) score -= 40;
                                    else score = 0;
                                }
                                else if(pows[i].type == 1){
                                    if(health+20 <= maxh){
                                        health += 20;
                                        healedFor += 20;
                                    }
                                    else{
                                        healedFor = maxh-health;
                                        health = maxh;
                                    }}
                                else if(pows[i].type == 2){
                                    maxh += 10;
                                    if((int)(Math.random()*100+1) <= 50) health = 20;
                                }
                                else if(pows[i].type == 3) {
                                    healedFor = maxh-health;
                                    health = maxh;
                                }}
                            repaint();
                            grabFocus();
                        }
                        else {
                            if(difficulty == 0 && (int)(Math.random()*20000+1)==1 && plats[highPlat].powAllow){
                                pows[i].inplay = true;
                                pows[i].y = plats[highPlat].y + 10;
                                pows[i].x = plats[highPlat].x;
                                plats[highPlat].powAllow = false;
                            }
                            else if(difficulty == 1 && (int)(Math.random()*18000+1)==1 && plats[highPlat].powAllow){
                                pows[i].inplay = true;
                                pows[i].y = plats[highPlat].y + 10;
                                pows[i].x = plats[highPlat].x;
                                plats[highPlat].powAllow = false;
                            }
                            else if(difficulty == 2 && (int)(Math.random()*16000+1)==1 && plats[highPlat].powAllow){
                                pows[i].inplay = true;
                                pows[i].y = plats[highPlat].y + 10;
                                pows[i].x = plats[highPlat].x;
                                plats[highPlat].powAllow = false;
                            }}}}}
            class TurretMover implements ActionListener{public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < 39; i++){
                        if(turr[i].inplay){
                            turr[i].y += 2*speedmultiplier;
                            if(turr[i].y > 1527) turr[i] = new Turret();
                            if(turr[i].type == 2 && (int)(Math.random()*1000+1) == 1 && !turr[i].shooting){
                                turr[i].shot.start();
                                turr[i].shooting = true;
                            }
                            if(turr[i].shoot == 3){
                                laser.add(new Laser(turr[i].x+20+turr[i].x3, turr[i].y+20+turr[i].y3, turr[i].x3, turr[i].y3));
                            }
                            repaint();
                            grabFocus();
                        }
                        else if(difficulty == 0) turr[i].inplay = (int)(Math.random()*20000+1)==1;
                        else if(difficulty == 1) turr[i].inplay = (int)(Math.random()*18000+1)==1;
                        else if(difficulty == 2) turr[i].inplay = (int)(Math.random()*10000+1)==1;
                    }}}
            class ProjectileMover implements ActionListener{public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < projectile.size(); i++){
                        //changing projectile pos
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
                boolean powAllow;
                public Platform(){
                    powAllow = true;
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
                boolean inplay, shooting;
                Timer shot;
                public Turret(){
                    shoot = 0;
                    shooting = false;
                    shot = new Timer(150, new Charge());
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
                        else if(ran == 5) type = 0;
                    }
                    inplay = false;
                }
                class Charge implements ActionListener{public void actionPerformed(ActionEvent e){
                    if(shoot < 3) shoot++;
                    else {shoot = 0; shot.stop(); shooting = false;}
                }}
            }
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
            class Laser{
                int startx, starty, endx, endy, laniframe;
                Timer LTime;
                Color[] LColor = new Color[5];
                public Laser(int x, int y, int changex, int changey){
                    startx = x; starty = y;
                    endx = 100*changex+startx; endy = 100*changey+starty;
                    LTime = new Timer(10, new LaserAni());
                    LColor[0] = new Color(51, 204, 255, 0.5f);
                    LColor[1] = new Color(51, 204, 255, 0.4f);
                    LColor[2] = new Color(51, 204, 255, 0.3f);
                    LColor[3] = new Color(51, 204, 255, 0.2f);
                    LColor[4] = new Color(51, 204, 255, 0.1f);
                }
                class LaserAni implements ActionListener{public void actionPerformed(ActionEvent e){
                    if(laniframe < 5) laniframe++;
                    else {laniframe = 0; LTime.stop(); laser.remove(this);}
                }}
            }
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '/') {
                    String command = scan.next();
                    if (command.equals("godmode_on")) godmode = true;
                    if (command.equals("godmode_off")) godmode = false;
                    if (command.equals("set_score")) score = scan.nextInt();
                    if (command.equals("stop_score")) scoretime.stop();
                    if (command.equals("start_score")) scoretime.start();
                    if (command.equals("kill")) health = 0;
                    if (command.equals("heal")) health = 1000;
                    if (command.equals("janiframe")) janiframe = scan.nextInt();
                    if (command.equals("rmode_on")) rmode = true;
                    if (command.equals("rmode_off")) rmode = false;
                    if (command.equals("reset_leader")) {
                        try {
                            out = new PrintWriter(prefix + "leaderboard.txt");
                        } catch (FileNotFoundException er) {
                            System.err.println("Error: file not created");
                            System.exit(1);
                        }
                        out.print("9999999 :)\n 0 none\n 0 none\n 0 none\n 0 none\n 0 none\n 0 none\n 0 none\n 0 none\n 0 none\n");
                        out.close();
                    }}
                else if(e.getKeyChar() == 'h') health += 10;
                else if(e.getKeyChar() == 'g' && gAllow == 0) {godmode = true; gmtime.start(); gAllow = 5000; godUsed++;}
                else if(e.getKeyChar() == 'r') {
                    revUsed++;
                    if(rAllow == 0) {
                        for(int i = 0; i < projectile.size(); i++) {projectile.get(i).changex *= -1; projectile.get(i).changey *= -1;}
                        if(!rmode) rctime.start(); rAllow = 5000;
                    }}}
            public void keyPressed(KeyEvent e){
                if(e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) apress = true;
                if(e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) dpressed = true;
                if((e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) && wallowpress) wpress = true;
            }
            public void keyReleased(KeyEvent e){
                if(e.getKeyChar() == 'a') apress = false;
                if(e.getKeyChar() == 'd') dpressed = false;
                if(e.getKeyChar() == 'w') {wpress = false; wallowpress = true;}
                if(e.getKeyCode() == KeyEvent.VK_LEFT) apress = false;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) dpressed = false;
                if(e.getKeyCode() == KeyEvent.VK_UP) {wpress = false; wallowpress = true;}
            }}}}