package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class RedoAction extends AbstractClassyAction {
    public RedoAction() {
        this.setEnabled(false);
        putValue(SMALL_ICON, loadIcon("/images/redo.png"));
        putValue(NAME, "Redo Action");
        putValue(SHORT_DESCRIPTION, "Redo Action");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getCommandManager().doCommand();
    }
}
