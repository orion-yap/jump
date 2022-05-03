/*
Orion Yap
Stick Jump! The game
Ik putting a lot of code on one line is bad but I wanted to save space since I have to print it out
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
//import org.jfugue.player.Player;
public class Jump3 extends JFrame{ //starter class: makes frame and calls panel class
    JFrame frame; Scanner scan;
    public static void main(String[] args){new Jump3();}
    public Jump3(){
        scan = new Scanner(System.in);
        frame = new JFrame("Stick Jump! v0.3");
        //board ratio: 690 to 460, 690 to 647
        //1500 to 690
        frame.setSize(1500, 1602);
        frame.setResizable(false);
        Pan pan = new Pan();
        frame.setContentPane(pan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    class Pan extends JPanel{ //main class: makes main panel and initializes most of the game
        HelpP helpP; Stats stats; Home home; StoryP storyP; Board board;
        JPanel playP, display, items, butP, homeP, end;
        JButton homeB, homeB2, homeB3, playB, storyB, helpB, again;
        JLabel val, mine;
        //HelpB helpB;
        int health, score, maxh;
        boolean color, first;
        CardLayout cL;
        Timer scoretime;
        Image back = new ImageIcon("src/window_background.png").getImage();
        //sets panels and buttons and calls runner
        public Pan(){
            //initialize panels and buttons
            homeB = new JButton ("Home"); homeB2 = new JButton("Home"); homeB3 = new JButton("Home");
            again = new JButton("Try again?");
            ImageIcon play = new ImageIcon("src/play.png");
            playB = new JButton("Play!", play);
            ImageIcon gear = new ImageIcon("src/gear.png");
            helpB = new JButton("Help", gear);
            storyB = new JButton(" Story");
            helpP = new HelpP(); stats = new Stats(); home = new Home();
            storyP = new StoryP();
            display = new JPanel(); playP = new JPanel(); items = new JPanel(); butP = new JPanel(); end = new JPanel(); homeP = new JPanel();
            val = new JLabel(new ImageIcon("src/valorant.png"));
            mine = new JLabel(new ImageIcon("src/minecraft2.png"));
            maxh = 100;
            health = maxh; score = 0;
            first = false;
            runner();
            Score scorekeep = new Score();
            scoretime = new Timer(1000, scorekeep);
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
            //Button setup
            homeB.setBackground(Color.BLACK); homeB.setForeground(Color.WHITE);
            homeB2.setBackground(Color.BLACK); homeB2.setForeground(Color.WHITE);
            homeB3.setBackground(Color.BLACK); homeB3.setForeground(Color.WHITE);
            again.setBackground(Color.BLACK); again.setForeground(Color.WHITE); color = false;
            Again alistener = new Again();
            Timer atime = new Timer(1000, alistener);
            atime.start();
            PlayAct pa = new PlayAct();
            playB.setBackground(Color.BLACK); playB.setForeground(Color.WHITE); playB.setFont(new Font("Serif", Font.PLAIN, 80));
            helpB.setBackground(Color.BLACK); helpB.setForeground(Color.WHITE); helpB.setFont(new Font("Serif", Font.PLAIN, 80));
            storyB.setBackground(Color.BLACK); storyB.setForeground(Color.WHITE); storyB.setFont(new Font("Serif", Font.PLAIN, 80));
            homeB.addActionListener(e -> cL.show(display, "link1"));
            homeB2.addActionListener(e -> cL.show(display, "link1"));
            homeB3.addActionListener(e -> cL.show(display, "link1"));
            again.addActionListener(pa);
            playB.addActionListener(pa);
            helpB.addActionListener(e -> cL.show(display, "link3"));
            storyB.addActionListener(e -> cL.show(display, "link4"));
            //navigation panel (buttons) setup
            butP.add(helpB);
            helpB.setBounds(0, 0, 100*100/30,  100);
            butP.add(playB);
            playB.setBounds(100*100/30, 0, 110*100/30, 100);
            butP.add(storyB);
            storyB.setBounds(100*100/30+110*100/30, 0, 70*100/30, 100);
            butP.add(val);
            val.setBounds(100*100/30+110*100/30+70*100/30, 0, 100, 100);
            butP.add(mine);
            mine.setBounds(100*100/30+110*100/30+70*100/30+100, 0, 100, 100);
            //home panel setup
            homeP.add(butP, BorderLayout.SOUTH);
            homeP.add(home);
            //display panel setup
            display.add(homeP, "link1");
            display.add(playP, "link2");
            display.add(helpP, "link3");
            display.add(storyP, "link4");
            display.add(end, "link5");
            //end screen setup
            JPanel esnav = new JPanel();
            esnav.setLayout(new GridLayout(1, 2, 0, 0));
            esnav.setPreferredSize(new Dimension(0, 100));
            homeB3.setFont(new Font("Serif", Font.PLAIN, 80));
            again.setFont(new Font("Serif", Font.BOLD, 80));
            esnav.add(homeB3);
            esnav.add(again);
            EndScreen es = new EndScreen();
            end.add(esnav, BorderLayout.SOUTH);
            end.add(es);
            //main panel setup
            add(display);
        }
        class PlayAct implements ActionListener{
            public void actionPerformed(ActionEvent e){
                //play panel setup
                end = new EndScreen();
                if(first) board.reset();
                else {board = new Board(); first = true;}
                stats = new Stats();
                items.setPreferredSize(new Dimension(110*1500/690, 1547));
                stats.setPreferredSize(new Dimension(123*1500/690, 1547));
                playP.add(items, BorderLayout.WEST);
                playP.add(stats, BorderLayout.EAST);
                playP.add(board, BorderLayout.CENTER);
                //reset stats
                maxh = 100; health = maxh; score = 0;
                cL.show(display, "link2");
        }}
        class Again implements ActionListener{
            public void actionPerformed(ActionEvent e){
                color = !color;
                if(color) again.setForeground(Color.GREEN);
                else again.setForeground(Color.WHITE);
            }}
        class Home extends JPanel{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
                g.setFont(new Font("Serif", Font.PLAIN, 100));
                g.setColor(Color.BLACK);
                g.drawString("Stick Jump!", 100, 1000);
                g.drawImage(back, -10*1500/690, -70*1500/690, 800*1500/690, 610*1500/690+300, null);
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
                //second portal
                g2.setColor(Color.decode("#ebab34"));
                g2.fillRect(65*1500/690, 350*1500/690+100, 5*1500/690, 70*1500/690);
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
                //first portal
                g2.setColor(Color.decode("#34d2eb"));
                g2.fillRect(600*1500/690-10, 100*1500/690+100, 5*1500/690, 70*1500/690);
                g2.setColor(Color.BLACK);
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
            public void paintComponent(Graphics g) {
                scoretime.start();
                super.paintComponent(g);
                if(score < 0) setBackground(Color.RED);
                else if(score < 50) setBackground(Color.decode("#28f928"));
                else if(score < 150) setBackground(Color.decode("#00c8ff"));
                else setBackground(Color.decode("#8d00cf"));
                //base healthbar
                g.setColor(Color.WHITE);
                g.fillRoundRect(5, 5, 200, 20, 10, 10);
                //health
                g.setColor(Color.RED);
                g.fillRoundRect(5, 5, health*200/maxh, 20, 10, 10);
                g.setColor(Color.BLACK);
                g.drawRoundRect(5, 5, 200, 20, 10, 10);
                g.drawRoundRect(5, 5, health*200/maxh, 20, 10, 10);
                //health labeling
                g.setFont(new Font("Serif", Font.ITALIC, 50));
                g.drawString(health+" / "+maxh, 5, 80);
                //score
                g.setFont(new Font("Serif", Font.BOLD, 60));
                g.drawString(score+"", 5, 150);
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
                else if(score < 50) g.setColor(Color.decode("#28f928"));
                else if(score < 150) g.setColor(Color.decode("#00c8ff"));
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
                homeB2.setPreferredSize(new Dimension(0, 100));
                homeB2.setFont(new Font("Serif", Font.PLAIN, 80));
                add(homeB2, BorderLayout.SOUTH);
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
                homeB.setPreferredSize(new Dimension(0, 100));
                homeB.setFont(new Font("Serif", Font.PLAIN, 80));
                add(homeB, BorderLayout.SOUTH);
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
            int px, py, pxforce, pyforce, tlength;
            double speedmultiplier;
            boolean wpress, apress, dpressed, wallowpress, onGround, loseHealth, godmode;
            PlatMover platmove; PlayerMover pmove; TurretMover turmove; ProjectileMover projmove; ProjSpawner pspawn;
            Timer ptime, platime, turtime, projtime, pstime;
            Image city, city2, city3, blood;
            Platform[] plats = new Platform[20]; PowerUp[] pows = new PowerUp[20]; Turret[] turr = new Turret[39];
            ArrayList<Projectile> projectile = new ArrayList();
            Graphics2D g2;
            public Board(){
                reset();
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
                godmode = false;
                tlength = 40;
                city = new ImageIcon("src/cityback2.png").getImage();
                city2 = new ImageIcon("src/cityback.png").getImage();
                city3 = new ImageIcon("src/cityback3.png").getImage();
                blood = new ImageIcon("src/blood.png").getImage();
            }
            public void reset(){
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
                if(score < 50) g.drawImage(city, 0, 0, 995, 1547, null);
                else if(score < 150) g.drawImage(city2, 0, 0, 995, 1547, null);
                else g.drawImage(city3, 0, 0, 995, 1547, null);
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
                public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < projectile.size(); i++){
                        projectile.get(i).x += projectile.get(i).changex*speedmultiplier;
                        projectile.get(i).y += projectile.get(i).changey*speedmultiplier;
                    }}}
            class TurretMover implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    int type;
                    for(int i = 0; i < 39; i++){
                        if(turr[i].inplay){
                            turr[i].y += 2*speedmultiplier;
                            if(turr[i].y > 1527) turr[i] = new Turret();
                            repaint();
                            grabFocus();
                        }
                        else turr[i].inplay = (int)(Math.random()*10000+1)==1;
                    }}}
            class ProjectileMover implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < projectile.size(); i++){
                        projectile.get(i).x += projectile.get(i).changex*speedmultiplier;
                        projectile.get(i).y += projectile.get(i).changey*speedmultiplier;
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
                    /* 0 = extra points; 1 = health regen (+10); 2 = extra health; 3 = health regen (max) */
                    type = (int)(Math.random()*10);
                    x = -50; y = -50;
                    inplay = false;
                }
            }
            class Turret{
                int x, y, type, shoot, x3, y3;
                boolean inplay;
                public Turret(){
                    shoot = 0;
                    if((int)(Math.random()*2)==0) x = -20;
                    else x = 975;
                    y = -50;
                    type = 0; //(int)(Math.random()*5);
                    inplay = false;
                }
            }
            class Projectile{
                int type, x, y, changex, changey;
                public Projectile(int tp, int tx, int ty, int x3, int y3){
                    x = tx+x3; y = ty+y3;
                    changex = x3; changey = y3;
                    type = tp;
                }}
            public void coord_change(int new_x, int new_y){px = new_x;py = 1547-new_y;}
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '/'){
                    String command = scan.next();
                    if(command.equals("godmode_on")) godmode = true;
                    if(command.equals("godmode_off")) godmode = false;
                    if(command.equals("set_score")) score = scan.nextInt();
                    if(command.equals("stop_score")) scoretime.stop();
                    if(command.equals("start_score")) scoretime.start();
                    if(command.equals("kill")) health = 0;
                }}
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