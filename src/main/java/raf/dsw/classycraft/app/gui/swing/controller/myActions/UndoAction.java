package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class UndoAction extends AbstractClassyAction {
    public UndoAction() {
        this.setEnabled(false);
        putValue(SMALL_ICON, loadIcon("/images/undo.png"));
        putValue(NAME, "Undo Action");
        putValue(SHORT_DESCRIPTION, "Undo Action");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getCommandManager().undoCommand();
    }
}
