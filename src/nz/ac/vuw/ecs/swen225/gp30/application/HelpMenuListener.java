package nz.ac.vuw.ecs.swen225.gp30.application;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public class HelpMenuListener extends JFrame implements MenuListener {

    //Help Menu Components.
    JPanel gamePanel, controlPanel, miscPanel;
    JLabel gameInfo, controlInfo, miscInfo;
    JTabbedPane tabbedPane;

    //Text Components.
    JLabel gameInfoText, controlInfoText, miscInfoText;

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
        gamePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createRaisedBevelBorder()));
        gameInfo = new JLabel("CHAPS CHALLENGE");
        gameInfo.setHorizontalAlignment(SwingConstants.CENTER);
        gameInfoText = new JLabel(gameInfoTextText);
        gameInfoText.setHorizontalAlignment(SwingConstants.CENTER);
        gamePanel.add(gameInfo);
        gamePanel.add(gameInfoText);
        gamePanel.setPreferredSize(new Dimension(468,438));

        //Control Panel Customization.
        controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createRaisedBevelBorder()));
        controlInfo = new JLabel("GAME CONTROLS");
        controlInfo.setHorizontalAlignment(SwingConstants.CENTER);
        controlPanel.add(controlInfo);
        controlPanel.setPreferredSize(new Dimension(468,438));

        //Misc Panel Customization.
        miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createRaisedBevelBorder()));
        miscInfo = new JLabel("EXTRA INFORMATION");
        miscInfo.setHorizontalAlignment(SwingConstants.CENTER);
        miscPanel.add(miscInfo);
        miscPanel.setPreferredSize(new Dimension(468,438));

        tabbedPane.add("Game", gamePanel);
        tabbedPane.add("Controls", controlPanel);
        tabbedPane.add("Extra", miscPanel);

        //Master Frame, holds all menu components.
        this.setTitle("Chaps Challenge: Instructions");
        this.add(tabbedPane);
        this.setVisible(true);
        this.pack();
        this.setFocusable(true);

    }

    @Override
    public void menuDeselected(MenuEvent menuEvent) {
        System.out.println("You are in the Help Menu - deselected\n");
    }

    @Override
    public void menuCanceled(MenuEvent menuEvent) {
        System.out.println("You are in the Help Menu - cancelled\n");
    }

    String gameInfoTextText =
            "Before Chip can join the Bit Busters computer club and hang out with the girl of his dreams, \n" +
                    " Melinda the Mental Marvel, he must solve all 144 challenging puzzles.\n" +
            "\n" +
            "Your bird's-eye view can help Chip rush through the puzzles and mazes before he's deleted by monsters, traps and the passage of time. Show Chip how to use blocks of soil to overcome water traps and cherry bombs, find keys, and pick up the computer chips which Chip needs to solve some puzzles.\n" +
            "\n" +
            "Can you help Chip win the cold heart of his one true love and become an official Bit Buster? If you can't, no one can!";

}
