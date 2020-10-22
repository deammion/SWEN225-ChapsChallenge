package nz.ac.vuw.ecs.swen225.gp30.application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * GUI class is all the visualization except for the renderer which takes
 * care of the game board. GUI displays the menu, containers, borders and
 * displays.
 *
 * @author jakeh.
 */
public class GUI extends JFrame {
    
    //JComponents.
    JPanel gamePanel, dashPanel, infoPanel, containerPanel;
    InventoryPanel invPanel;

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
    JMenuItem one, two;
    //Replay Menu Items.
    JMenuItem recLoad;

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
        levelText = new JLabel();
        levelText.setBorder(BorderFactory.createEmptyBorder(10,30,10,10));
        timeText = new JLabel();
        timeText.setBorder(BorderFactory.createEmptyBorder(10,27,10,10));
        chipsText = new JLabel();
        chipsText.setBorder(BorderFactory.createEmptyBorder(10,14,10,10));

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
        this.setTitle("Chap's Challenge! ");

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

        //Add all the sun items to the menu.
        replay.add(recLoad);

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
        exit.addActionListener(listener);
    }

    /**
     * Method to get the inventory panel.
     *
     * @return - the inventory panel.
     */
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
     * The amount of time left in a level to be set, complimentary
     * for the pause option.
     *
     * @param timeLeft - time left to complete a level.
     */
    public void setTimeLeft(int timeLeft){
        timeText.setText("Time: " + timeLeft);
    }

    /**
     * The number of Chips left for Chap to collect, used for the
     * display.
     *
     * @param chipsLeft - Microchips left to collect.
     */
    public void setChipsLeft(int chipsLeft){
        chipsText.setText("Chips Left: " + chipsLeft + " ");
    }

    /**
     * The level number for the game, used for the GUI to display
     * the text in the information panel.
     *
     * @param level - the level of the game.
     */
    public void setLevel(int level) {
        levelText.setText("Level: " + level + " ");
    }

}
