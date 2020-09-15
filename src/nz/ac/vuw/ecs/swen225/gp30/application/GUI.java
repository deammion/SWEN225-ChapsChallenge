package nz.ac.vuw.ecs.swen225.gp30.application;

import javax.swing.*;

public class GUI extends JFrame {

    public int level = 1;

    GUI(){

        //The main GUI frame.
        JFrame GUIFrame = new JFrame("Chip's Challenge: " + level);

        //The dimensions, no layout managers, visible yes.
        GUIFrame.setSize(800,500);
        GUIFrame.setLayout(null);
        GUIFrame.setVisible(true);

    }


}
