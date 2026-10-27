package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;

import java.awt.event.ActionEvent;

public class AboutUsAction extends AbstractClassyAction {

    public AboutUsAction(){
        putValue(SMALL_ICON, loadIcon("/images/aboutus.png"));
        putValue(NAME, "AboutUs");
        putValue(SHORT_DESCRIPTION, "AboutUs");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}

