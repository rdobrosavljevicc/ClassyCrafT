package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class ClassyTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        URL imageURL = null;

        if (((ClassyTreeItem)value).getClassyNode() instanceof ProjectExplorer)
            imageURL = getClass().getResource("/images/texplorer.png");
        else if (((ClassyTreeItem)value).getClassyNode() instanceof Project)
            imageURL = getClass().getResource("/images/tproject.png");
        else if (((ClassyTreeItem)value).getClassyNode() instanceof Package)
            imageURL = getClass().getResource("/images/tpackage.png");
        else if (((ClassyTreeItem)value).getClassyNode() instanceof Diagram)
            imageURL = getClass().getResource("/images/tdiagram.png");

        Icon icon = null;
        if (imageURL != null)
            icon = new ImageIcon(imageURL);
        setIcon(icon);

        return this;
    }
}
