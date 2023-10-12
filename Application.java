import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimerTask;
import javax.swing.*;

public class Application {

    JFrame mainWindow;
    JLabel exit,top;
    JPanel mainPanel;
    JMenuBar menuBar;
    JToggleButton Clock, Timer, Stopwatch;
    int mouseX,mouseY;

    public static void main(String[] args) {
        new Application();
    }

    Application() {

        ImageIcon iconmain = new ImageIcon("iconmain.png");
        mainWindow = new JFrame("Time Application");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(500, 300);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setIconImage(iconmain.getImage());
        mainWindow.setResizable(false);

        mainWindow.setUndecorated(true);
        mainWindow.setOpacity(0.95f);
        mainWindow.setShape(new RoundRectangle2D.Double(0,0,500,300,100,100));

        exit=new JLabel("x");
        exit.setBounds(455, 15, 50, 50);
        exit.setFont(new Font(null, Font.BOLD, 17));
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });

        top=new JLabel("Clock.java");
        top.setBounds(225, 17, 75, 30);
        top.setFont(new Font("Null", Font.BOLD, 11));

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(222, 244, 254));
        mainPanel.add(top);
        mainPanel.add(exit);
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e){
                mainWindow.setLocation(mainWindow.getX()+e.getX()-mouseX, mainWindow.getY()+e.getY()-mouseY);
            }
        });
        mainPanel.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                mouseX=e.getX();
                mouseY=e.getY();
            }
        });

        mainWindow.add(mainPanel);
        nav();
        mainWindow.setVisible(true);

    }

    void nav() {

        Timer = new JToggleButton("Set timer");
        Clock = new JToggleButton("Clock");
        Stopwatch = new JToggleButton("Stopwatch");

        Timer.setBorderPainted(false);
        Timer.setFocusable(false);
        Clock.setBorderPainted(false);
        Clock.setFocusable(false);
        Stopwatch.setBorderPainted(false);
        Stopwatch.setFocusable(false);

        Timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.dispose();
                new timer();
            }
        });

        Clock.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.dispose();
                new clock();
            }

        });

        Stopwatch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.dispose();
                new Stopwatch();
            }

        });

        Clock.setBounds(100, 55, 100, 35);
        Timer.setBounds(203, 55, 100, 35);
        Stopwatch.setBounds(306, 55, 100, 35);

        mainPanel.add(Clock);
        mainPanel.add(Timer);
        mainPanel.add(Stopwatch);
    }

}

class timer extends Application {

    JLabel timer;
    JToggleButton sumbitButton;
    JButton reset;

    timer() {
        Timer.setSelected(true);
        timer = new JLabel("00:00 Seconds");
        timer.setBounds(100, 145, 500, 100);
        timer.setFont(new Font(null, Font.BOLD, 45));

        JLabel message = new JLabel("Set timer :");
        sumbitButton = new JToggleButton("Start");

        sumbitButton.setBounds(360, 120, 75, 20);
        sumbitButton.setFocusable(false);
        sumbitButton.setBorderPainted(false);

        reset=new JButton("reset");
        reset.setBounds(200, 240, 90, 18);
        reset.setFont(new Font(null, 0, 15));
        reset.setBorderPainted(false);
        reset.setFocusable(false);

        message.setBounds(90, 102, 100, 60);
        message.setFont(new Font(null, Font.PLAIN, 20));

        mainPanel.add(sumbitButton);
        mainPanel.add(reset);
        mainPanel.add(message);
        mainPanel.add(timer);
        logic();
    }

