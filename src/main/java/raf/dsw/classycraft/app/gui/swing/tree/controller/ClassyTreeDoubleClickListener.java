package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClassyTreeDoubleClickListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() != 2)
            return;

        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedItem();
        if (selected == null)
            return;

        if (selected.getClassyNode() instanceof Package) {
            MainFrame.getInstance().getPackageView().setCurrPackage(selected);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
