package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.maze.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener, Runnable {

    //Current level Chip is on.
    public int gameLevel = 1;

    //Boolean for game states.
    boolean recordAndReplayRunning = false;
    boolean gamePaused = false;

    //Timing Component for the Game.
    long totalTime = 100;
    long timeLeft = totalTime;

    //JComponents.
    JFrame GUIFrame;
    JPanel gamePanel, infoPanel, containerPanel;

    //JMenu Components.
    JMenuBar menuBar;
    JMenu game, options, level, replay, help;

    //Text Components.
    JLabel levelText, timeText, chipsText;

    //Game Menu Items.
    JMenuItem pause, resume, exit;
    //Options Menu Items.
    JMenuItem save, load;
    //Level Menu Items.
    JMenuItem one, two, three;
    //Replay Menu Items.
    JRadioButtonMenuItem speedSet0, speedSet1, speedSet2, speedSet3;
    ButtonGroup speedButtonGroup;
    JMenuItem step, auto;
    JMenu speed;

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
        this.setTitle("Chip's Challenge: " + gameLevel);

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
        resume = new JMenuItem("Resume");
        resume.addActionListener(this);
        pause = new JMenuItem("Pause");
        pause.addActionListener(this);
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        game.add(resume); game.add(pause); game.add(exit);

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

        //Record n Replay Menu.
        replay = new JMenu("Replay");
        step = new JMenuItem("Step-by-Step");
        step.addActionListener(this);
        auto = new JMenuItem("Auto-Replay");
        auto.addActionListener(this);

        //The replay speed setting submenu setup.
        speed = new JMenu("Replay Speed");
        speedButtonGroup = new ButtonGroup();
        speedSet0 = new JRadioButtonMenuItem("0.5x speed");
        speedSet1 = new JRadioButtonMenuItem("1.0x speed", true);
        speedSet2 = new JRadioButtonMenuItem("1.5x speed");
        speedSet3 = new JRadioButtonMenuItem("2.0x speed");
        speed.add(speedSet0); speed.add(speedSet1); speed.add(speedSet2); speed.add(speedSet3);
        speedButtonGroup.add(speedSet0); speedButtonGroup.add(speedSet1);
        speedButtonGroup.add(speedSet2); speedButtonGroup.add(speedSet3);


        //Add all the sun items to the menu.
        replay.add(step); replay.add(auto); replay.add(speed);

        //Help Menu.
        help = new JMenu("Help");
        help.addMenuListener(new HelpMenuListener());

        //Add Menu Components.
        menuBar.add(game); menuBar.add(options); menuBar.add(level); menuBar.add(replay); menuBar.add(help);

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


            //Replay Menu Items.
            case "Step-by-Step":
                //Invoke step-by-step method.
                System.out.println("You are in step-by-step\n");
                break;
            case "Auto-Replay":
                //Invoke auto-replay method.
                System.out.println("You are in auto-replay\n");
                break;

            //Help Menu Items.
            case "Help":
                //Invoke help method (dialog box).
                System.out.println("You are in the Help");

        }
    }

    /**
     * Allows the game to run smoothly, takes care of the rendering updates and internal components. The
     * time count down
     */

    @Override
    public void run() {

        //Game loop details:
        //Implement the run method of the thread
        //Create an infinite while loop
        //Use a boolean variable running to control the loop.

        while(true) {

            if(!isGamePaused() && !isRecordAndReplayRunning()){

                if(timeLeft > 0){

                    //update the board

                }

            }

        }

        }

    //Helper Methods all implemented below. The get/set/is methods:

        // get the time left.
        // get the player inventory.
        // get the player treasure count.
        // get the number of treasures in a level.



    /**
     * Is the game is Record and Repay mode.
     *
      * @return - if the game is in record or replay mode.
     */
    private boolean isRecordAndReplayRunning() {
        return recordAndReplayRunning;
    }

    /**
     * Is the game paused.
     *
     * @return - if the game is paused or not.
     */
    private boolean isGamePaused() {
        return gamePaused;
    }

    /**
     * The amount of time left in a level to be set, complimentary
     * for the pause option.
     *
     * @param timeLeft - time left to complete a level
     */
    public void setTimeLeft(Long timeLeft){
        this.timeLeft = timeLeft;
    }

    /**
     * How much time is left to complete the current level.
     *
     * @return - time left to complete the level.
     */
    public long getTimeLeft() {
        return timeLeft;
    }

    /**
     * The level of the game the player is on.
     *
     * @return - the level number.
     */
    public int getGameLevel(){
        return gameLevel;
    }

}
