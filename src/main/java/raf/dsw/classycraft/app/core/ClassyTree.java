package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;

public interface ClassyTree {
    ClassyTreeView generateTree(ProjectExplorer projectExplorer);
    void addChild(ClassyTreeItem parent);
    void deleteChild(ClassyTreeItem parent, ClassyTreeItem child);
    void editAuthor(ClassyTreeItem node);
    ClassyTreeItem getSelectedItem();

}
