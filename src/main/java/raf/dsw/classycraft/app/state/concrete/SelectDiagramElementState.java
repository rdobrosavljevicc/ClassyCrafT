package raf.dsw.classycraft.app.state.concrete;

import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painter.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painter.LassoSelectionPainter;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class SelectDiagramElementState extends State {
    RectangleElement selection;
    @Override
    public void mousePressed(ClassyNodeComposite parent, Point pos) {
        ((DiagramView)MainFrame.getInstance().getPackageView().getCurrPane()).clearSelected();
        selection = new RectangleElement(null, null, Color.BLACK, new BasicStroke(2), null, pos, new Dimension(1, 1));
    }

    @Override
    public void mouseDragged(ClassyNodeComposite parent, Point pos) {
        MainFrame.getInstance().getPackageView().changePainters();
        selection.setSize(new Dimension((int) (pos.getX() - selection.getPosition().getX()), (int) (pos.getY() - selection.getPosition().getY())));
        MainFrame.getInstance().getPackageView().getPainters().add(new LassoSelectionPainter(selection));
        MainFrame.getInstance().getPackageView().repaint();
    }

    @Override
    public void mouseReleased(ClassyNodeComposite parent, Point pos) {
        MainFrame.getInstance().getPackageView().changePainters();

        if (selection.getPosition().getX() > (selection.getPosition().getX() + selection.getSize().getWidth())){
            int temp = (int) selection.getPosition().getX();
            selection.getPosition().x = (int) (selection.getPosition().getX() + selection.getSize().getWidth());
            selection.getSize().width = (int) (temp - selection.getPosition().getX());
        }

        if (selection.getPosition().getY() > (selection.getPosition().getY() + selection.getSize().getHeight())){
            int temp = (int) selection.getPosition().getY();
            selection.getPosition().y = (int) (selection.getPosition().getY() + selection.getSize().getHeight());
            selection.getSize().height = (int) (temp - selection.getPosition().getY());
        }

        ((DiagramView)MainFrame.getInstance().getPackageView().getTabbedPane().getSelectedComponent()).getSelectedElements().clear();

        Rectangle2D selectionRectangle = new Rectangle2D.Double(selection.getPosition().getX(), selection.getPosition().getY(), selection.getSize().getWidth(), selection.getSize().getHeight());

        for (ElementPainter painter : MainFrame.getInstance().getPackageView().getPainters()) {
            if (painter instanceof ConnectionPainter) {

                Line2D connection = new Line2D.Double(((ConnectionPainter) painter).getDirection()[0], ((ConnectionPainter) painter).getDirection()[1], ((ConnectionPainter) painter).getDirection()[2], ((ConnectionPainter) painter).getDirection()[3]);

                if (connection.intersects(selectionRectangle))
                    ((DiagramView) MainFrame.getInstance().getPackageView().getTabbedPane().getSelectedComponent()).getSelectedElements().add(painter.getDiagramElement());
            }

            else if (painter.getDiagramElement() instanceof Interclass) {
                Rectangle2D interclass = new Rectangle2D.Double(((Interclass) painter.getDiagramElement()).getPosition().getX(), ((Interclass) painter.getDiagramElement()).getPosition().getY(), ((Interclass) painter.getDiagramElement()).getSize().getWidth(), ((Interclass) painter.getDiagramElement()).getSize().getHeight());

                if (interclass.intersects(selectionRectangle))
                    ((DiagramView) MainFrame.getInstance().getPackageView().getTabbedPane().getSelectedComponent()).getSelectedElements().add(painter.getDiagramElement());
            }
        }


        for (DiagramElement selectedElement : ((DiagramView) MainFrame.getInstance().getPackageView().getTabbedPane().getSelectedComponent()).getSelectedElements()){
            selectedElement.setPaint(Color.BLUE);
        }
    }
}