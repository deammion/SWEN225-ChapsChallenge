package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.maze.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener, Runnable {

    //Current level Chip is on.
    public int GameLevel = 1;

    //JComponents.
    JFrame GUIFrame;
    JPanel gamePanel, infoPanel, containerPanel;

    //JMenu Components.
    JMenuBar menuBar;
    JMenu game, options, level, help;

    //Text Components.
    JLabel levelText, timeText, chipsText;

    //Game Menu Items.
    JMenuItem pause, resume, exit;
    //Options Menu Items.
    JMenuItem save, load;
    //Level Menu Items.
    JMenuItem one, two, three;

    GUI(){

        //Game panel component of the GUI, will hold the board to be rendered.
        gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createTitledBorder("Game"));
        gamePanel.setPreferredSize(new Dimension(468, 468));

        //Information panel component of the GUI
        infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
        infoPanel.setPreferredSize(new Dimension(200, 468));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        levelText = new JLabel("Level: ");
        timeText = new JLabel("Time: ");
        chipsText = new JLabel("Chips Left: ");

        infoPanel.add(levelText);
        infoPanel.add(timeText);
        infoPanel.add(chipsText);

        //Container panel which is the Master container.
        containerPanel = new JPanel();
        containerPanel.add(gamePanel);
        containerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        containerPanel.add(infoPanel);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(25, 50,50, 50));
        containerPanel.setLayout(new FlowLayout());

        this.setContentPane(containerPanel);
        this.setResizable(false);
        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);

        //The main GUI frame.
        this.setTitle("Chip's Challenge: " + GameLevel);

        //Menu setup.
        createMenu();
        this.add(menuBar);
        this.setJMenuBar(menuBar);

        //Key listener set up.
        setFocusable(true);
        this.addKeyListener(new Controls());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Method to create the Menu for the GUI, has all the submenu items
     * implemented too.
     */
    public void createMenu(){

        //MenuBar.
        menuBar = new JMenuBar();

        //Game Menu.
        game = new JMenu("Game");
        pause = new JMenuItem("Pause");
        pause.addActionListener(this);
        resume = new JMenuItem("Resume");
        resume.addActionListener(this);
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        game.add(pause); game.add(resume); game.add(exit);

        //Option Menu.
        options = new JMenu("Options");
        save = new JMenuItem("Save");
        save.addActionListener(this);
        load = new JMenuItem("Load");
        load.addActionListener(this);
        options.add(save); options.add(load);

        //Level Menu.
        level = new JMenu("Level");
        one = new JMenuItem("One");
        one.addActionListener(this);
        two = new JMenuItem("Two");
        two.addActionListener(this);
        three = new JMenuItem("Three");
        three.addActionListener(this);
        level.add(one); level.add(two); level.add(three);

        //Help Menu.
        help = new JMenu("Help");
        help.addMenuListener(new HelpMenuListener());

        //Add Menu Components.
        menuBar.add(game); menuBar.add(options); menuBar.add(level); menuBar.add(help);

    }

    /**
     * The user of the game clicks on a menu item, this will invoke a method
     * which will Pause, Resume etc.
     *
     * @param actionEvent - event happening.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch(actionEvent.getActionCommand()){

            //Game Menu Items.
            case "Pause":
                //Invoke pause method.
                System.out.println("You are in the Pause\n");
                break;
            case "Resume":
                //Invoke resume method.
                System.out.println("You are in the Resume\n");
                break;
            case "Exit":
                //Invoke exit method.
                break;

            //Options Menu Items.
            case "Save":
                //Invoke save method.
                System.out.println("You are in the Save\n");
                break;
            case "Load":
                //Invoke load method.
                break;

            //Level Menu Items.
            case "One":
                //Invoke one method.
                System.out.println("You are in the One\n");
                break;
            case "Two":
                //Invoke two method.
                break;
            case "Three":
                //Invoke three method.
                break;

            //Help Menu Items.
            case "Help":
                //Invoke help method (dialog box).
                System.out.println("You are in the Help");

        }
    }

    @Override
    public void run() {

    }
}
