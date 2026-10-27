package raf.dsw.classycraft.app.gui.swing.controller.stateActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class AddConnectionAction extends AbstractClassyAction {
    public AddConnectionAction() {
        putValue(SMALL_ICON, loadIcon("/images/connection.png"));
        putValue(NAME, "New Connection");
        putValue(SHORT_DESCRIPTION, "New Connection");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPackageView().getStateManager().setAddConnection();
    }
}
