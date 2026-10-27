package raf.dsw.classycraft.app.gui.swing.controller.stateActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class ChangeInterclassAction extends AbstractClassyAction {
    public ChangeInterclassAction() {
        putValue(SMALL_ICON, loadIcon("/images/classContent.png"));
        putValue(NAME, "Change Interclass");
        putValue(SHORT_DESCRIPTION, "Change Interclass");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPackageView().getStateManager().setAddClassContent();
    }
}
