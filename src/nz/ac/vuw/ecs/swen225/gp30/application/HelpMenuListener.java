package nz.ac.vuw.ecs.swen225.gp30.application;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public class HelpMenuListener extends JFrame implements MenuListener {

    JPanel helpMenu;
    JPanel gamePanel, controlPanel, miscPanel;
    JTabbedPane tabbedPane;

    /**
     * If the help menu gets selected then open the instruction sheet which contains
     * all the game information needed for the player to get a better understanding of
     * the game.
     * @param menuEvent - if the menu is selected, deselected or cancelled.
     */
    @Override
    public void menuSelected(MenuEvent menuEvent) {
        System.out.println("You are in the Help Menu - selected\n");

        //Tab components.
        tabbedPane = new JTabbedPane();

        //Game Panel Customization.
        gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        gamePanel.setBorder(BorderFactory.createRaisedBevelBorder());
        gamePanel.setPreferredSize(new Dimension(468,468));

        //Control Panel Customization.
        controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        controlPanel.setPreferredSize(new Dimension(468,468));

        //Misc Panel Customization.
        miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        miscPanel.setPreferredSize(new Dimension(468,468));

        tabbedPane.add("Game", gamePanel);
        tabbedPane.add("Controls", controlPanel);
        tabbedPane.add("Extra", miscPanel);



        //Master Frame, holds all menu components.
        this.setTitle("Instructions Menu: Chaps Challenge.");
        this.add(tabbedPane);
        this.setVisible(true);
        this.pack();
        this.setFocusable(true);

        //helpMenu = new JFrame();
        //JOptionPane.showMessageDialog(helpMenu,"Welcome to the instructions: \n\n" +
/*                "Chips Challenge is a game where you (Chip) navigates around a maze collecting \n" +
                "items off the floor into your inventory. The keyboard controls for the game are \n" +
                "listed below to familiarise yourself with the game: \n\n" +
                "" +
                "-> 'A' & Left Arrow: Move Left.\n" +
                "-> 'W' & Upwards Arrow: Move Upwards.\n" +
                "-> 'S' & Downwards Arrow: Move Down.\n" +
                "-> 'D' & Right Arrow : Move Right.\n" +
                "\n" +
                "The game also has menu items that can be accessed using shortcut keys, the keys \n" +
                "will allow you to manipulate the game state as described below: \n\n" +
                "" +
                "-> 'Ctrl-X': Exit the game, current state will be lost. Game resumes from level beginning.\n" +
                "-> 'Ctrl-S': Exit the game, current state will be saved. Game resumes from saved position.\n" +
                "-> 'Ctrl-R': Resume a saved game.\n" +
                "-> 'Ctrl-P': Start a new game at the last unfinished level.\n" +
                "-> 'Ctrl-1': Start a new game at level 1.\n" +
                "-> 'SPACE' : Pause the game, will display a game paused dialog.\n" +
                "-> 'ESCAPE': \"Close the game is paused\" dialog and resume the game.\n" +
                "\n\n" +
                "Thanks for reading the instructions, have fun playing Chips Challenge!\n\n\n" +
                "");*/

    }

    @Override
    public void menuDeselected(MenuEvent menuEvent) {
        System.out.println("You are in the Help Menu - deselected\n");
    }

    @Override
    public void menuCanceled(MenuEvent menuEvent) {
        System.out.println("You are in the Help Menu - cancelled\n");
    }
}
