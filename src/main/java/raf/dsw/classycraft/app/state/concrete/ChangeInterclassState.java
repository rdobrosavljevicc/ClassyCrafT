package raf.dsw.classycraft.app.state.concrete;

import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.classyRepository.implementation.Aggregation;
import raf.dsw.classycraft.app.classyRepository.implementation.Composition;
import raf.dsw.classycraft.app.gui.swing.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.commands.implementation.ChangeClassContentCommand;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ChangeInterclassState extends State {

    @Override
    public void mousePressed(ClassyNodeComposite parent, Point pos) {
    }

    @Override
    public void mouseDragged(ClassyNodeComposite parent, Point pos) {

    }

    @Override
    public void mouseReleased(ClassyNodeComposite parent, Point pos) {
        Interclass ic = null;
        for (ElementPainter child : MainFrame.getInstance().getPackageView().getPainters()){
            DiagramElement diagramElement = child.getDiagramElement();
            if (child.getDiagramElement() instanceof Interclass){
                Rectangle2D interclass = new Rectangle2D.Double(((Interclass) diagramElement).getPosition().getX(), ((Interclass) diagramElement).getPosition().getY(), ((Interclass) diagramElement).getSize().getWidth(), ((Interclass) diagramElement).getSize().getHeight());
                if (interclass.intersects(pos.getX(), pos.getY(), 1, 1)) {
                    ic = (Interclass) diagramElement;
                    break;
                }
            }
            else if (child.getDiagramElement() instanceof Aggregation || child.getDiagramElement() instanceof Composition){
                double[] directions = {((ConnectionPainter)child).getDirection()[0],((ConnectionPainter)child).getDirection()[1], ((ConnectionPainter)child).getDirection()[2], ((ConnectionPainter)child).getDirection()[3]};
                Line2D connection = new Line2D.Double(directions[0], directions[1], directions[2], directions[3]);
                if (connection.intersects(pos.getX(), pos.getY(), 1, 1)) {
                    MainFrame.getInstance().getPackageView().connectionContent((Connection) diagramElement);
                    return;
                }
            }
        }
        if (ic == null)
            return;

        ArrayList<Object> content = MainFrame.getInstance().getPackageView().interclassContent(ic);
        if (content == null)
            return;
        AbstractCommand command = new ChangeClassContentCommand(ic, content);
        ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getCommandManager().addCommand(command);
    }
}
