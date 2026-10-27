package raf.dsw.classycraft.app.gui.swing.view.painter;

import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class RectanglePainter extends InterclassPainter{
    public RectanglePainter(DiagramElement element) {
        super(element);
        Interclass rectangle = (Interclass) element;
        element.setStroke(new BasicStroke(2));
        this.setShape(new GeneralPath());
        ((GeneralPath)this.getShape()).moveTo((float)rectangle.getPosition().x, (float)rectangle.getPosition().y);
        ((GeneralPath)this.getShape()).lineTo((float)(rectangle.getPosition().x + rectangle.getSize().width), (float)rectangle.getPosition().y);
        ((GeneralPath)this.getShape()).lineTo((float)(rectangle.getPosition().x + rectangle.getSize().width), (float)(rectangle.getPosition().y + rectangle.getSize().height));
        ((GeneralPath)this.getShape()).lineTo((float)rectangle.getPosition().x, (float)(rectangle.getPosition().y + rectangle.getSize().height));
        ((GeneralPath)this.getShape()).closePath();
    }
}
