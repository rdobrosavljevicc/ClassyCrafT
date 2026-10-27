package raf.dsw.classycraft.app.gui.swing.view.painter;

import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;

import java.awt.*;

public class LassoSelectionPainter extends ElementPainter{

    public LassoSelectionPainter(DiagramElement element) {
        super(element);
    }

    @Override
    public void paint(Graphics2D graphics, DiagramElement element) {
        Interclass rectangle = (Interclass) element;
        graphics.setPaint(Color.BLACK);
        graphics.setStroke(new BasicStroke(2));
        graphics.drawLine((int) rectangle.getPosition().getX(), (int) rectangle.getPosition().getY(), (int) (rectangle.getPosition().getX() + rectangle.getSize().getWidth()), (int) rectangle.getPosition().getY());
        graphics.drawLine((int) (rectangle.getPosition().getX() + rectangle.getSize().getWidth()), (int) rectangle.getPosition().getY(), (int) (rectangle.getPosition().getX() + rectangle.getSize().getWidth()), (int) (rectangle.getPosition().getY() + rectangle.getSize().getHeight()));
        graphics.drawLine((int) (rectangle.getPosition().getX() + rectangle.getSize().getWidth()), (int) (rectangle.getPosition().getY() + rectangle.getSize().getHeight()), (int) (rectangle.getPosition().getX()), (int) (rectangle.getPosition().getY() + rectangle.getSize().getHeight()));
        graphics.drawLine((int) (rectangle.getPosition().getX()), (int) (rectangle.getPosition().getY() + rectangle.getSize().getHeight()), (int) rectangle.getPosition().getX(), (int) rectangle.getPosition().getY());
    }

    @Override
    public boolean elementAt(DiagramElement element, Point point) {
        return false;
    }
}
