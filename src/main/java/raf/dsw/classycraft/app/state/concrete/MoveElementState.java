package raf.dsw.classycraft.app.state.concrete;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.commands.implementation.MoveElementCommand;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.state.State;

import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;


public class MoveElementState extends State {
    private Interclass mainInterclass;
    private HashMap<String, Point> initialPositions;

    @Override
    public void mousePressed(ClassyNodeComposite parent, Point pos) {
        initialPositions = new HashMap<>();
        for (ClassyNode child : parent.getChildren()) {
            if (child instanceof Interclass) {
                Rectangle2D interclass = new Rectangle2D.Double(((Interclass) child).getPosition().getX(), ((Interclass) child).getPosition().getY(), ((Interclass) child).getSize().getWidth(), ((Interclass) child).getSize().getHeight());
                if (interclass.intersects(pos.getX(), pos.getY(), 1, 1)) {
                    mainInterclass = (Interclass) child;
                }
            }
        }
        if (mainInterclass == null)
            return;

        initialPositions.put(mainInterclass.getName(), (Point) mainInterclass.getPosition().clone());


        for (DiagramElement diagramElement : ((DiagramView)MainFrame.getInstance().getPackageView().getTabbedPane().getSelectedComponent()).getSelectedElements()) {
            if (diagramElement instanceof Interclass) {
                if (!diagramElement.equals(mainInterclass))
                    initialPositions.put(diagramElement.getName(), (Point) ((Interclass) diagramElement).getPosition().clone());
            }
        }
    }

    @Override
    public void mouseDragged(ClassyNodeComposite parent, Point pos) {
        if (mainInterclass == null)
            return;

        int xDiff = (int) (pos.getX() - mainInterclass.getPosition().getX());
        int yDiff = (int) (pos.getY() - mainInterclass.getPosition().getY());

        if (!((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getSelectedElements().contains(mainInterclass)){
            ApplicationFramework.getInstance().getClassyRepository().editPosition(mainInterclass, xDiff, yDiff);
            return;
        }

        for (DiagramElement diagramElement : ((DiagramView)MainFrame.getInstance().getPackageView().getTabbedPane().getSelectedComponent()).getSelectedElements()) {
            if (diagramElement instanceof Interclass)
                ApplicationFramework.getInstance().getClassyRepository().editPosition((Interclass) diagramElement, xDiff, yDiff);
        }
    }

    @Override
    public void mouseReleased(ClassyNodeComposite parent, Point pos) {
        if (mainInterclass == null)
            return;

        for (ClassyNode child : parent.getChildren()){
            if (child instanceof Interclass) {
                Interclass interclass = (Interclass) child;
                if (initialPositions.containsKey(interclass.getName()))
                    interclass.setPosition(new Point(initialPositions.get(interclass.getName())));
            }
        }

        int xDiff = (int) (pos.getX() - mainInterclass.getPosition().getX());
        int yDiff = (int) (pos.getY() - mainInterclass.getPosition().getY());

        ClassyTreeItem treeDiagram = null;
        Iterator<TreeNode> it = MainFrame.getInstance().getPackageView().getCurrPackage().children().asIterator();
        while (it.hasNext()) {
            treeDiagram = (ClassyTreeItem) it.next();
            if (treeDiagram.getClassyNode().equals(parent)) {
                break;
            }
        }

        if (treeDiagram == null)
            return;

        ClassyTreeItem treeItem = null;
        Iterator<TreeNode> it2 = treeDiagram.children().asIterator();
        while (it2.hasNext()) {
            treeItem = (ClassyTreeItem) it2.next();
            if (treeItem.getClassyNode().equals(mainInterclass)) {
                break;
            }
        }
        if (treeItem == null)
            return;

        AbstractCommand command = new MoveElementCommand(treeDiagram, treeItem, xDiff, yDiff);
        ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getCommandManager().addCommand(command);

        mainInterclass = null;
    }
}
