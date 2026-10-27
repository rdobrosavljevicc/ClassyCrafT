package raf.dsw.classycraft.app.gui.swing.view.painter;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.Connection;
import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.Aggregation;
import raf.dsw.classycraft.app.classyRepository.implementation.Composition;

import java.awt.*;

@Getter
@Setter
public class ConnectionPainter extends ElementPainter{
    int[] direction;
    public ConnectionPainter(DiagramElement element) {
        super(element);
    }

    @Override
    public void paint(Graphics2D graphics, DiagramElement element) {
        if (((Connection) element).getFromElement() == null || ((Connection) element).getToElement() == null)
            return;

        if (direction == null)
            direction = new int[]{(int) ((Connection) element).getFromElement().getPosition().getX(), (int) ((Connection) element).getFromElement().getPosition().getY(), (int) ((Connection) element).getToElement().getPosition().getX(), (int) ((Connection) element).getToElement().getPosition().getY()};

        graphics.setStroke(new BasicStroke(2));
        graphics.setPaint(element.getPaint());
        graphics.draw(this.getShape());
        graphics.setStroke(element.getStroke());

        if (element instanceof Composition)
            graphics.drawString(((Composition) element).getVariableName(), (direction[0] + direction[2] - 10) / 2, (direction[1] + direction[3]) / 2 - 10);

        else if (element instanceof Aggregation)
            graphics.drawString(((Aggregation) element).getVariableName(), (direction[0] + direction[2] - 10) / 2, (direction[1] + direction[3]) / 2 - 10);

        if (((Connection) element).getFromElement().equals(((Connection) element).getToElement())){
        graphics.drawLine(direction[0], direction[1], direction[0] + 10, direction[1]);
        graphics.drawLine(direction[0] + 10, direction[1], direction[0] + 10, direction[3] - 10);
        graphics.drawLine(direction[0] + 10, direction[3] - 10, direction[2], direction[3] - 10);
        graphics.drawLine(direction[2], direction[3] - 10, direction[2], direction[3]);
        }
        else
            graphics.drawLine(direction[0], direction[1], direction[2], direction[3]);

        graphics.setPaint(Color.WHITE);
        if (this instanceof CompositionPainter)
            graphics.setPaint(Color.BLACK);
        graphics.fill(this.getShape());
    }

        @Override
    public boolean elementAt(DiagramElement element, Point point) {
        return false;
    }
}
