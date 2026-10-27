package raf.dsw.classycraft.app.state.concrete;

import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.classyRepository.diagramElementFactory.ConnectionFactory;
import raf.dsw.classycraft.app.classyRepository.diagramElementFactory.DiagramElementFactory;
import raf.dsw.classycraft.app.classyRepository.diagramElementFactory.ElementFactoryUtils;
import raf.dsw.classycraft.app.classyRepository.implementation.Aggregation;
import raf.dsw.classycraft.app.classyRepository.implementation.Composition;
import raf.dsw.classycraft.app.gui.swing.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.commands.implementation.AddElementCommand;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.state.State;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class AddConnectionState extends State {
    Connection connection;

    public void mousePressed(ClassyNodeComposite parent, Point pos){
        Interclass fromElement = null;

        DiagramElement diagramElement;
        for (ClassyNode child : parent.getChildren()) {
            diagramElement = (DiagramElement) child;
            if (diagramElement instanceof Interclass) {
                Rectangle2D interclass = new Rectangle2D.Double(((Interclass) diagramElement).getPosition().getX(), ((Interclass) diagramElement).getPosition().getY(), ((Interclass) diagramElement).getSize().getWidth(), ((Interclass) diagramElement).getSize().getHeight());
                if (interclass.intersects(pos.getX(), pos.getY(), 1, 1)){
                    fromElement = (Interclass) diagramElement;
                    break;
                }
            }
        }

        if (fromElement == null || fromElement.getParent() == null) {
            connection = null;
            return;
        }

        DiagramElementFactory factory = ElementFactoryUtils.getNodeFactory(MainFrame.getInstance().getPackageView().getChildType(), this);
        connection = ((ConnectionFactory) factory).getElement("Connection" + parent.getChildren().size() + 1, parent);
        connection.setFromElement(fromElement);
    }

    public void mouseDragged(ClassyNodeComposite parent, Point pos) {
        if (connection == null || connection.getFromElement() == null || connection.getFromElement().getParent() == null) {
            return;
        }

        Interclass tempElement = new RectangleElement(null, null, Color.BLACK, new BasicStroke(2), null, pos, new Dimension(0, 0));

        MainFrame.getInstance().getPackageView().changePainters();

        connection.setToElement(tempElement);

        MainFrame.getInstance().getPackageView().getPainters().add(MainFrame.getInstance().getPackageView().makePainter(connection));

        MainFrame.getInstance().getPackageView().repaint();
    }

    public void mouseReleased(ClassyNodeComposite parent, Point pos) {
        if (connection == null || connection.getFromElement() == null || connection.getFromElement().getParent() == null) {
            return;
        }

        MainFrame.getInstance().getPackageView().changePainters();

        Interclass toElement = null;
        DiagramElement diagramElement;
        for (ClassyNode child : parent.getChildren()) {
            diagramElement = (DiagramElement) child;
            if (diagramElement instanceof Interclass) {
                Rectangle2D interclass = new Rectangle2D.Double(((Interclass) diagramElement).getPosition().getX(), ((Interclass) diagramElement).getPosition().getY(), ((Interclass) diagramElement).getSize().getWidth(), ((Interclass) diagramElement).getSize().getHeight());
                if (interclass.intersects(pos.getX(), pos.getY(),1, 1)) {
                    toElement = (Interclass) diagramElement;
                    break;
                }
            }
        }

        if (toElement == null || toElement.getParent() == null || toElement.getName() == null) {
            connection = null;
            return;
        }

        connection.setToElement(toElement);

        String variableName = toElement.getName().substring(0, 1).toLowerCase() + toElement.getName().substring(1);
        if (connection instanceof Composition)
            ((Composition) connection).setVariableName(variableName);
        else if (connection instanceof Aggregation)
            ((Aggregation) connection).setVariableName(variableName + "s");

        if (connection.getToElement().getParent() != null && connection.getFromElement().getParent() != null) {
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

            AbstractCommand command = new AddElementCommand(treeDiagram, new ClassyTreeItem(connection));
            ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getCommandManager().addCommand(command);
            ClassyTreeView treeView = ((ClassyTreeImplementation) MainFrame.getInstance().getClassyTree()).getTreeView();
            treeView.expandPath(treeView.getSelectionPath().pathByAddingChild(treeDiagram));
            SwingUtilities.updateComponentTreeUI(treeView);
        }
    }
}
