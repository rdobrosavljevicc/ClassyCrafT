package raf.dsw.classycraft.app.gui.swing.controller.stateActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class MoveElementAction extends AbstractClassyAction {
    public MoveElementAction() {
        putValue(SMALL_ICON, loadIcon("/images/moveElement.png"));
        putValue(NAME, "Move Elements");
        putValue(SHORT_DESCRIPTION, "Move Elements");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPackageView().getStateManager().setMoveElementState();
    }
}
