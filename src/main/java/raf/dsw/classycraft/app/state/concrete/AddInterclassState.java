package raf.dsw.classycraft.app.state.concrete;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;
import raf.dsw.classycraft.app.classyRepository.diagramElementFactory.DiagramElementFactory;
import raf.dsw.classycraft.app.classyRepository.diagramElementFactory.ElementFactoryUtils;
import raf.dsw.classycraft.app.classyRepository.diagramElementFactory.InterclassFactory;
import raf.dsw.classycraft.app.classyRepository.implementation.Class;
import raf.dsw.classycraft.app.classyRepository.implementation.Enum;
import raf.dsw.classycraft.app.classyRepository.implementation.Interface;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class AddInterclassState extends State {

    @Override
    public void mousePressed(ClassyNodeComposite parent, Point pos) {

    }

    @Override
    public void mouseDragged(ClassyNodeComposite parent, Point pos) {

    }

    @Override
    public void mouseReleased(ClassyNodeComposite parent, Point pos) {
        Interclass copy = null;
        for (ClassyNode child : parent.getChildren()){
            if (child instanceof Interclass){
                Interclass interclass = (Interclass) child;
                Rectangle2D interclass2D = new Rectangle2D.Double(interclass.getPosition().getX(), interclass.getPosition().getY(), interclass.getSize().getWidth(), interclass.getSize().getHeight());
                if (interclass2D.intersects(pos.getX(), pos.getY(), 1, 1)){
                    if (interclass instanceof Class) {
                        copy = new Class(interclass.getName(), interclass.getParent(), interclass.getPaint(), interclass.getStroke(), interclass.getVisibility(), (Point) interclass.getPosition().clone(), (Dimension) interclass.getSize().clone());
                        ((Class) copy).setContent(new ArrayList<>(((Class) interclass).getContent()));
                    }
                    else if (interclass instanceof Interface){
                        copy = new Interface(interclass.getName(), interclass.getParent(), interclass.getPaint(), interclass.getStroke(), interclass.getVisibility(), (Point) interclass.getPosition().clone(), (Dimension) interclass.getSize().clone());
                        ((Interface) copy).setContent(new ArrayList<>(((Interface) interclass).getContent()));
                    }
                    else if (interclass instanceof Enum){
                        copy = new Enum(interclass.getName(), interclass.getParent(), interclass.getPaint(), interclass.getStroke(), interclass.getVisibility(), (Point) interclass.getPosition().clone(), (Dimension) interclass.getSize().clone());
                        ((Enum) copy).setContent(new ArrayList<>(((Enum) interclass).getContent()));
                    }
                }
            }
        }

        Interclass interclass;
        if (copy != null){
            copy.setName(copy.getName() + ".copy");
            copy.setPosition(new Point((int) (copy.getPosition().getX() + 20), (int) copy.getPosition().getY() + 20));

            interclass = copy;
        }
        else{
            DiagramElementFactory factory = ElementFactoryUtils.getNodeFactory(MainFrame.getInstance().getPackageView().getChildType(), this);
            if (factory == null)
                return;
            interclass = ((InterclassFactory) factory).getElement("Interclass" + UUID.randomUUID().toString().substring(0, 8), null, pos, new Dimension(100, 50), parent);
            String visibility = MainFrame.getInstance().getPackageView().elementVisibilityUpdate(interclass);
            if (visibility == null)
                return;

            interclass.setVisibility(visibility);
        }
        if (parent.getChildren().contains(interclass))
            return;

        Font font = new Font("Helvetica",Font.PLAIN,12);
        Canvas canvas = new Canvas();
        FontMetrics metrics = canvas.getFontMetrics(font);
        interclass.updateSize(metrics);

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

        AbstractCommand command = new AddElementCommand(treeDiagram, new ClassyTreeItem(interclass));
        ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getCommandManager().addCommand(command);
        ClassyTreeView treeView = ((ClassyTreeImplementation) MainFrame.getInstance().getClassyTree()).getTreeView();
        treeView.expandPath(treeView.getSelectionPath().pathByAddingChild(treeDiagram));
        SwingUtilities.updateComponentTreeUI(treeView);
    }

}
