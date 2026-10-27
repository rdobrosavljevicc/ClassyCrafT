package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class DeleteNodeAction extends AbstractClassyAction {

    public DeleteNodeAction() {
        putValue(SMALL_ICON, loadIcon("/images/minus.png"));
        putValue(NAME, "Delete Node");
        putValue(SHORT_DESCRIPTION, "Delete Node");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedItem();
        MainFrame.getInstance().getClassyTree().deleteChild((ClassyTreeItem) selected.getParent(), selected);
    }
}
