package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class LoadProjectAction extends AbstractClassyAction {
    public LoadProjectAction() {
        putValue(SMALL_ICON, loadIcon("/images/load.png"));
        putValue(NAME, "Load Project/Diagram");
        putValue(SHORT_DESCRIPTION, "Load Project/Diagram");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        jfc.setFileFilter(filter);
        ClassyTreeItem parent = MainFrame.getInstance().getClassyTree().getSelectedItem();
        if (parent.getClassyNode() instanceof Package)
            jfc.setCurrentDirectory(new File(".\\ClassyCrafT\\src\\main\\resources\\templates"));

        if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = jfc.getSelectedFile();
                ClassyTreeItem child = ApplicationFramework.getInstance().getSerializer().loadProject(file);
                parent.add(child);
                ApplicationFramework.getInstance().getClassyRepository().addChild((ClassyNodeComposite) parent.getClassyNode(), child.getClassyNode());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        ClassyTreeView treeView = ((ClassyTreeImplementation) MainFrame.getInstance().getClassyTree()).getTreeView();
        SwingUtilities.updateComponentTreeUI(treeView);
    }
}
