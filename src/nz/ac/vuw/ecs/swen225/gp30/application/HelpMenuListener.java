package nz.ac.vuw.ecs.swen225.gp30.application;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class HelpMenuListener extends JFrame implements MenuListener {

    //Help Menu Components.
    JPanel gamePanel, controlPanel, miscPanel;
    JLabel gameInfo, controlInfo, miscInfo;
    JTabbedPane tabbedPane;

    //Text Components.
    JTextPane gameInfoText, controlInfoText, miscInfoText;

    /**
     * If the help menu gets selected then open the instruction sheet which contains
     * all the game information needed for the player to get a better understanding of
     * the game.
     * @param menuEvent - if the menu is selected, deselected or cancelled.
     */
    @Override
    public void menuSelected(MenuEvent menuEvent) {

        //Tab components.
        tabbedPane = new JTabbedPane();

        //Game Panel Customization.
        gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createRaisedBevelBorder()));
        gameInfo = new JLabel("CHAPS CHALLENGE");
        gameInfo.setHorizontalAlignment(SwingConstants.CENTER);
        gameInfoText = new JTextPane();
        //Attribute set for styling.
        StyledDocument styledDocumentGame = gameInfoText.getStyledDocument();
        SimpleAttributeSet attributeSetGame = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributeSetGame, StyleConstants.ALIGN_CENTER);
        styledDocumentGame.setParagraphAttributes(0, styledDocumentGame.getLength(), attributeSetGame, false);
        //Game Info Text Changes.
        gameInfoText.setCharacterAttributes(attributeSetGame,true);
        gameInfoText.setText(gameInfoTextText);
        gameInfoText.setEditable(false);
        //Add Components to Game Panel
        gamePanel.add(gameInfo);
        gamePanel.add(gameInfoText);
        gamePanel.setPreferredSize(new Dimension(468,438));

        //Control Panel Customization.
        controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createRaisedBevelBorder()));
        controlInfo = new JLabel("GAME CONTROLS");
        controlInfo.setHorizontalAlignment(SwingConstants.CENTER);
        controlInfoText = new JTextPane();
        //Attribute set for styling.
        StyledDocument styledDocumentControl = controlInfoText.getStyledDocument();
        SimpleAttributeSet attributeSetControl = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributeSetControl, StyleConstants.ALIGN_CENTER);
        styledDocumentControl.setParagraphAttributes(0, styledDocumentControl.getLength(), attributeSetControl, false);
        //Control Info Text Changes.
        controlInfoText.setCharacterAttributes(attributeSetControl,true);
        controlInfoText.setText(controlInfoTextText);
        controlInfoText.setEditable(false);
        //Add Components to the Control Panel.
        controlPanel.add(controlInfo);
        controlPanel.add(controlInfoText);
        controlPanel.setPreferredSize(new Dimension(468,438));

        //Misc Panel Customization.
        miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createRaisedBevelBorder()));
        miscInfo = new JLabel("EXTRA INFORMATION");
        miscInfo.setHorizontalAlignment(SwingConstants.CENTER);
        miscInfoText = new JTextPane();
        //Attribute set for styling.
        StyledDocument styledDocumentMisc = miscInfoText.getStyledDocument();
        SimpleAttributeSet attributeSetMisc = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributeSetMisc, StyleConstants.ALIGN_CENTER);
        styledDocumentMisc.setParagraphAttributes(0, styledDocumentMisc.getLength(), attributeSetMisc, false);
        //Misc Info Text Changes.
        miscInfoText.setCharacterAttributes(attributeSetMisc,true);
        miscInfoText.setText(miscInfoTextText);
        miscInfoText.setEditable(false);
        //Add Components to the Misc Panel.
        miscPanel.add(miscInfo);
        miscPanel.add(miscInfoText);
        miscPanel.setPreferredSize(new Dimension(468,438));

        //Add the Panels to the tabbed Pane.
        tabbedPane.add("Game", gamePanel);
        tabbedPane.add("Controls", controlPanel);
        tabbedPane.add("Extra", miscPanel);

        //Master Frame, holds all menu components.
        this.setTitle("Chaps Challenge: Instructions");
        this.add(tabbedPane);
        this.setVisible(true);
        this.pack();
        this.setFocusable(true);
        this.setResizable(false);

    }

    @Override
    public void menuDeselected(MenuEvent menuEvent) {
        System.out.println("You are in the Help Menu - deselected\n");
    }

    @Override
    public void menuCanceled(MenuEvent menuEvent) {
        System.out.println("You are in the Help Menu - cancelled\n");
    }

    String gameInfoTextText = "Before Chap can join the Big Busters computer club and hang out with the\n"
                            + "girl of his dreams, Melinda the Mental Marvel, he must solve all three\n"
                            + "challenging puzzles.\n\n"
                            + "Your bird's-eye view can help Chap rush through the puzzles and mazes\n"
                            + "before he's deleted by monsters, traps and the passage of time. Show\n"
                            + "Chap how to use the blocks of soil to overcome water traps and cherry\n"
                            + "bombs, find keys, and pick up the computer chips which Chap needs to\n"
                            + "solve some puzzles. \n\n"
                            + "Can you help Chap win the cold heart of his one true love and become\n"
                            + "an official Big Buster? If you can't, no one can! Please help Chap\n"
                            + "complete these set of extremely hard and challenging puzzles to help\n"
                            + "him secure his ultimate goal of joining the Big Busters! \n\n"
                            + "The objective of the game is to explore the imaginary world, collect\n"
                            + "objects, solve puzzles, and performing actions to complete the game\n"
                            + "allowing Chap to join the infamous Big Busters computer club entering\n"
                            + "the computing hall of fame along side Bill Jobs, Steve Gates and Epic\n"
                            + "Games TM! \n\n"
                            + "Enjoy playing our Game! \n";

    String controlInfoTextText = "The keyboard shortcuts to help you play the game are categorized below: \n\n"
                            + "Standard Controls: \n"
                            + "W & 'Upwards Arrow Key' - Move Up.\n"
                            + "A & 'Left Arrow Key' - Move Left.\n"
                            + "S & 'Downwards Arrow Key' - Move Down.\n"
                            + "D & 'Right Arrow Key' - Move Right.\n"
                            + "Space Bar - Pause the game.\n"
                            + "Escape - Resume the game.\n\n"
                            + "Ctrl-X - Exit the game, game state will be lost.\n"
                            + "Ctrl-S - Exit the game, will save game state.\n"
                            + "Ctrl-R - Resumes a saved game.\n"
                            + "Ctrl-P - Starts a new game at last unfinished level.\n"
                            + "Ctrl-1 - Starts a new game at level 1.\n\n"
                            + "Record and Replay Controls:\n"
                            + "'Up Arrow Key' - Increase Replay Speed\n"
                            + "'Down Arrow Key' - Decrease Replay Speed\n"
                            + "'Left Arrow Key' - Step backwards\n"
                            + "'Right Arrow Key' - Step forwards\n"
                            + "Space Bar - Pause/Resume\n";

    String miscInfoTextText = "This version of Chips Challenge has been created as a SWEN225 Group\n"
                            + "Project to bring together and showcase the techniques we have used\n"
                            + "over the course of the trimester in a small game.\n\n"
                            + "This implementation of the game has been created by: \n\n\n"
                            + "Damien Tamasese - Record and Replay Package\n"
                            + "Joshua Cook-Harding - Persistence Package\n"
                            + "Jake Hobbs - Application Package\n"
                            + "Pranav Gohil - Renderer Package\n"
                            + "Oscar Camplin - Maze Package\n\n"
                            + "We hope you enjoy the game!\n\n\n\n\n\n\n"
                            + "Created September - October 2020\n\n";


}
