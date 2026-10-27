package raf.dsw.classycraft.app.state.concrete;
import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.gui.swing.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.commands.implementation.DeleteElementCommand;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.state.State;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class DeleteDiagramElementState extends State {

    @Override
    public void mousePressed(ClassyNodeComposite parent, Point pos) {

    }

    @Override
    public void mouseDragged(ClassyNodeComposite parent, Point pos) {
    }

    @Override
    public void mouseReleased(ClassyNodeComposite parent, Point pos) {
        ClassyTreeItem classyTreeParent = null;
        Iterator<TreeNode> it = MainFrame.getInstance().getPackageView().getCurrPackage().children().asIterator();
        while (it.hasNext()){
            ClassyTreeItem diagramTreeItem = (ClassyTreeItem) it.next();
            if (diagramTreeItem.getClassyNode().equals(parent)){
                classyTreeParent = diagramTreeItem;
                break;
            }
        }
        if (classyTreeParent == null)
            return;

        DiagramElement element = null;
        for (ElementPainter painter : MainFrame.getInstance().getPackageView().getPainters()) {
            if (painter instanceof ConnectionPainter){
                Line2D connection = new Line2D.Double(((ConnectionPainter) painter).getDirection()[0], ((ConnectionPainter) painter).getDirection()[1], ((ConnectionPainter) painter).getDirection()[2], ((ConnectionPainter) painter).getDirection()[3]);
                if (connection.intersects(pos.getX(), pos.getY(), 4, 4)){
                    element = painter.getDiagramElement();
                    break;
                }
            }
            else if (painter.getDiagramElement() instanceof Interclass) {
                Rectangle2D interclass = new Rectangle2D.Double(((Interclass) painter.getDiagramElement()).getPosition().getX(), ((Interclass) painter.getDiagramElement()).getPosition().getY(), ((Interclass) painter.getDiagramElement()).getSize().getWidth(), ((Interclass) painter.getDiagramElement()).getSize().getHeight());

                if (interclass.intersects(pos.getX(), pos.getY(), 1, 1)){
                    element = painter.getDiagramElement();
                    break;
                }
            }
        }
        if (element == null)
            return;

        ClassyTreeItem classyTreeChild = null;
        Iterator<TreeNode> findClassyTreeChild = classyTreeParent.children().asIterator();
        while (findClassyTreeChild.hasNext()){
            ClassyTreeItem diagramTreeItem = (ClassyTreeItem) findClassyTreeChild.next();
            if (diagramTreeItem.getClassyNode().getName().equals(element.getName())){
                classyTreeChild = diagramTreeItem;
                break;
            }
        }

        if (classyTreeChild == null) {
            classyTreeChild = new ClassyTreeItem(element);
        }

        AbstractCommand command = new DeleteElementCommand(classyTreeParent, classyTreeChild);
        ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getCommandManager().addCommand(command);
        SwingUtilities.updateComponentTreeUI(((ClassyTreeImplementation) MainFrame.getInstance().getClassyTree()).getTreeView());
    }
}
