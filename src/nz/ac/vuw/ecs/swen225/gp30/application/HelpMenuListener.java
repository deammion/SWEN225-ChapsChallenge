package nz.ac.vuw.ecs.swen225.gp30.application;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class HelpMenuListener implements MenuListener {

    JFrame helpMenu;

    @Override
    public void menuSelected(MenuEvent menuEvent) {
        System.out.println("You are in the Help Menu - selected\n");

        helpMenu = new JFrame();
        JOptionPane.showMessageDialog(helpMenu,"Welcome to the instructions.");

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
