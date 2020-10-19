package nz.ac.vuw.ecs.swen225.gp30.application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {

    //Current level Chip is on.
    int gameLevel = 1;
    int chipsLeft = 5;
    int microLeft = 0;

    //Boolean for game states.
    boolean recordAndReplayRunning = false;
    boolean gamePaused = false;

    //JComponents.
    JPanel gamePanel, dashPanel, infoPanel, containerPanel;
    InventoryPanel invPanel;

    //JMenu Components.
    JMenuBar menuBar;
    JMenu game, options, level, replay, help;

    //Text Components.
    JLabel levelText, timeText, chipsText, microText;

    //Game Menu Items.
    JMenuItem pause, resume, exit;
    //Options Menu Items.
    JMenuItem save, load;
    //Level Menu Items.
    JMenuItem one, two;
    //Replay Menu Items.
    JRadioButtonMenuItem speedSet0, speedSet1, speedSet2, speedSet3;
    ButtonGroup speedButtonGroup;
    JMenuItem recLoad;
    JMenu speed;

    /**
     * Adds the panels to the master panel.
     */
    public void init() {

        //Information panel component of the GUI
        dashPanel = new JPanel();
        dashPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dashPanel.setPreferredSize(new Dimension(200, 378));
        dashPanel.setLayout(new BoxLayout(dashPanel, BoxLayout.Y_AXIS));
        //Information Panel text Components.
        levelText = new JLabel("Level: " + gameLevel + " ");
        levelText.setBorder(BorderFactory.createEmptyBorder(10,30,10,10));
        timeText = new JLabel();
        timeText.setBorder(BorderFactory.createEmptyBorder(10,27,10,10));
        chipsText = new JLabel("Chips Left: " + chipsLeft + " ");
        chipsText.setBorder(BorderFactory.createEmptyBorder(10,14,10,10));
        microText = new JLabel("Microchips Left: " + microLeft + " ");
        microText.setBorder(BorderFactory.createEmptyBorder(10,15,10,10));
        // container for text
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(levelText);
        infoPanel.add(timeText);
        infoPanel.add(chipsText);

        //inv panel
        invPanel = new InventoryPanel();

        //Add text components to the info panel.
        dashPanel.add(infoPanel);
        dashPanel.add(Box.createRigidArea(new Dimension(60, 100)));
        dashPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        dashPanel.add(invPanel);

        //Container panel which is the Master container.
        containerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File("assets/circuit_board_background.jpg")), 0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        containerPanel.add(gamePanel);
        containerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        containerPanel.add(dashPanel);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(25, 50,50, 50));
        containerPanel.setLayout(new FlowLayout());

        //Set the attributes for the master JFrame.
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
        this.setFocusable(true);
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
        pause = new JMenuItem("Pause");
        exit = new JMenuItem("Exit");
        game.add(resume); game.add(pause); game.add(exit);

        //Option Menu.
        options = new JMenu("Options");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        options.add(save); options.add(load);

        //Level Menu.
        level = new JMenu("Level");
        one = new JMenuItem("One");
        two = new JMenuItem("Two");
        level.add(one); level.add(two);

        //Record n Replay Menu.
        replay = new JMenu("Replay");
        recLoad = new JMenuItem("Load File");

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
        replay.add(recLoad); replay.add(speed);

        //Help Menu.
        help = new JMenu("Help");
        help.addMenuListener(new HelpMenuListener());

        //Add Menu Components.
        menuBar.add(game); menuBar.add(options); menuBar.add(level); menuBar.add(replay); menuBar.add(help);
    }

    /**
     * Adds the menu item action listeners so that they are active for when they
     * are called by the controls class.
     *
     * @param listener - action listener.
     */
    public void setActionListeners(ActionListener listener) {
        resume.addActionListener(listener);
        pause.addActionListener(listener);
        exit.addActionListener(listener);
        save.addActionListener(listener);
        load.addActionListener(listener);
        one.addActionListener(listener);
        two.addActionListener(listener);
        recLoad.addActionListener(listener);
        speedSet0.addActionListener(listener);
        speedSet1.addActionListener(listener);
        speedSet2.addActionListener(listener);
        speedSet3.addActionListener(listener);
        exit.addActionListener(listener);
    }

    public InventoryPanel getInventoryPanel() {
        return invPanel;
    }

    /**
     * Panel in the GUI which holds the rendered board, holds chap
     * and all other drawn tiles.
     *
     * @param gamePanel - is the Game Panel.
     */
    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
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
    public void setTimeLeft(int timeLeft){
        timeText.setText("Time: " + timeLeft);
    }

    public void setChipsLeft(int ChipsLeft){
        chipsText.setText("Chips: " + chipsLeft);
    }

}



