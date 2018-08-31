/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousesimulator;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author John and Cass
 */
public class MouseSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new MouseSimulator();
    }
    
    private int LeftRight = 1;
    private int Left = 0, Right = 0, Degree = 0, Speed = 0;
    MouseSimulator(){
        JFrame jFrame = new JFrame("Mouse Simulator For Cass <3");
        jFrame.setSize(new Dimension(1000,1000));
        int mult;
        //List panels = new LinkedList();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel tester = new JPanel(new GridLayout(1,4));
        
        JPanel yellow = new JPanel(new GridLayout(1,1));
        yellow.setPreferredSize(new Dimension(1000,250));
        yellow.setBackground(Color.WHITE);
        
        jFrame.add(tester, BorderLayout.NORTH);
        jFrame.add(yellow, BorderLayout.SOUTH);
        int color = 0;
        
        JPanel MouseCapPos = new JPanel(new GridLayout(1,1));
        MouseCapPos.setPreferredSize(new Dimension(250,250));
        MouseCapPos.setBackground(Color.CYAN);
        JLabel labelM = new JLabel("", SwingConstants.CENTER);
        labelM.setText("X: " + Integer.toString(Left) + " ,Y: " + Integer.toString(Right));
        labelM.setFont(new Font("Serif", Font.PLAIN, 32));
        MouseCapPos.add(labelM);
        tester.add(MouseCapPos);
        
        JPanel DirectionAndSpeed = new JPanel(new GridLayout(3,1));
        DirectionAndSpeed.setPreferredSize(new Dimension(250,250));
        DirectionAndSpeed.setBackground(Color.MAGENTA);
        tester.add(DirectionAndSpeed);
        
        JPanel DirectionD = new JPanel(new GridLayout(1,1));
        DirectionD.setPreferredSize(new Dimension(250,150));
        DirectionD.setBackground(Color.WHITE);
        JLabel imageD = new JLabel("", SwingConstants.CENTER);
        DirectionD.add(imageD, BorderLayout.CENTER);
        JPanel DegreeD = new JPanel(new GridLayout(1,1));
        DegreeD.setPreferredSize(new Dimension(250,50));
        DegreeD.setBackground(Color.GREEN);
        JLabel labelD = new JLabel("", SwingConstants.CENTER);
        DegreeD.add(labelD, BorderLayout.CENTER);
        JPanel SpeedS = new JPanel(new GridLayout(1,1));
        SpeedS.setPreferredSize(new Dimension(250,50));
        SpeedS.setBackground(Color.LIGHT_GRAY);
        JLabel labelS = new JLabel("", SwingConstants.CENTER);
        SpeedS.add(labelS, BorderLayout.CENTER);
        
        ImageIcon arrowImage = new ImageIcon("Images/Arrow.png");
        ImageIcon imageIcon = new ImageIcon(arrowImage.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        imageD.setIcon(imageIcon);
        labelD.setText("Direction: " + Integer.toString(Degree) + " degrees");
        labelD.setFont(new Font("Serif", Font.PLAIN, 24));
        labelS.setText("Speed: " + Integer.toString(Speed) + " px per second");
        labelS.setFont(new Font("Serif", Font.PLAIN, 24));
        
        DirectionAndSpeed.add(DirectionD);
        DirectionAndSpeed.add(DegreeD);
        DirectionAndSpeed.add(SpeedS);
        
        JPanel ActiveOrNot = new JPanel(new GridLayout(1,1));
        ActiveOrNot.setPreferredSize(new Dimension(250,250));
        ActiveOrNot.setBackground(Color.YELLOW);
        JLabel label = new JLabel("", SwingConstants.CENTER);
        ActiveOrNot.add(label);
        tester.add(ActiveOrNot);
        
        JPanel WorkingOrNot = new JPanel(new GridLayout(1,1));
        WorkingOrNot.setPreferredSize(new Dimension(250,250));
        WorkingOrNot.setBackground(Color.ORANGE);
        JLabel OnOrOff = new JLabel("OFF", SwingConstants.CENTER);
        OnOrOff.setFont(new Font("Serif", Font.PLAIN, 128));
        WorkingOrNot.add(OnOrOff);
        tester.add(WorkingOrNot);
        
        JButton MouseCapButton = new JButton("Capture Mouse Position");
        yellow.add(MouseCapButton);
        MouseCapButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                GrabMouseClick(labelM);
            }
        });
        
        JButton DirectionButton = new JButton("Direction and Speed");
        yellow.add(DirectionButton);
        DirectionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                SetDirectionAndSpeed(imageD,labelD,labelS);
            }
        });
        
        JButton ReadyButton = new JButton ("Left Or Right Click");
        yellow.add(ReadyButton);
        ReadyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                LeftRight = LeftRight * -1;
                //System.out.println(LeftRight);
                UpdateClickImage(LeftRight, label);
            }
        });
        
        JButton GoButton = new JButton ("GO!");
        yellow.add(GoButton);
        GoButton.addMouseListener(new MouseAdapter() {
            TimerTask task;
            @Override
            public void mousePressed(MouseEvent e) {
                OnOrOff.setText("ON");
                //ThingLauncher();
                task = new LaunchTehThing();
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(task, 0, 500); // Time is in milliseconds
                // The second parameter is delay before the first run
                // The third is how often to run it
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
                OnOrOff.setText("OFF");
            }
        });
        
        UpdateClickImage(LeftRight, label);
        
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    
    public void GrabMouseClick(JLabel temp){
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        Left = (int) mouseX;
        Right = (int) mouseY;
        System.out.println("X:" + mouseX);
        System.out.println("Y:" + mouseY);
        temp.setText("X: " + Integer.toString(Left) + " ,Y: " + Integer.toString(Right));
        temp.setFont(new Font("Serif", Font.PLAIN, 32));
    }
    
    public static void UpdateClickImage(int mouseSide, JLabel temp){
        String currentClick;
        if (mouseSide == 1){
            currentClick = "L";
        }
        else{
            currentClick = "R";
        }
        temp.setText(currentClick);
        temp.setFont(new Font("Serif", Font.PLAIN, 128));
    }
    
    public void SetDirectionAndSpeed(JLabel temp1, JLabel temp2, JLabel temp3){
        JFrame jaFrame = new JFrame("Direction and Speed");
        jaFrame.setSize(new Dimension(500,500));
        
        JPanel direction = new JPanel(new GridLayout(1,2));
        direction.setPreferredSize(new Dimension(500,250));
        JPanel speed = new JPanel(new GridLayout(1,2));
        speed.setPreferredSize(new Dimension(500,250));
        ImageIcon arrowImage = new ImageIcon("Images/Arrow.png");
        
        jaFrame.add(direction, BorderLayout.NORTH);
        jaFrame.add(speed, BorderLayout.SOUTH);
        JPanel directImage = new JPanel(new GridLayout(1,1));
        directImage.setPreferredSize(new Dimension(250,250));
        directImage.setBackground(Color.RED);
        JLabel imageA = new JLabel("", SwingConstants.CENTER);
        directImage.add(imageA);
        imageA.setIcon(new RotatedIcon(arrowImage, Degree));
        JPanel directNum = new JPanel(new GridLayout(2,1));
        directNum.setPreferredSize(new Dimension(250,250));
        directNum.setBackground(Color.BLUE);
        JLabel degreeL = new JLabel("Degree: " + Degree, SwingConstants.CENTER);
        degreeL.setFont(new Font("Serif", Font.PLAIN, 32));
        directNum.add(degreeL);
        JPanel DegButtons = new JPanel(new GridLayout(1,1));
        directNum.add(DegButtons);
        JButton MinusButton = new JButton ("-");
        MinusButton.setFont(new Font("Serif", Font.PLAIN, 128));
        DegButtons.add(MinusButton);
        MinusButton.addMouseListener(new MouseAdapter() {
            TimerTask task;
            @Override
            public void mousePressed(MouseEvent e) {
                task = new MoveDegrees(-1, degreeL, imageA);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(task, 0, 50); // Time is in milliseconds
                // The second parameter is delay before the first run
                // The third is how often to run it
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                task.cancel();
                // Will not stop execution of task.run() if it is midway
                // But will guarantee that after this call it runs no more than one more time
            }
        });
        JButton PlusButton = new JButton ("+");
        PlusButton.setFont(new Font("Serif", Font.PLAIN, 128));
        DegButtons.add(PlusButton);
        PlusButton.addMouseListener(new MouseAdapter() {
            TimerTask task;
            @Override
            public void mousePressed(MouseEvent e) {
                task = new MoveDegrees(1, degreeL, imageA);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(task, 0, 50); // Time is in milliseconds
                // The second parameter is delay before the first run
                // The third is how often to run it
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                task.cancel();
                // Will not stop execution of task.run() if it is midway
                // But will guarantee that after this call it runs no more than one more time
            }
        });
        
        
        direction.add(directImage, BorderLayout.EAST);
        direction.add(directNum, BorderLayout.WEST);
        
        JPanel speedPanel = new JPanel(new GridLayout(2,1));
        speedPanel.setPreferredSize(new Dimension(250,250));
        speedPanel.setBackground(Color.WHITE);
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(250,250));
        okButton.setFont(new Font("Serif", Font.PLAIN, 128));
        speed.add(speedPanel, BorderLayout.WEST);
        speed.add(okButton, BorderLayout.EAST);
        
        JLabel speedL = new JLabel("Speed", SwingConstants.CENTER);
        speedL.setFont(new Font("Serif", Font.PLAIN, 32));
        speedPanel.add(speedL, BorderLayout.NORTH);
        JTextField speedT = new JTextField(5);
        speedT.setFont(speedT.getFont().deriveFont(50f));
        speedT.setText(Integer.toString(Speed));
        speedPanel.add(speedT, BorderLayout.SOUTH);
        
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int d = Degree, s = Speed;
                String input = speedT.getText();
                if(input.equals(""))
                    s = 0;
                else
                    s = Integer.parseInt(input);
                SetDirectionFrameData(d,s,temp1, temp2, temp3);
                jaFrame.dispose();
            }
        });
        
        jaFrame.pack();
        jaFrame.setLocationRelativeTo(null);
        jaFrame.setVisible(true);
    }
    
    public void SetDirectionFrameData(int d, int s, JLabel temp1, JLabel temp2, JLabel temp3){
        Degree = d;
        Speed = s;
        ImageIcon arrowImage = new ImageIcon("Images/Arrow.png");
        ImageIcon imageIcon = new ImageIcon(arrowImage.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        temp1.setIcon(new RotatedIcon(imageIcon, Degree));
        temp2.setText("Direction: " + Integer.toString(Degree) + " degrees");
        temp2.setFont(new Font("Serif", Font.PLAIN, 24));
        temp3.setText("Speed: " + Integer.toString(Speed) + " px per second");
        temp3.setFont(new Font("Serif", Font.PLAIN, 24));
    }
    
    private class MoveDegrees extends TimerTask {
        int PlusMinus;
        JLabel temp, tempI;
        ImageIcon arrowImage = new ImageIcon("Images/Arrow.png");
        
        MoveDegrees(int sign, JLabel temper, JLabel Imager){
            PlusMinus = sign;
            temp = temper;
            tempI = Imager;
        }
        @Override
        public void run() {
            Degree = Degree + PlusMinus;
            if(Degree == -1)
                Degree = 359;
            else if(Degree == 360)
                Degree = 0;
            temp.setText("Degree: " + Degree);
            tempI.setIcon(new RotatedIcon(arrowImage, Degree));
        }
    }
    
    private class LaunchTehThing extends TimerTask {
        JFrame lFrame = new JFrame("THING LAUNCHER");
        JPanel backDrop = new JPanel(new GridLayout(1,1));
        ImageIcon arrowImage = new ImageIcon("Images/Arrow.png");
        JLabel tehLabel = new JLabel("", SwingConstants.CENTER);
        LaunchTehThing(){
            lFrame.setPreferredSize(new Dimension(1000, 1000));
            backDrop.setPreferredSize(new Dimension(1000, 1000));
            backDrop.setBackground(Color.WHITE);
            lFrame.add(backDrop);
            backDrop.add(tehLabel);
            tehLabel.setIcon(arrowImage);
            lFrame.pack();
            lFrame.setLocationRelativeTo(null);
            lFrame.setVisible(true);
        }
        @Override
        public void run() {
        
        
        }
    }
    
    private void ThingLauncher(){
        JFrame lFrame = new JFrame("THING LAUNCHER");
        lFrame.setPreferredSize(new Dimension(1000, 1000));
        
        JPanel backDrop = new JPanel(new GridLayout(1,1));
        backDrop.setPreferredSize(new Dimension(1000, 1000));
        backDrop.setBackground(Color.WHITE);
        lFrame.add(backDrop);
        
        ImageIcon arrowImage = new ImageIcon("Images/Arrow.png");
        JLabel tehLabel = new JLabel("", SwingConstants.CENTER);
        backDrop.add(tehLabel);
        tehLabel.setIcon(arrowImage);
        
        lFrame.pack();
        lFrame.setLocationRelativeTo(null);
        lFrame.setVisible(true);
    }
}
