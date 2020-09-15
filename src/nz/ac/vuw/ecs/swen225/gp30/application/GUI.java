package nz.ac.vuw.ecs.swen225.gp30.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GUI extends JFrame implements ActionListener {

    //Current level Chip is on.
    public int GameLevel = 1;

    //JComponents.
    JFrame GUIFrame;

    //JMenu Components.
    JMenuBar menuBar;
    JMenu game, options, level, help;

    //Game Menu Items.
    JMenuItem pause, resume, exit;
    //Options Menu Items.
    JMenuItem save, load;
    //Level Menu Items.
    JMenuItem one, two, three;

    GUI(){

        //The main GUI frame.
        GUIFrame = new JFrame("Chip's Challenge: " + GameLevel);

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

        //Configuring and Building the frame.
        GUIFrame.add(menuBar);
        GUIFrame.setJMenuBar(menuBar);
        GUIFrame.setSize(800,500);
        GUIFrame.setLayout(null);
        GUIFrame.setVisible(true);

        GUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


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
}
