package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeCellEditor;
import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeDoubleClickListener;
import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeSelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class ClassyTreeView extends JTree {
    public ClassyTreeView(DefaultTreeModel DefaultTreeModel) {
        setModel(DefaultTreeModel);
        ClassyTreeCellRenderer treeCellRenderer = new ClassyTreeCellRenderer();
        addTreeSelectionListener(new ClassyTreeSelectionListener());
        addMouseListener(new ClassyTreeDoubleClickListener());
        setCellEditor(new ClassyTreeCellEditor(this, treeCellRenderer));
        setCellRenderer(treeCellRenderer);
        setEditable(true);
    }
}
