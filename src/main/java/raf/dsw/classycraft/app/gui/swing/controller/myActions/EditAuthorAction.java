package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class EditAuthorAction extends AbstractClassyAction {
    public EditAuthorAction() {
        putValue(SMALL_ICON, loadIcon("/images/editAuthor.png"));
        putValue(NAME, "Rename Author");
        putValue(SHORT_DESCRIPTION, "Rename Author");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedItem();
        MainFrame.getInstance().getClassyTree().editAuthor(selected);
    }
}