    void logic() {

        JTextField inputime = new JTextField();
        inputime.setBounds(185, 125, 170, 18);

        sumbitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int ip = Integer.parseInt(inputime.getText());
                timerlogic(ip);
            }

        });

        reset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                sumbitButton.setEnabled(true);
                t2.cancel();
                task2.cancel();
                timer.setText("00:00 Seconds");
            }

        });
        mainPanel.add(inputime);


    }
    java.util.Timer t2;
    TimerTask task2;
    void timerlogic(int ip){
        sumbitButton.setEnabled(false);
        t2=new java.util.Timer();
        task2=new TimerTask() {
            int sec=ip%60;
            int min=ip/60;
            

            @Override
            public void run() {
                sec=sec%60;

                if(sec>=0 && min>=0){
                    timer.setText(new DecimalFormat("00").format(min)+":"+new DecimalFormat("00").format(sec)+" Seconds");
                    if(sec==0){
                        min--;
                        sec=60;
                    }
                    sec--;
                }
            }
            
        };
        t2.scheduleAtFixedRate(task2, 0, 1000);

    }
}

class clock extends Application {
    JLabel clock;
    String m = "AM";

    clock() {
        Clock.setSelected(true);
        clock = new JLabel("Current Time- 00:00:00 " + m);
        clock.setBounds(37, 115, 500, 100);
        clock.setFont(new Font(null, Font.BOLD, 35));
        mainPanel.add(clock);

        Timer t = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic();
            }
        });
        t.start();
        logic();
    }

    void logic() {
        Calendar c = Calendar.getInstance();
        String sec = new DecimalFormat("00").format(c.get(Calendar.SECOND));
        String min = new DecimalFormat("00").format(c.get(Calendar.MINUTE));
        String hr = new DecimalFormat("00").format(c.get(Calendar.HOUR_OF_DAY));
        if (c.get(Calendar.AM_PM) == 1) {
            m = "PM";
        }
        clock.setText("Current Time- " + hr + ":" + min + ":" + sec + " " + m);

    }
}

class Stopwatch extends Application {
    JLabel stopwatch;
    JToggleButton start;
    JButton reset,stop;
    java.util.Timer t;

    Stopwatch() {
        Stopwatch.setSelected(true);
        stopwatch = new JLabel("00:00:00");
        stopwatch.setBounds(105, 115, 500, 100);
        stopwatch.setFont(new Font(null, Font.BOLD, 75));

        start = new JToggleButton("Start");
        reset=new JButton("reset");
        stop = new JButton("Stop");

        start.setBounds(110, 240, 90, 18);
        start.setBorderPainted(false);
        start.setFont(new Font(null, 0, 15));
        start.setFocusable(false);
        
        reset.setBounds(210, 240, 90, 18);
        reset.setFont(new Font(null, 0, 15));
        reset.setBorderPainted(false);
        reset.setFocusable(false);
        
        stop.setBounds(310, 240, 90, 18);
        stop.setFont(new Font(null, 0, 15));
        stop.setBorderPainted(false);
        stop.setFocusable(false);

        mainPanel.add(start);
        mainPanel.add(reset);
        mainPanel.add(stop);
        mainPanel.add(stopwatch);

        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic();
                start.setEnabled(false);
            }
        });

        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                start.setEnabled(true);
                flag = false;
                t.cancel();
                task.cancel();
            }

        });

        reset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                start.setEnabled(true);
                t.cancel();
                task.cancel();
                flag=false;
                stopwatch.setText("00:00:00");
            }

        });
    }

    TimerTask task;
    boolean flag = true;

    void logic() {
        flag = true;
        t = new java.util.Timer();
        task = new TimerTask() {
            int count = 0;
            int mil = 0, sec = 0, min = 0;

            @Override
            public void run() {
                while (flag) {
                    mil = count % 100;
                    if (mil == 99) {
                        sec++;
                    }
                    sec = sec % 60;
                    if (sec == 59) {
                        min++;
                    }
                    stopwatch.setText(new DecimalFormat("00").format(min) + ":" + new DecimalFormat("00").format(sec)
                            + ":" + new DecimalFormat("00").format(mil));
                    count++;
                    try {
                        Thread.sleep(9);
                    } catch (InterruptedException e) {
                    }
                }
            }

        };
        t.scheduleAtFixedRate(task, 0, 1000);
    }
}