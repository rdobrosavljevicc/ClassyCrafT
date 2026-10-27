package raf.dsw.classycraft.app.gui.swing.commands.implementation;

import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Iterator;

public class DeleteElementCommand extends AbstractCommand {
    private ClassyTreeItem parent;
    private ClassyTreeItem child;
    private ArrayList<DiagramElement> connections;
    private ArrayList<DiagramElement> selectedElements;

    public DeleteElementCommand(ClassyTreeItem parent, ClassyTreeItem child) {
        this.parent = parent;
        this.child = child;
        connections = new ArrayList<>();
        selectedElements = new ArrayList<>(((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getSelectedElements());
        findConnections();
    }

    @Override
    public void doCommand() {
        if (child == null || parent == null)
            return;

        for (DiagramElement selectedElement : selectedElements){
            if (selectedElement instanceof Interclass) {
                Iterator<TreeNode> it = parent.children().asIterator();
                while (it.hasNext()){
                    ClassyTreeItem treeItemSelected = (ClassyTreeItem) it.next();
                    if (treeItemSelected.getClassyNode().equals(selectedElement)){
                        treeItemSelected.removeFromParent();
                        break;
                    }
                }
            }
            ApplicationFramework.getInstance().getClassyRepository().deleteNode(selectedElement);
        }
        ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).clearSelected();

        for (DiagramElement diagramElement : connections){
            ApplicationFramework.getInstance().getClassyRepository().deleteNode(diagramElement);
        }
    }

    @Override
    public void undoCommand() {
        if (child == null || parent == null)
            return;

        for (DiagramElement selectedElement : selectedElements){
            if (selectedElement instanceof Interclass) {
                parent.add(new ClassyTreeItem(selectedElement));
            }
            ApplicationFramework.getInstance().getClassyRepository().addChild((ClassyNodeComposite) parent.getClassyNode(), selectedElement);
        }
        for (DiagramElement diagramElement : connections){
            ApplicationFramework.getInstance().getClassyRepository().addChild((ClassyNodeComposite) parent.getClassyNode(), diagramElement);
        }
    }

    private void findConnections(){
        if (selectedElements.isEmpty())
            selectedElements.add((DiagramElement) child.getClassyNode());

        for (DiagramElement selectedElement : selectedElements) {
            for (ClassyNode classyNode : ((ClassyNodeComposite) parent.getClassyNode()).getChildren()) {
                DiagramElement diagramElement = (DiagramElement) classyNode;
                if (diagramElement instanceof Connection && (((Connection) diagramElement).getFromElement().equals(selectedElement) || ((Connection) diagramElement).getToElement().equals(selectedElement))) {
                    connections.add(diagramElement);
                }
            }
        }
    }
}
