package raf.dsw.classycraft.app.gui.swing.view.painter;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;

import java.awt.*;

@Getter
@Setter
public abstract class ElementPainter{
    private DiagramElement diagramElement;
    private Shape shape;

    public ElementPainter(DiagramElement element) {
        this.diagramElement = element;
    }

    public abstract void paint(Graphics2D graphics, DiagramElement element);

    public abstract boolean elementAt(DiagramElement element, Point point);

}
