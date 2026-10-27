package raf.dsw.classycraft.app.gui.swing.commands.implementation;

import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.util.ArrayList;

public class MoveElementCommand extends AbstractCommand {
    private ClassyTreeItem parent;
    private ClassyTreeItem child;
    private ArrayList<DiagramElement> selectedElements;
    private int xDiff;
    private int yDiff;

    public MoveElementCommand(ClassyTreeItem parent, ClassyTreeItem child, int xDiff, int yDiff) {
        this.parent = parent;
        this.child = child;
        this.xDiff = xDiff;
        this.yDiff = yDiff;
        selectedElements = new ArrayList<>(((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getSelectedElements());
        if (!selectedElements.contains((DiagramElement)child.getClassyNode())) {
            selectedElements.clear();
            selectedElements.add((DiagramElement) child.getClassyNode());
        }
    }

    @Override
    public void doCommand() {
        if (child == null || parent == null)
            return;

        for (DiagramElement selectedElement : selectedElements) {
            if (selectedElement instanceof Interclass) {
                ApplicationFramework.getInstance().getClassyRepository().editPosition((Interclass) selectedElement, xDiff, yDiff);
            }
            ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).clearSelected();
        }
    }

    @Override
    public void undoCommand() {
        if (child == null || parent == null)
            return;

        for (DiagramElement selectedElement : selectedElements){
            if (selectedElement instanceof Interclass) {
                ApplicationFramework.getInstance().getClassyRepository().editPosition((Interclass) selectedElement, -xDiff, -yDiff);
            }
            ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).clearSelected();
        }
    }
}
