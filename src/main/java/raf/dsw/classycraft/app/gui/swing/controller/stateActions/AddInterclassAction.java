package raf.dsw.classycraft.app.gui.swing.controller.stateActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class AddInterclassAction extends AbstractClassyAction {
    public AddInterclassAction() {
        putValue(SMALL_ICON, loadIcon("/images/interclass.png"));
        putValue(NAME, "New Interclass");
        putValue(SHORT_DESCRIPTION, "New Interclass");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPackageView().getStateManager().setAddInterclass();
    }
}
