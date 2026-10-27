package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class ClassyTreeCellEditor extends DefaultTreeCellEditor implements ActionListener {
    private Object clickedOn = null;
    private JTextField edit = null;

    public ClassyTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        clickedOn = value;
        edit = new JTextField(value.toString());
        edit.addActionListener(this);
        return edit;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        if (event instanceof MouseEvent)
            if (((MouseEvent)event).getClickCount() == 3)
                return true;
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(clickedOn instanceof ClassyTreeItem) || ((ClassyTreeItem) clickedOn).getClassyNode() instanceof ProjectExplorer)
            return;
        ClassyTreeItem clicked = (ClassyTreeItem) clickedOn;
        clicked.getClassyNode().setName(e.getActionCommand());
    }
}
