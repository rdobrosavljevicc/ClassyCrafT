package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class NewNodeAction extends AbstractClassyAction {

    public NewNodeAction(){
        putValue(SMALL_ICON, loadIcon("/images/plus.png"));
        putValue(NAME, "New Node");
        putValue(SHORT_DESCRIPTION, "New Node");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedItem();
        MainFrame.getInstance().getClassyTree().addChild(selected);
    }
}
