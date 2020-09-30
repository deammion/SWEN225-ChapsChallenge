package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUI extends JFrame implements ActionListener {

    //Current level Chip is on.
    int gameLevel = 1;
    int chipsLeft = 5;

    //Boolean for game states.
    boolean recordAndReplayRunning = false;
    boolean gamePaused = false;

    //Timing Component for the Game.
    long totalTime = 100;
    long timeLeft = totalTime;

    //JComponents.
    JPanel gamePanel, infoPanel, containerPanel;
    JLabel background;

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

    public GUI(){

        //Game panel component of the GUI, will hold the board to be rendered.
        gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createRaisedBevelBorder());
        gamePanel.setPreferredSize(new Dimension(468, 468));

        //Information panel component of the GUI
        infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        infoPanel.setPreferredSize(new Dimension(200, 468));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        levelText = new JLabel("Level: " + gameLevel);
        timeText = new JLabel("Time: " + timeLeft + " seconds");
        chipsText = new JLabel("Chips Left: " + chipsLeft);

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
        this.addKeyListener(new Controls(this));
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
        speedSet0.addActionListener(this);
        speedSet1 = new JRadioButtonMenuItem("1.0x speed", true);
        speedSet1.addActionListener(this);
        speedSet2 = new JRadioButtonMenuItem("1.5x speed");
        speedSet2.addActionListener(this);
        speedSet3 = new JRadioButtonMenuItem("2.0x speed");
        speedSet3.addActionListener(this);
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

    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    abstract void move(Move move);

    abstract void pause();

    abstract void resume();

    /**
     * The user of the game clicks on a menu item, this will invoke a method
     * which will Pause, Resume etc.
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
            case "0.5x speed":
                //Set speed to 0.5.
                System.out.println("You are in 0.5x speed");
            case "1.0x speed":
                //Set speed to 1.0.
                System.out.println("You are in 1.0x speed");
            case "1.5x speed":
                //Set speed to 1.5.
                System.out.println("You are in 1.5x speed");
            case "2.0x speed":
                //Set speed to 2.0.
                System.out.println("You are in 2.0x speed");
            //Help Menu Items.
            case "Help":
                //Invoke help method (dialog box).
                System.out.println("You are in the Help");

        }
    }

    /**
     * Is the game is Record and Repay mode.
     * @return - if the game is in record or replay mode.
     */
    private boolean isRecordAndReplayRunning() {
        return recordAndReplayRunning;
    }

    /**
     * Is the game paused.
     * @return - if the game is paused or not.
     */
    private boolean isGamePaused() {
        return gamePaused;
    }

    /**
     * The amount of time left in a level to be set, complimentary
     * for the pause option.
     * @param timeLeft - time left to complete a level
     */
    public void setTimeLeft(Long timeLeft){
        this.timeLeft = timeLeft;
    }

    /**
     * How much time is left to complete the current level.
     * @return - time left to complete the level.
     */
    public long getTimeLeft() {
        return timeLeft;
    }

    /**
     * The level of the game the player is on.
     * @return - the level number.
     */
    public int getGameLevel(){
        return gameLevel;
    }

}
